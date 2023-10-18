package manuall.api.service

import manuall.api.domain.*
import manuall.api.dto.perfil.NotificacaoDto
import manuall.api.dto.perfil.NotificacaoSolicitacaoDto
import manuall.api.dto.usuario.*
import manuall.api.enums.TipoUsuario
import manuall.api.repository.*
import manuall.api.security.JwtTokenManager
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.util.*
import java.util.concurrent.ArrayBlockingQueue

@Service
class UsuarioService(
    val passwordEncoder: PasswordEncoder,
    val jwtTokenManager: JwtTokenManager,
    val authenticationManager: AuthenticationManager,
    val usuarioRepository: UsuarioRepository,
    val dadosEnderecoRepository: DadosEnderecoRepository,
    val areaRepository: AreaRepository,
    val servicoRepository: ServicoRepository,
    val usuarioServicoRepository: UsuarioServicoRepository,
    val solicitacaoRepository: SolicitacaoRepository
) {

    fun getIdByToken(token: String?): ResponseEntity<Int> {

        val usuario = jwtTokenManager.validateToken(token)
            ?: return ResponseEntity.status(480).build()

        return ResponseEntity.status(200).body(usuario.id)
    }

    fun buscarArea(): List<Area> {
        return areaRepository.findAll()
    }

    fun buscarTiposServico(idServico: Int): List<Servico> {
        return servicoRepository.findAllByAreaId(idServico)
    }

    fun loginChecar(email: String): ResponseEntity<List<Int>> {
        val possiveisUsuarios = usuarioRepository.findByEmail(email)

        return if (possiveisUsuarios.size > 1) {
            ResponseEntity.status(207).body(
                possiveisUsuarios.map { TipoUsuario.fromObjectToInt(it) }
            )
        } else {
            ResponseEntity.status(200).build()
        }
    }

    fun loginEfetuar(usuarioLoginRequest: UsuarioLoginRequest): ResponseEntity<LoginResponse> {

        val possiveisUsuarios = if (usuarioLoginRequest.tipoUsuario !== null) {
            usuarioRepository.findByEmailAndTipoUsuarioList(
                usuarioLoginRequest.email,
                TipoUsuario.fromIntToClass(usuarioLoginRequest.tipoUsuario!!)
            )
        } else {
            usuarioRepository.findByEmail(usuarioLoginRequest.email)
        }

        return if (possiveisUsuarios.isEmpty()) {
            ResponseEntity.status(401).build()
        } else {
            val usuario = possiveisUsuarios[0]

            if (passwordEncoder.matches(usuarioLoginRequest.senha, usuario.senha)) {
                when (usuario.status) {
                    null -> return ResponseEntity.status(206).body(
                        LoginResponse(
                            null,
                            null,
                            TipoUsuario.fromObjectToInt(usuario),
                            if (dadosEnderecoRepository.findByUsuarioId(usuario.id).isEmpty)
                                2
                            else
                                3,
                            null
                        )
                    )

                    4 -> return ResponseEntity.status(403).body(
                        LoginResponse(
                            null,
                            "Aprovação negada",
                            null,
                            null,
                            null
                        )
                    )

                    1 -> return ResponseEntity.status(403).body(
                        LoginResponse(
                            null,
                            "Aprovação pendente",
                            null,
                            null,
                            null
                        )
                    )
                }

                val authentication = authenticationManager.authenticate(
                    UsernamePasswordAuthenticationToken(
                        TipoUsuario.fromObjectToString(usuario) + usuario.email,
                        usuarioLoginRequest.senha
                    )
                )

                SecurityContextHolder.getContext().authentication = authentication
                if (usuario is Prestador && usuario.plano == null)
                    ResponseEntity.status(206).body(
                        LoginResponse(
                            jwtTokenManager.generateToken(authentication),
                            null,
                            null,
                            4,
                            usuario.id
                        )
                    )
                else
                    ResponseEntity.status(200).body(
                        LoginResponse(
                            jwtTokenManager.generateToken(authentication),
                            null,
                            TipoUsuario.fromObjectToInt(usuario),
                            null,
                            null
                        )
                    )
            } else {
                ResponseEntity.status(401).build()
            }
        }
    }

    fun checarValidadeLoginAdm(token: String?): ResponseEntity<Unit> {

        val usuario = jwtTokenManager.validateToken(token)
            ?: return ResponseEntity.status(480).build()

        if (usuario !is Administrador) return ResponseEntity.status(480).build()

        return ResponseEntity.status(200).build()
    }

    fun logoff(token: String?): ResponseEntity<Unit> {

        if (jwtTokenManager.validateToken(token) == null)
            return ResponseEntity.status(480).build()

        jwtTokenManager.expirarToken(token!!)
        return ResponseEntity.status(200).build()
    }

    fun getNotificacoes(token: String?): ResponseEntity<List<NotificacaoDto>> {

        val usuario = jwtTokenManager.validateToken(token)
            ?: return ResponseEntity.status(480).build()

        val usuarioSolicitacoes = when (usuario) {
            is Contratante -> solicitacaoRepository.findByContratanteId(usuario.id)
            is Prestador -> solicitacaoRepository.findByPrestadorId(usuario.id)
            else -> return ResponseEntity.status(404).build()
        }

        val notificacoes = mutableListOf<NotificacaoDto>()

        for (i in usuarioSolicitacoes.indices) {
            val solicitacao = usuarioSolicitacoes[i]
            when (solicitacao.status) {
                1 -> {
                    notificacoes.add(
                        NotificacaoDto(
                            solicitacao.id,
                            if (usuario is Prestador)
                                solicitacao.contratante.nome!!
                            else
                                solicitacao.prestador.nome!!,
                            1,
                            solicitacao.dataFim,
                            NotificacaoSolicitacaoDto(
                                solicitacao.incluiAula,
                                solicitacao.servico.nome,
                                solicitacao.tamanho,
                                solicitacao.medida,
                                solicitacao.descricao,
                                solicitacao.solicitacaoImg.map { it.anexo }
                            )
                        )
                    )
                }

                2 -> {
                    val cal = Calendar.getInstance()
                    cal.add(Calendar.DATE, -4)
                    if (solicitacao.dataFim != null && solicitacao.dataFim!!.before(cal.time)) {
                        if (solicitacao.formOrcamento == null && usuario is Prestador) {
                            notificacoes.add(
                                NotificacaoDto(
                                    solicitacao.id,
                                    solicitacao.contratante.nome!!,
                                    4,
                                    solicitacao.dataFim,
                                    null
                                )
                            )
                        }
                    } else {
                        notificacoes.add(
                            NotificacaoDto(
                                solicitacao.id,
                                if (usuario is Prestador)
                                    solicitacao.contratante.nome!!
                                else
                                    solicitacao.prestador.nome!!,
                                2,
                                null,
                                null,
                            )
                        )
                    }
                }

                4 -> {
                    notificacoes.add(
                        NotificacaoDto(
                            solicitacao.id,
                            if (usuario is Prestador)
                                solicitacao.contratante.nome!!
                            else
                                solicitacao.prestador.nome!!,
                            3,
                            solicitacao.dataFim,
                            null,
                        )
                    )
                }
            }
        }

        return ResponseEntity.status(200).body(notificacoes)
    }

    fun aprovacoesPendentes(token: String?): ResponseEntity<List<AprovacaoDto>> {
        val usuario = jwtTokenManager.validateToken(token)
            ?: return ResponseEntity.status(480).build()

        if (usuario !is Administrador) {
            return ResponseEntity.status(403).build()
        }

        val usuarios = usuarioRepository.aprovacoesPendentes()

        val filaPrestadores = ArrayBlockingQueue<AprovacaoDto>(usuarios.size)

        usuarios.forEach {
            filaPrestadores.put(
                AprovacaoDto(
                    it,
                    usuarioServicoRepository.findServicosNomeByUsuarioId(it.id)
                )
            )
        }

        val prestadores = mutableListOf<AprovacaoDto>()
        while (!filaPrestadores.isEmpty()) {
            prestadores.add(filaPrestadores.take())
        }

        return ResponseEntity.status(200).body(prestadores)
    }

    fun aprovar(token: String?, idPrestador: Int, aprovar: Boolean): ResponseEntity<Unit> {

        if (jwtTokenManager.validateToken(token) == null)
            return ResponseEntity.status(480).build()

        val usuario = usuarioRepository.findById(idPrestador).get()

        usuario as Prestador

        usuario.status = if (aprovar) 2 else 4

        usuarioRepository.save(usuario)

        return ResponseEntity.status(200).build()
    }

}