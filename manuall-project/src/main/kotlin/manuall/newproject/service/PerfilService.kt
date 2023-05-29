package manuall.newproject.service

import jakarta.transaction.Transactional
import manuall.newproject.domain.Usuario
import manuall.newproject.domain.UsuarioImg
import manuall.newproject.dto.perfil.*
import manuall.newproject.dto.usuario.AlterPfpRequest
import manuall.newproject.repository.*
import manuall.newproject.security.JwtTokenManager
import org.springframework.http.ResponseEntity
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class PerfilService(
    val jwtTokenManager: JwtTokenManager,
    val passwordEncoder: PasswordEncoder,
    val usuarioRepository: UsuarioRepository,
    val descServicosRepository: DescServicosRepository,
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

        prestadorEncontrado.acessos = prestadorEncontrado.acessos!! + 1

        usuarioRepository.save(prestadorEncontrado)

        return ResponseEntity.status(200).build()
    }

    fun alterarPerfil(token: String, alterarPerfilDTO: AlterarPerfilDto): ResponseEntity<String> {
        val usuarioEncontrado = if (jwtTokenManager.validarToken(token)) {
            jwtTokenManager.getUserFromToken(token) ?: return ResponseEntity.status(480).build()
        } else {
            return ResponseEntity.status(480).build()
        }

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
        val usuarioEncontrado = if (jwtTokenManager.validarToken(token)) {
            jwtTokenManager.getUserFromToken(token) ?: return ResponseEntity.status(480).build()
        } else {
            return ResponseEntity.status(480).build()
        }

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
        val usuarioEncontrado = if (jwtTokenManager.validarToken(token)) {
            jwtTokenManager.getUserFromToken(token) ?: return ResponseEntity.status(480).build()
        } else {
            return ResponseEntity.status(480).build()
        }

        return if (passwordEncoder.matches(alterSenhaRequest.senhaAntiga, usuarioEncontrado.senha)) {

            usuarioEncontrado.senha = passwordEncoder.encode(alterSenhaRequest.senhaNova)
            ResponseEntity.status(200).body(usuarioRepository.save(usuarioEncontrado))

        } else {
            ResponseEntity.status(401).build()
        }
    }

    fun atualizarDesc(token: String, alterDescRequest: AlterDescRequest): ResponseEntity<Usuario> {

        // Checando se token foi expirado e então encontrando usuário por token
        val usuarioEncontrado = if (jwtTokenManager.validarToken(token)) {
            jwtTokenManager.getUserFromToken(token) ?: return ResponseEntity.status(480).build()
        } else {
            return ResponseEntity.status(480).build()
        }

        usuarioEncontrado.descricao = alterDescRequest.descricao
        return ResponseEntity.status(200).body(usuarioRepository.save(usuarioEncontrado))
    }

    fun deletar(token: String): ResponseEntity<String> {

        // Checando se token foi expirado e então encontrando usuário por token
        val usuarioEncontrado = if (jwtTokenManager.validarToken(token)) {
            jwtTokenManager.getUserFromToken(token) ?: return ResponseEntity.status(480).build()
        } else {
            return ResponseEntity.status(480).body("Token expirado")
        }

        descServicosRepository.deleteByUsuarioId(usuarioEncontrado.id)
        dadosEnderecoRepository.deleteByUsuarioId(usuarioEncontrado.id)
        usuarioRepository.deleteById(usuarioEncontrado.id)
        return ResponseEntity.status(200).body(null)

    }

    fun atualizarPFP(token: String, alterPfpRequest: AlterPfpRequest): ResponseEntity<Usuario> {

        // Checando se token foi expirado e então encontrando usuário por token
        val usuarioEncontrado = if (jwtTokenManager.validarToken(token)) {
            jwtTokenManager.getUserFromToken(token) ?: return ResponseEntity.status(480).build()
        } else {
            return ResponseEntity.status(480).build()
        }

        usuarioEncontrado.anexoPfp = alterPfpRequest.novaUrl
        return ResponseEntity.status(200).body(usuarioRepository.save(usuarioEncontrado))
    }

    fun getSolicitacoes(token: String): ResponseEntity<List<NotificacaoDto>> {

        val usuarioEncontrado = if (jwtTokenManager.validarToken(token)) {
            jwtTokenManager.getUserFromToken(token) ?: return ResponseEntity.status(480).build()
        } else {
            return ResponseEntity.status(480).build()
        }

        if (usuarioEncontrado.tipoUsuario == 1) {
            val solicitacoes = solicitacaoRepository.findByContratanteUsuarioIdOrderByIdDesc(usuarioEncontrado.id)
            val algo = mutableListOf<NotificacaoDto>()
            solicitacoes.forEach {
                algo.add(
                    NotificacaoDto(
                        it.prestadorUsuario.nome!!,
                        if (it.status == 1) "Você enviou uma solicitação para " else if (it.status == 2) "Sua solicitação foi aceita por " else "Sua solicitação foi recusada por ",
                        it.contratanteUsuario.anexoPfp!!
                    )
                )
            }
            return ResponseEntity.status(200).body(algo)
        } else {
            val solicitacoes = solicitacaoRepository.findByPrestadorUsuarioIdOrderByIdDesc(usuarioEncontrado.id)
            val algo = mutableListOf<NotificacaoDto>()
            solicitacoes.forEach {
                algo.add(
                    NotificacaoDto(
                        it.contratanteUsuario.nome!!,
                        if (it.status == 1) "Você recebeu uma solicitação de " else if (it.status == 2) "Você aceitou a solicitação de " else "Você recusou a solicitação de ",
                        it.contratanteUsuario.anexoPfp!!
                    )
                )
            }
            return ResponseEntity.status(200).body(algo)
        }
    }

    fun postarUrl(token: String, urlPerfilDto: urlPerfilDto): ResponseEntity<List<String>> {

        val usuarioEncontrado = if (jwtTokenManager.validarToken(token)) {
            jwtTokenManager.getUserFromToken(token) ?: return ResponseEntity.status(480).build()
        } else {
            return ResponseEntity.status(480).build()
        }

        urlPerfilDto.imagens.forEach {
            val usuarioImg = UsuarioImg()

            usuarioImg.usuario = usuarioRepository.findById(usuarioEncontrado.id).get()
            usuarioImg.anexo = it

            usuarioImgRepository.save(usuarioImg)
        }
        return ResponseEntity.status(201).build()
    }

    fun excluirUrls(token: String, urlPerfilDto: urlPerfilDto): ResponseEntity<Int> {

        val usuarioEncontrado = if (jwtTokenManager.validarToken(token)) {
            jwtTokenManager.getUserFromToken(token) ?: return ResponseEntity.status(480).build()
        } else {
            return ResponseEntity.status(480).build()
        }

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