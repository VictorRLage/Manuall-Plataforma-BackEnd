package manuall.api.service

import manuall.api.domain.Administrador
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

    fun buscarTodos(token: String?): ResponseEntity<List<Any>> {

        val usuario = jwtTokenManager.validateToken(token)
            ?: return ResponseEntity.status(480).build()

        if (usuario !is Administrador) return ResponseEntity.status(480).build()

        return ResponseEntity.status(200).body(usuarioRepository.findAll())
    }

    fun acessarPerfilPrestador(idPrestador: Int): ResponseEntity<Unit> {
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

    fun alterarPerfil(token: String?, alterarPerfilDTO: AlterarPerfilDto): ResponseEntity<String> {

        val usuario = jwtTokenManager.validateToken(token)
            ?: return ResponseEntity.status(480).build()

        if (usuario !is Prestador)
            return ResponseEntity.status(403).body("Usuário não é um prestador")

        usuario.area = alterarPerfilDTO.area
        usuario.descricao = alterarPerfilDTO.descricao
        usuario.anexoPfp = alterarPerfilDTO.profile
        usuario.nome = alterarPerfilDTO.nome
        usuario.orcamentoMin = alterarPerfilDTO.orcamentoMin
        usuario.orcamentoMax = alterarPerfilDTO.orcamentoMax
        usuario.prestaAula = alterarPerfilDTO.prestaAula

        usuarioRepository.save(usuario)

        return ResponseEntity.ok("Perfil do usuário atualizado com sucesso")
    }

    fun checarPrestadorByToken(token: String?): ResponseEntity<PerfilDto> {

        val usuario = jwtTokenManager.validateToken(token)
            ?: return ResponseEntity.status(480).build()

        if (usuario !is Prestador)
            return ResponseEntity.status(403).build()

        return checarPrestador(usuario)
    }

    fun checarPrestadorById(id: Int): ResponseEntity<PerfilDto> {

        val usuario = usuarioRepository.findById(id)
            .orElse(null)
            ?: return ResponseEntity.status(404).build()

        if (usuario !is Prestador)
            return ResponseEntity.status(404).build()

        return checarPrestador(usuario)
    }

    fun checarPrestador(usuario: Prestador): ResponseEntity<PerfilDto> {

        val dadosEndereco = dadosEnderecoRepository.findByUsuarioId(usuario.id).get()
        val dadosAvaliacao = avaliacaoRepository.findByPrestadorUsuarioId(usuario.id)
        val notificacoes = solicitacaoRepository.findAllByUsuarioId(usuario.id)
        val nomeArea = areaRepository.findAreaNomeByUsuarioId(usuario.id)
        val urls = usuarioImgRepository.findUrlsByUsuarioId(usuario.id)
        val servicos = usuarioServicoRepository.findServicosByUsuarioId(usuario.id)

        val perfilDTO = PerfilDto(
            nomeArea,
            usuario.descricao,
            usuario.anexoPfp,
            usuario.nome,
            usuario.orcamentoMin,
            usuario.orcamentoMax,
            usuario.prestaAula,
            dadosEndereco.estado,
            dadosEndereco.cidade,
            urls,
            servicos,
            dadosAvaliacao,
            notificacoes
        )
        return ResponseEntity.status(200).body(perfilDTO)
    }

    fun atualizarSenha(token: String?, alterSenhaRequest: AlterSenhaRequest): ResponseEntity<Usuario> {

        // Checando se token foi expirado e então encontrando usuário por token
        val usuario = jwtTokenManager.validateToken(token)
            ?: return ResponseEntity.status(480).build()

        return if (passwordEncoder.matches(alterSenhaRequest.senhaAntiga, usuario.senha)) {

            usuario.senha = passwordEncoder.encode(alterSenhaRequest.senhaNova)
            ResponseEntity.status(200).body(usuarioRepository.save(usuario))

        } else {
            ResponseEntity.status(401).build()
        }
    }

    fun atualizarDesc(token: String?, alterDescRequest: AlterDescRequest): ResponseEntity<Usuario> {

        // Checando se token foi expirado e então encontrando usuário por token
        val usuario = jwtTokenManager.validateToken(token)
            ?: return ResponseEntity.status(480).build()

        if (usuario !is Prestador)
            return ResponseEntity.status(403).build()

        usuario.descricao = alterDescRequest.descricao
        return ResponseEntity.status(200).body(usuarioRepository.save(usuario))
    }

    fun deletar(token: String?): ResponseEntity<String> {

        // Checando se token foi expirado e então encontrando usuário por token
        val usuario = jwtTokenManager.validateToken(token)
            ?: return ResponseEntity.status(480).build()

        dadosEnderecoRepository.deleteByUsuarioId(usuario.id)
        usuarioRepository.deleteById(usuario.id)
        return ResponseEntity.status(200).body(null)

    }

    fun atualizarPFP(token: String?, alterPfpRequest: AlterPfpRequest): ResponseEntity<Usuario> {

        println("usuario.id")
        // Checando se token foi expirado e então encontrando usuário por token
        val usuario = jwtTokenManager.validateToken(token)
            ?: return ResponseEntity.status(480).build()

        println(usuario.id)
        if (usuario !is Prestador)
            return ResponseEntity.status(403).build()

        usuario.anexoPfp = alterPfpRequest.novaUrl
        return ResponseEntity.status(200).body(usuarioRepository.save(usuario))
    }

    fun getSolicitacoes(token: String?): ResponseEntity<List<NotificacaoDto>> {

        val usuario = jwtTokenManager.validateToken(token)
            ?: return ResponseEntity.status(480).build()

        if (usuario is Prestador) {
            val solicitacoes = solicitacaoRepository.findByPrestadorUsuarioIdOrderByIdDesc(usuario.id)
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
            val solicitacoes = solicitacaoRepository.findByContratanteUsuarioIdOrderByIdDesc(usuario.id)
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

    fun postarUrl(token: String?, urlPerfilDto: UrlPerfilDto): ResponseEntity<List<String>> {

        val usuario = jwtTokenManager.validateToken(token)
            ?: return ResponseEntity.status(480).build()

        urlPerfilDto.imagens.forEach {
            val usuarioImg = UsuarioImg()

            usuarioImg.usuario = usuarioRepository.findById(usuario.id).get() as Prestador
            usuarioImg.anexo = it

            usuarioImgRepository.save(usuarioImg)
        }
        return ResponseEntity.status(201).build()
    }

    fun excluirUrls(token: String?, urlPerfilDto: UrlPerfilDto): ResponseEntity<Int> {

        val usuario = jwtTokenManager.validateToken(token)
            ?: return ResponseEntity.status(480).build()

        urlPerfilDto.imagens.forEach {

            val teste = usuarioImgRepository.findByAnexoAndUsuarioId(
                it,
                usuarioRepository.findById(usuario.id).get().id
            )
            usuarioImgRepository.delete(teste.get())
        }
        return ResponseEntity.status(201).build()
    }
}