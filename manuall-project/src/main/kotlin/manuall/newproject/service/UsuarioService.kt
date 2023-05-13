package manuall.newproject.service

import manuall.newproject.domain.Usuario
import manuall.newproject.dto.AlterDescRequest
import manuall.newproject.dto.AlterSenhaRequest
import manuall.newproject.dto.CadastroRequest
import manuall.newproject.dto.UsuarioLoginDto
import manuall.newproject.repository.AreaUsuarioRepository
import manuall.newproject.repository.DadosEnderecoRepository
import manuall.newproject.repository.DescServicosRepository
import manuall.newproject.repository.UsuarioRepository
import manuall.newproject.security.JwtTokenManager
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.util.*

@Service
class UsuarioService (
    val passwordEncoder: PasswordEncoder,
    val jwtTokenManager: JwtTokenManager,
    val authenticationManager: AuthenticationManager,
    val usuarioRepository: UsuarioRepository,
    val dadosEnderecoRepository: DadosEnderecoRepository,
    val areaUsuarioRepository: AreaUsuarioRepository,
    val descServicosRepository: DescServicosRepository
) {

    fun login(usuarioLoginDto: UsuarioLoginDto): ResponseEntity<String> {

        // Pegando os usuários com o email requisitado em uma lista, já que podem
        // existir 2 usuários com o mesmo email e tipo_usuario diferentes
        val possiveisUsuarios = usuarioRepository.findByEmail(usuarioLoginDto.email)

        return if (possiveisUsuarios.isEmpty()) {
            ResponseEntity.status(204).build()
        } else if (possiveisUsuarios.size > 1) {
            ResponseEntity.status(409).body("Dois valores encontrados")
        } else {

            // Tendo em vista que existe apenas 1 usuário nesta lista, vamos simplificar e chamá-lo de "usuario"
            val usuario = possiveisUsuarios[0].get()

            // Autenticando sua senha, essa função decripta a senha do banco e a compara com a recebida
            if (passwordEncoder.matches(usuarioLoginDto.senha, usuario.senha)) {

                // Gerando token de sessão com base no tipo_usuario e email, para identificar unicamente cada login
                val authentication = authenticationManager.authenticate(
                    UsernamePasswordAuthenticationToken(
                        usuario.tipoUsuario.toString()+usuario.email,
                        usuarioLoginDto.senha
                    )
                )

                SecurityContextHolder.getContext().authentication = authentication
                ResponseEntity.status(200).body(jwtTokenManager.generateToken(authentication))

            } else {
                ResponseEntity.status(403).body("Senha incorreta")
            }

        }

    }

    fun criar(cadastroRequest: CadastroRequest): ResponseEntity<Void> {

        val usuarioCheck: Optional<Usuario> = usuarioRepository.findByEmailAndTipoUsuario(cadastroRequest.usuario.email, cadastroRequest.usuario.tipoUsuario)

        return if (usuarioCheck.isEmpty) {

            // Codificando a senha do usuário para enviar a senha encriptada ao banco
            cadastroRequest.usuario.senha = passwordEncoder.encode(cadastroRequest.usuario.senha.toString())

            // Inserindo usuário e pegando seu id para usar nos próximos inserts
            val usuarioAtual: Int = usuarioRepository.save(cadastroRequest.usuario).id

            // Inserindo vários dados na tabela area_usuario
            cadastroRequest.areaUsuario.forEach {
                it.usuario.id = usuarioAtual
                areaUsuarioRepository.save(it)
            }

            // Inserindo dados na tabela dados_endereco
            cadastroRequest.dadosEndereco.usuario.id = usuarioAtual
            dadosEnderecoRepository.save(cadastroRequest.dadosEndereco)

            // Inserindo vários dados na tabela desc_servicos
            cadastroRequest.descServicos.forEach {
                it.usuario.id = usuarioAtual
                descServicosRepository.save(it)
            }

            ResponseEntity.status(201).build()
        } else {
            ResponseEntity.status(409).build()
        }

    }

    fun atualizarSenha(token: String, alterSenhaRequest: AlterSenhaRequest): ResponseEntity<Usuario> {

        // Checando se token foi expirado e então encontrando usuário por token
        val usuarioEncontrado = if (jwtTokenManager.validarToken(token)) {
            jwtTokenManager.getUserFromToken(token)
        } else {
            return ResponseEntity.status(401).build()
        }

        return if (alterSenhaRequest.senhaAntiga == usuarioEncontrado.senha) {

            usuarioEncontrado.senha = alterSenhaRequest.senhaNova
            ResponseEntity.status(200).body(usuarioRepository.save(usuarioEncontrado))

        } else {
            ResponseEntity.status(401).build()
        }

    }

    fun atualizarDesc(token: String, alterDescRequest: AlterDescRequest): ResponseEntity<Usuario> {

        // Checando se token foi expirado e então encontrando usuário por token
        val usuarioEncontrado = if (jwtTokenManager.validarToken(token)) {
            jwtTokenManager.getUserFromToken(token)
        } else {
            return ResponseEntity.status(401).build()
        }

        usuarioEncontrado.descricao = alterDescRequest.descricao
        return ResponseEntity.status(200).body(usuarioRepository.save(usuarioEncontrado))

    }

    fun deletar(token: String): ResponseEntity<String> {

        // Checando se token foi expirado e então encontrando usuário por token
        val usuarioEncontrado = if (jwtTokenManager.validarToken(token)) {
            jwtTokenManager.getUserFromToken(token)
        } else {
            return ResponseEntity.status(401).body("Token expirado")
        }

        descServicosRepository.deleteByUsuarioId(usuarioEncontrado.id)
        areaUsuarioRepository.deleteByUsuarioId(usuarioEncontrado.id)
        dadosEnderecoRepository.deleteByUsuarioId(usuarioEncontrado.id)
        usuarioRepository.deleteById(usuarioEncontrado.id)
        return ResponseEntity.status(200).body(null)

    }
}