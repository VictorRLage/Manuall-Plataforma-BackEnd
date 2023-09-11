package manuall.api.service

import manuall.api.domain.*
import manuall.api.dto.usuario.AprovacaoDto
import manuall.api.dto.usuario.LoginResponse
import manuall.api.dto.usuario.UsuarioLoginRequest
import manuall.api.dto.usuario.FilteredUsuario
import manuall.api.enums.TipoUsuario
import manuall.api.repository.*
import manuall.api.security.JwtTokenManager
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UsuarioService (
    val passwordEncoder: PasswordEncoder,
    val jwtTokenManager: JwtTokenManager,
    val authenticationManager: AuthenticationManager,
    val usuarioRepository: UsuarioRepository,
    val dadosEnderecoRepository: DadosEnderecoRepository,
    val areaRepository: AreaRepository,
    val servicoRepository: ServicoRepository,
    val usuarioServicoRepository: UsuarioServicoRepository
) {

    fun getIdByToken(token: String?): ResponseEntity<Int> {

        val usuario = jwtTokenManager.validateToken(token)
            ?: return ResponseEntity.status(480).build()

        return ResponseEntity.status(200).body(usuario.id)
    }

    fun getPrestadoresOrderByPlano(): ResponseEntity<List<FilteredUsuario>> {
        return ResponseEntity.status(200).body(usuarioRepository.findAllPrestadores())
    }

    fun getPrestadoresByAreaIdOrderByPlano(idArea: Int): ResponseEntity<List<FilteredUsuario>> {
        return ResponseEntity.status(200).body(usuarioRepository.findAllPrestadoresByArea(idArea))
    }

    fun buscarArea(): List<Area> {
        return areaRepository.findAll()
    }

    fun buscarTiposServico(idServico: Int): List<Servico> {
        return servicoRepository.findAllByAreaId(idServico)
    }

    fun loginChecar(email: String): ResponseEntity<Int> {
        val possiveisUsuarios = usuarioRepository.findByEmail(email)

        return if (possiveisUsuarios.isEmpty()) {
            ResponseEntity.status(204).build()
        } else if (possiveisUsuarios.size > 1) {
            ResponseEntity.status(409).build()
        } else {
            ResponseEntity.status(200).body(
                TipoUsuario.fromObjectToInt(possiveisUsuarios[0].get())
            )
        }
    }

    fun loginEfetuar(usuarioLoginRequest: UsuarioLoginRequest): ResponseEntity<String> {

        // Pegando os usuários com o email requisitado em uma lista, já que podem
        // existir 2 usuários com o mesmo email e tipo_usuario diferentes
        val possivelUsuario =
            usuarioRepository.findByEmailAndTipoUsuario(
                usuarioLoginRequest.email,
                TipoUsuario.fromIntToClass(usuarioLoginRequest.tipoUsuario)
            )

        return if (possivelUsuario.isEmpty) {
            ResponseEntity.status(401).body("Credenciais inválidas")
        } else {
            // Tendo em vista que existe apenas 1 usuário nesta lista, vamos simplificar e chamá-lo de "usuario"
            val usuario = possivelUsuario.get()

            // Autenticando sua senha, essa função decripta a senha do banco e a compara com a recebida
            if (passwordEncoder.matches(usuarioLoginRequest.senha, usuario.senha)) {

                when (usuario.status) {
                    null -> {
                        if (dadosEnderecoRepository.findByUsuarioId(usuario.id).isEmpty) {
                            // Parou o cadastro na fase 2
                            ResponseEntity.status(403).body(
                                LoginResponse(
                                    usuario.id,
                                    2
                                )
                            )
                        } else {
                            // Parou o cadastro na fase 3
                            ResponseEntity.status(403).body(
                                LoginResponse(
                                    usuario.id,
                                    3
                                )
                            )
                        }

                    }

                    4 -> return ResponseEntity.status(403).body("Aprovação negada")
                    1 -> return ResponseEntity.status(403).body("Aprovação pendente")
                }

                // Gerando token de sessão com base no tipo_usuario e email, para identificar unicamente cada login
                val authentication = authenticationManager.authenticate(
                    UsernamePasswordAuthenticationToken(
                        TipoUsuario.fromObjectToString(usuario) + usuario.email,
                        usuarioLoginRequest.senha
                    )
                )

                SecurityContextHolder.getContext().authentication = authentication
                // Token + 200 = Cadastro 100% concluído
                // Token + 403 = Parou na escolha de plano
                ResponseEntity.status(if (usuario is Prestador && usuario.plano == null) 206 else 200)
                    .body(jwtTokenManager.generateToken(authentication))

            } else {
                ResponseEntity.status(401).body("Credenciais inválidas")
            }

        }

    }

    fun logoff(token: String?): ResponseEntity<Unit> {

        if (jwtTokenManager.validateToken(token) == null)
            return ResponseEntity.status(480).build()

        jwtTokenManager.expirarToken(token!!)
        return ResponseEntity.status(200).build()
    }

    fun checarValidadeLoginAdm(token: String?): ResponseEntity<Unit> {

        val usuario = jwtTokenManager.validateToken(token)
            ?: return ResponseEntity.status(480).build()

        if (usuario !is Administrador) return ResponseEntity.status(480).build()

        return ResponseEntity.status(200).build()
    }

    fun aprovacoesPendentes(token: String?): ResponseEntity<List<AprovacaoDto>> {

        val usuario = jwtTokenManager.validateToken(token)
            ?: return ResponseEntity.status(480).build()

        if (usuario !is Administrador) {
            return ResponseEntity.status(403).build()
        }

        val usuarios = usuarioRepository.aprovacoesPendentes()
        val listaPendentes = mutableListOf<AprovacaoDto>()
        usuarios.forEach {
            listaPendentes.add(AprovacaoDto(
                it,
                usuarioServicoRepository.findServicosNomeByUsuarioId(it.id)
            ))
        }

        return ResponseEntity.status(200).body(listaPendentes)
    }

    fun aprovar(token: String?, idPrestador: Int, aprovar: Boolean): ResponseEntity<Unit> {

        if (jwtTokenManager.validateToken(token) == null)
            return ResponseEntity.status(480).build()

        val usuario = usuarioRepository.findById(idPrestador).get()

        usuario.status = if (aprovar) 2 else 4

        usuarioRepository.save(usuario)

        return ResponseEntity.status(200).build()
    }

    fun getPrestadoresFiltrados(
        idArea: Int,
        filtro: String,
        crescente: Boolean
    ): ResponseEntity<List<FilteredUsuario>> {

        val filtroCompleto = """
            find${
                if (idArea == 0) "All" else "ByAreaId"
            }OrderBy$filtro${
                if (crescente) "Asc" else "Desc"
            }
        """.trimIndent()

        val method = try {
            if (idArea == 0)
                usuarioRepository::class.java
                    .getMethod(filtroCompleto)
            else
                usuarioRepository::class.java
                    .getMethod(filtroCompleto, idArea::class.java)
        } catch (e: NoSuchMethodException) {
            return ResponseEntity.status(404).build()
        }

        val filtragem = runCatching {
            if (idArea == 0)
                method.invoke(usuarioRepository)
            else
                method.invoke(usuarioRepository, idArea)
        }.getOrElse { _ -> return ResponseEntity.status(404).build() }

        return ResponseEntity.status(200).body(filtragem as List<FilteredUsuario>)

    }

}