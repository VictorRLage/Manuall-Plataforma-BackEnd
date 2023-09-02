package manuall.api.service

import manuall.api.domain.Prestador
import manuall.api.domain.Usuario
import manuall.api.domain.UsuarioImg
import manuall.api.dto.perfil.*
import manuall.api.dto.usuario.AlterPfpRequest
import manuall.api.repository.*
import manuall.api.security.JwtTokenManager
import org.springframework.http.ResponseEntity
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class PerfilService(
    val jwtTokenManager: JwtTokenManager,
    val passwordEncoder: PasswordEncoder,
    val usuarioRepository: UsuarioRepository,
    val dadosEnderecoRepository: DadosEnderecoRepository,
    val avaliacaoRepository: AvaliacaoRepository,
    val solicitacaoRepository: SolicitacaoRepository,
    val areaRepository: AreaRepository,
    val usuarioImgRepository: UsuarioImgRepository,
    val usuarioServicoRepository: UsuarioServicoRepository
) {

    fun acessarPerfilPrestador(idPrestador: Int): ResponseEntity<Void> {
        val prestador = usuarioRepository.findById(idPrestador)
        if (prestador.isEmpty) {
            return ResponseEntity.status(404).build()
        }

        val prestadorEncontrado = prestador.get()

        if (prestadorEncontrado !is Prestador)
            return ResponseEntity.status(403).build()

        prestadorEncontrado.acessos = prestadorEncontrado.acessos!! + 1

        usuarioRepository.save(prestadorEncontrado)

        return ResponseEntity.status(200).build()
    }

    fun alterarPerfil(token: String, alterarPerfilDTO: AlterarPerfilDto): ResponseEntity<String> {

        val usuarioEncontrado = jwtTokenManager.takeIf { it.validarToken(token) }
            ?.getUserFromToken(token)
            ?: return ResponseEntity.status(480).build()

        if (usuarioEncontrado !is Prestador)
            return ResponseEntity.status(403).body("Usuário não é um prestador")

        usuarioEncontrado.area = alterarPerfilDTO.area
        usuarioEncontrado.descricao = alterarPerfilDTO.descricao
        usuarioEncontrado.anexoPfp = alterarPerfilDTO.profile
        usuarioEncontrado.nome = alterarPerfilDTO.nome
        usuarioEncontrado.orcamentoMin = alterarPerfilDTO.orcamentoMin
        usuarioEncontrado.orcamentoMax = alterarPerfilDTO.orcamentoMax
        usuarioEncontrado.prestaAula = alterarPerfilDTO.prestaAula

        usuarioRepository.save(usuarioEncontrado)

        return ResponseEntity.ok("Perfil do usuário atualizado com sucesso")
    }

    fun checarPrestador(token: String): ResponseEntity<PerfilDto> {

        val usuarioEncontrado = jwtTokenManager.takeIf { it.validarToken(token) }
            ?.getUserFromToken(token)
            ?: return ResponseEntity.status(480).build()

        if (usuarioEncontrado !is Prestador)
            return ResponseEntity.status(403).build()

        val dadosEndereco = dadosEnderecoRepository.findByUsuarioId(usuarioEncontrado.id).get()
        val dadosAvaliacao = avaliacaoRepository.findByPrestadorUsuarioId(usuarioEncontrado.id)
        val notificacoes = solicitacaoRepository.findAllByUsuarioId(usuarioEncontrado.id)
        val nomeArea = areaRepository.findAreaNomeByUsuarioId(usuarioEncontrado.id)
        val urls = usuarioImgRepository.findUrlsByUsuarioId(usuarioEncontrado.id)
        val servicos = usuarioServicoRepository.findServicosNomeByUsuarioId(usuarioEncontrado.id)

        val perfilDTO = PerfilDto(
            nomeArea!!,
            usuarioEncontrado.descricao!!,
            usuarioEncontrado.anexoPfp!!,
            usuarioEncontrado.nome!!,
            usuarioEncontrado.orcamentoMin!!,
            usuarioEncontrado.orcamentoMax!!,
            usuarioEncontrado.prestaAula!!,
            dadosEndereco.estado!!,
            dadosEndereco.cidade!!,
            urls,
            servicos,
            dadosAvaliacao,
            notificacoes
        )
        return ResponseEntity.status(200).body(perfilDTO)
    }

    fun atualizarSenha(token: String, alterSenhaRequest: AlterSenhaRequest): ResponseEntity<Usuario> {

        // Checando se token foi expirado e então encontrando usuário por token
        val usuarioEncontrado = jwtTokenManager.takeIf { it.validarToken(token) }
            ?.getUserFromToken(token)
            ?: return ResponseEntity.status(480).build()

        return if (passwordEncoder.matches(alterSenhaRequest.senhaAntiga, usuarioEncontrado.senha)) {

            usuarioEncontrado.senha = passwordEncoder.encode(alterSenhaRequest.senhaNova)
            ResponseEntity.status(200).body(usuarioRepository.save(usuarioEncontrado))

        } else {
            ResponseEntity.status(401).build()
        }
    }

    fun atualizarDesc(token: String, alterDescRequest: AlterDescRequest): ResponseEntity<Usuario> {

        // Checando se token foi expirado e então encontrando usuário por token
        val usuarioEncontrado = jwtTokenManager.takeIf { it.validarToken(token) }
            ?.getUserFromToken(token)
            ?: return ResponseEntity.status(480).build()

        if (usuarioEncontrado !is Prestador)
            return ResponseEntity.status(403).build()

        usuarioEncontrado.descricao = alterDescRequest.descricao
        return ResponseEntity.status(200).body(usuarioRepository.save(usuarioEncontrado))
    }

    fun deletar(token: String): ResponseEntity<String> {

        // Checando se token foi expirado e então encontrando usuário por token
        val usuarioEncontrado = jwtTokenManager.takeIf { it.validarToken(token) }
            ?.getUserFromToken(token)
            ?: return ResponseEntity.status(480).build()

        dadosEnderecoRepository.deleteByUsuarioId(usuarioEncontrado.id)
        usuarioRepository.deleteById(usuarioEncontrado.id)
        return ResponseEntity.status(200).body(null)

    }

    fun atualizarPFP(token: String, alterPfpRequest: AlterPfpRequest): ResponseEntity<Usuario> {

        // Checando se token foi expirado e então encontrando usuário por token
        val usuarioEncontrado = jwtTokenManager.takeIf { it.validarToken(token) }
            ?.getUserFromToken(token)
            ?: return ResponseEntity.status(480).build()

        if (usuarioEncontrado !is Prestador)
            return ResponseEntity.status(403).build()

        usuarioEncontrado.anexoPfp = alterPfpRequest.novaUrl
        return ResponseEntity.status(200).body(usuarioRepository.save(usuarioEncontrado))
    }

    fun getSolicitacoes(token: String): ResponseEntity<List<NotificacaoDto>> {

        val usuarioEncontrado = jwtTokenManager.takeIf { it.validarToken(token) }
            ?.getUserFromToken(token)
            ?: return ResponseEntity.status(480).build()

        if (usuarioEncontrado is Prestador) {
            val solicitacoes = solicitacaoRepository.findByPrestadorUsuarioIdOrderByIdDesc(usuarioEncontrado.id)
            return ResponseEntity.status(200).body(solicitacoes.map {
                NotificacaoDto(
                    it.contratanteUsuario.nome,
                    when (it.status) {
                        1 -> "Você recebeu uma solicitação de "
                        2 -> "Você aceitou a solicitação de "
                        else -> "Você recusou a solicitação de "
                    },
                    null
                )
            })
        } else {
            val solicitacoes = solicitacaoRepository.findByContratanteUsuarioIdOrderByIdDesc(usuarioEncontrado.id)
            return ResponseEntity.status(200).body(solicitacoes.map {
                NotificacaoDto(
                    it.prestadorUsuario.nome,
                    when (it.status) {
                        1 -> "Você enviou uma solicitação para "
                        2 -> "Sua solicitação foi aceita por "
                        else -> "Sua solicitação foi recusada por "
                    },
                    it.prestadorUsuario.anexoPfp
                )
            })
        }
    }

    fun postarUrl(token: String, urlPerfilDto: UrlPerfilDto): ResponseEntity<List<String>> {

        val usuarioEncontrado = jwtTokenManager.takeIf { it.validarToken(token) }
            ?.getUserFromToken(token)
            ?: return ResponseEntity.status(480).build()

        urlPerfilDto.imagens.forEach {
            val usuarioImg = UsuarioImg()

            usuarioImg.usuario = usuarioRepository.findById(usuarioEncontrado.id).get() as Prestador
            usuarioImg.anexo = it

            usuarioImgRepository.save(usuarioImg)
        }
        return ResponseEntity.status(201).build()
    }

    fun excluirUrls(token: String, urlPerfilDto: UrlPerfilDto): ResponseEntity<Int> {

        val usuarioEncontrado = jwtTokenManager.takeIf { it.validarToken(token) }
            ?.getUserFromToken(token)
            ?: return ResponseEntity.status(480).build()

        urlPerfilDto.imagens.forEach {

            val teste = usuarioImgRepository.findByAnexoAndUsuarioId(
                it,
                usuarioRepository.findById(usuarioEncontrado.id).get().id
            )
            usuarioImgRepository.delete(teste.get())
        }
        return ResponseEntity.status(201).build()
    }
}