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

    fun buscarTodos(token: String?): ResponseEntity<List<Usuario>> {

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
            return ResponseEntity.status(403).build()

        return checarPrestador(usuario)
    }

    fun checarPrestador(usuario: Prestador): ResponseEntity<PerfilDto> {

        val imagens = usuarioImgRepository.findUrlsByUsuarioId(usuario.id)
        val servicos = usuarioServicoRepository.findServicosByUsuarioId(usuario.id)
        val avaliacoes = avaliacaoRepository.findByPrestadorId(usuario.id)

        val perfilDTO = PerfilDto(
            usuario.area?.nome,
            usuario.descricao,
            usuario.anexoPfp,
            usuario.nome,
            usuario.orcamentoMin,
            usuario.orcamentoMax,
            usuario.prestaAula,
            usuario.dadosEndereco?.estado,
            usuario.dadosEndereco?.cidade,
            imagens,
            servicos,
            avaliacoes
        )

        return ResponseEntity.status(200).body(perfilDTO)
    }

    fun atualizarSenha(token: String?, alterSenhaRequest: AlterSenhaRequest): ResponseEntity<Unit> {

        val usuario = jwtTokenManager.validateToken(token)
            ?: return ResponseEntity.status(480).build()

        return if (passwordEncoder.matches(alterSenhaRequest.senhaAntiga, usuario.senha)) {

            usuario.senha = passwordEncoder.encode(alterSenhaRequest.senhaNova)
            usuarioRepository.save(usuario)

            ResponseEntity.status(200).build()

        } else {
            ResponseEntity.status(401).build()
        }
    }

    fun atualizarDesc(token: String?, alterDescRequest: AlterDescRequest): ResponseEntity<Unit> {

        val usuario = jwtTokenManager.validateToken(token)
            ?: return ResponseEntity.status(480).build()

        if (usuario !is Prestador)
            return ResponseEntity.status(403).build()

        usuario.descricao = alterDescRequest.descricao
        usuarioRepository.save(usuario)

        return ResponseEntity.status(200).build()
    }

    fun deletar(token: String?): ResponseEntity<String> {

        // Checando se token foi expirado e então encontrando usuário por token
        val usuario = jwtTokenManager.validateToken(token)
            ?: return ResponseEntity.status(480).build()

        dadosEnderecoRepository.deleteByUsuarioId(usuario.id)
        usuarioRepository.deleteById(usuario.id)
        return ResponseEntity.status(200).body(null)

    }

    fun getPfp(token: String?): ResponseEntity<String> {

        val usuario = jwtTokenManager.validateToken(token)
            ?: return ResponseEntity.status(480).build()

        if (usuario !is Prestador)
            return ResponseEntity.status(403).build()

        return if (usuario.anexoPfp == null)
            ResponseEntity.status(404).build()
        else
            ResponseEntity.status(200).body(usuario.anexoPfp)
    }

    fun atualizarPFP(token: String?, alterPfpRequest: AlterPfpRequest): ResponseEntity<Unit> {

        val usuario = jwtTokenManager.validateToken(token)
            ?: return ResponseEntity.status(480).build()

        if (usuario !is Prestador)
            return ResponseEntity.status(403).build()

        usuario.anexoPfp = alterPfpRequest.novaUrl
        usuarioRepository.save(usuario)

        return ResponseEntity.status(200).build()
    }

    fun postarUrl(token: String?, anexo: UrlImagemDto): ResponseEntity<Unit> {

        val usuario = jwtTokenManager.validateToken(token)
            ?: return ResponseEntity.status(480).build()

        val usuarioImg = UsuarioImg()
        usuarioImg.prestador = usuarioRepository.findById(usuario.id).get() as Prestador
        usuarioImg.anexo = anexo.imagem
        usuarioImgRepository.save(usuarioImg)

        return ResponseEntity.status(201).build()
    }

    fun excluirUrls(token: String?, imagemId: Int): ResponseEntity<Unit> {

        jwtTokenManager.validateToken(token)
            ?: return ResponseEntity.status(480).build()

        usuarioImgRepository.delete(
            usuarioImgRepository.findById(imagemId).get()
        )

        return ResponseEntity.status(200).build()
    }
}