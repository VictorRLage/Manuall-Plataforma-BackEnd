package manuall.newproject.service

import manuall.newproject.domain.*
import manuall.newproject.dto.usuario.LoginResponse
import manuall.newproject.dto.usuario.UsuarioLoginRequest
import manuall.newproject.dto.usuario.UsuariosFilteredList
import manuall.newproject.repository.*
import manuall.newproject.security.JwtTokenManager
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
    val servicoRepository: ServicoRepository
) {

    fun getPrestadoresOrderByPlano(): ResponseEntity<List<UsuariosFilteredList>> {
        return ResponseEntity.status(200).body(usuarioRepository.findAllPrestadores())
    }

    fun getPrestadoresByAreaIdOrderByPlano(idArea: Int): ResponseEntity<List<UsuariosFilteredList>> {
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
            ResponseEntity.status(200).body(possiveisUsuarios[0].get().tipoUsuario)
        }
    }

    fun loginEfetuar(usuarioLoginRequest: UsuarioLoginRequest): ResponseEntity<String> {

        // Pegando os usuários com o email requisitado em uma lista, já que podem
        // existir 2 usuários com o mesmo email e tipo_usuario diferentes
        val possivelUsuario =
            usuarioRepository.findByEmailAndTipoUsuario(usuarioLoginRequest.email, usuarioLoginRequest.tipoUsuario)

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

                    4 -> ResponseEntity.status(403).body("Aprovação negada")
                    1 -> ResponseEntity.status(403).body("Aprovação pendente")
                }

                // Gerando token de sessão com base no tipo_usuario e email, para identificar unicamente cada login
                val authentication = authenticationManager.authenticate(
                    UsernamePasswordAuthenticationToken(
                        usuario.tipoUsuario.toString() + usuario.email,
                        usuarioLoginRequest.senha
                    )
                )

                SecurityContextHolder.getContext().authentication = authentication
                // Token + 200 = Cadastro 100% concluído
                // Token + 403 = Parou na escolha de plano
                ResponseEntity.status(if (usuario.plano == null) 206 else 200)
                    .body(jwtTokenManager.generateToken(authentication))

            } else {
                ResponseEntity.status(401).body("Credenciais inválidas")
            }

        }

    }

    fun logoff(token: String): ResponseEntity<Void> {
        jwtTokenManager.expirarToken(token)
        return ResponseEntity.status(200).build()
    }

    fun getPrestadoresFiltrado(
        idArea: String,
        filtro: String,
        crescente: Boolean
    ): ResponseEntity<List<UsuariosFilteredList>> {

        val filtragem = when (filtro) {
            "Nota" ->
                if (crescente)
                    if (idArea == "null")
                        usuarioRepository.findAllOrderByNotaAsc()
                    else
                        usuarioRepository.findByAreaIdOrderByNotaAsc(idArea.toInt())
                else
                    if (idArea == "null")
                        usuarioRepository.findAllOrderByNotaDesc()
                    else
                        usuarioRepository.findByAreaIdOrderByNotaDesc(idArea.toInt())
            "PrecoMax" ->
                if (crescente)
                    if (idArea == "null")
                        usuarioRepository.findAllOrderByPrecoMaxAsc()
                    else
                        usuarioRepository.findByAreaIdOrderByPrecoMaxAsc(idArea.toInt())
                else
                    if (idArea == "null")
                        usuarioRepository.findAllOrderByPrecoMaxDesc()
                    else
                        usuarioRepository.findByAreaIdOrderByPrecoMaxDesc(idArea.toInt())
            "PrecoMin" ->
                if (crescente)
                    if (idArea == "null")
                        usuarioRepository.findAllOrderByPrecoMinAsc()
                    else
                        usuarioRepository.findByAreaIdOrderByPrecoMinAsc(idArea.toInt())
                else
                    if (idArea == "null")
                        usuarioRepository.findAllOrderByPrecoMinDesc()
                    else
                        usuarioRepository.findByAreaIdOrderByPrecoMinDesc(idArea.toInt())
            "Alfabetica" ->
                if (crescente)
                    if (idArea == "null")
                        usuarioRepository.findAllOrderByAlfabeticaAsc()
                    else
                        usuarioRepository.findByAreaIdOrderByAlfabeticaAsc(idArea.toInt())
                else
                    if (idArea == "null")
                        usuarioRepository.findAllOrderByAlfabeticaDesc()
                    else
                        usuarioRepository.findByAreaIdOrderByAlfabeticaDesc(idArea.toInt())
            "Servico" ->
                if (crescente)
                    if (idArea == "null")
                        usuarioRepository.findAllOrderByServicoAsc()
                    else
                        usuarioRepository.findByAreaIdOrderByServicoAsc(idArea.toInt())
                else
                    if (idArea == "null")
                        usuarioRepository.findAllOrderByServicoDesc()
                    else
                        usuarioRepository.findByAreaIdOrderByServicoDesc(idArea.toInt())
            "ServicoAula" ->
                if (crescente)
                    if (idArea == "null")
                        usuarioRepository.findAllOrderByServicoAulaAsc()
                    else
                        usuarioRepository.findByAreaIdOrderByServicoAulaAsc(idArea.toInt())
                else
                    if (idArea == "null")
                        usuarioRepository.findAllOrderByServicoAulaDesc()
                    else
                        usuarioRepository.findByAreaIdOrderByServicoAulaDesc(idArea.toInt())

            else -> return ResponseEntity.status(404).build()
        }

        return ResponseEntity.status(200).body(filtragem)

    }

}