package manuall.newproject.service

import manuall.newproject.domain.Usuario
import manuall.newproject.dto.AlterDescRequest
import manuall.newproject.dto.AlterSenhaRequest
import manuall.newproject.dto.AlterarPerfilDTO
import manuall.newproject.dto.PerfilDTO
import manuall.newproject.repository.*
import manuall.newproject.security.JwtTokenManager
import org.springframework.http.ResponseEntity
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class PerfilService (
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

    fun alterarPerfil(token: String, alterarPerfilDTO: AlterarPerfilDTO): ResponseEntity<String> {
        val usuarioEncontrado = if (jwtTokenManager.validarToken(token)) {
            jwtTokenManager.getUserFromToken(token) ?: return ResponseEntity.status(480).build()
        } else {
            return ResponseEntity.status(480).build()
        }

        usuarioEncontrado.area = alterarPerfilDTO.area
        usuarioEncontrado.descricao = alterarPerfilDTO.descricao
        usuarioEncontrado.anexoPfp = alterarPerfilDTO.profile
        usuarioEncontrado.nome = alterarPerfilDTO.nome
        usuarioEncontrado.orcamentoMin = alterarPerfilDTO.orcamento_min
        usuarioEncontrado.orcamentoMax = alterarPerfilDTO.orcamento_max
        usuarioEncontrado.prestaAula = alterarPerfilDTO.presta_aula

        usuarioRepository.save(usuarioEncontrado)

        return ResponseEntity.ok("Perfil do usuário atualizado com sucesso")
    }

    fun checarPrestador(token: String): ResponseEntity<PerfilDTO> {
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

        val perfilDTO = PerfilDTO(
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
}