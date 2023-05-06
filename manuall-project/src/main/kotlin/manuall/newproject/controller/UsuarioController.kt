package manuall.newproject.controller

import io.swagger.v3.oas.annotations.security.SecurityRequirement
import jakarta.transaction.Transactional
import manuall.newproject.dto.AlterDescRequest
import manuall.newproject.dto.AlterSenhaRequest
import manuall.newproject.dto.CadastroRequest
import manuall.newproject.dto.UsuarioLoginDto
import manuall.newproject.domain.Usuario
import manuall.newproject.repository.*
import manuall.newproject.security.JwtTokenManager
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/usuarios")
class UsuarioController (
    val passwordEncoder: PasswordEncoder,
    val jwtTokenManager: JwtTokenManager,
    val authenticationManager: AuthenticationManager,
    val usuarioRepository: UsuarioRepository,
    val dadosBancariosRepository: DadosBancariosRepository,
    val dadosEnderecoRepository: DadosEnderecoRepository,
    val areaUsuarioRepository: AreaUsuarioRepository,
    val descServicosRepository: DescServicosRepository
) {

    @PostMapping("/login")
    fun login(@RequestBody usuarioLoginDto: UsuarioLoginDto): ResponseEntity<String> {

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

    @PostMapping("/cadastrar")
    fun criar(@RequestBody cadastroRequest: CadastroRequest): ResponseEntity<Void> {

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

            // Inserindo dados na tabela dados_bancarios
            cadastroRequest.dadosBancarios.usuario.id = usuarioAtual
            dadosBancariosRepository.save(cadastroRequest.dadosBancarios)

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

    @SecurityRequirement(name = "Bearer")
    @PatchMapping("/alterar/senha")
    fun atualizarSenha(@RequestHeader("Authorization") token: String, @RequestBody alterSenhaRequest: AlterSenhaRequest): ResponseEntity<Usuario> {

        // Decriptando token e encontrando usuário
        val decriptacaoToken = jwtTokenManager.getUsernameFromToken(token.substring(7))
        val usuarioEncontrado = usuarioRepository.findByEmailAndTipoUsuario(decriptacaoToken.substring(1), decriptacaoToken.substring(0,1).toInt()).get()

        return if (alterSenhaRequest.senhaAntiga == usuarioEncontrado.senha) {

            usuarioEncontrado.senha = alterSenhaRequest.senhaNova
            ResponseEntity.status(200).body(usuarioRepository.save(usuarioEncontrado))

        } else {
            ResponseEntity.status(401).build()
        }
    }

    @SecurityRequirement(name = "Bearer")
    @PatchMapping("/alterar/desc")
    fun atualizarDesc(@RequestHeader("Authorization") token: String, @RequestBody alterDescRequest: AlterDescRequest): ResponseEntity<Usuario> {

        // Decriptando token e encontrando usuário
        val decriptacaoToken = jwtTokenManager.getUsernameFromToken(token.substring(7))
        val usuarioEncontrado = usuarioRepository.findByEmailAndTipoUsuario(decriptacaoToken.substring(1), decriptacaoToken.substring(0,1).toInt()).get()

        usuarioEncontrado.descricao = alterDescRequest.descricao
        return ResponseEntity.status(200).body(usuarioRepository.save(usuarioEncontrado))
    }

    @Transactional
    @SecurityRequirement(name = "Bearer")
    @DeleteMapping("/deletar")
    fun deletar(@RequestHeader("Authorization") token: String): ResponseEntity<Void> {

        // Decriptando token e encontrando usuário
        val decriptacaoToken = jwtTokenManager.getUsernameFromToken(token.substring(7))
        val usuarioEncontrado = usuarioRepository.findByEmailAndTipoUsuario(decriptacaoToken.substring(1), decriptacaoToken.substring(0,1).toInt()).get()

        descServicosRepository.deleteByUsuarioId(usuarioEncontrado.id)
        dadosBancariosRepository.deleteByUsuarioId(usuarioEncontrado.id)
        areaUsuarioRepository.deleteByUsuarioId(usuarioEncontrado.id)
        dadosEnderecoRepository.deleteByUsuarioId(usuarioEncontrado.id)
        usuarioRepository.deleteById(usuarioEncontrado.id)
        return ResponseEntity.status(200).body(null)
    }


    @GetMapping("/checar/{email}")
    fun findByEmail(@PathVariable email: String): List<Optional<Usuario>> {
        return usuarioRepository.findByEmail(email)
    }

    @GetMapping("/checar/{email}/{tipoUsuario}")
    fun findByEmailAndTipoUsuario(@PathVariable email: String, @PathVariable tipoUsuario: Int): Usuario? {
        return usuarioRepository.findByEmailAndTipoUsuario(email, tipoUsuario).get()
    }

    @GetMapping("/example/sem-token")
    fun getTestandoSemToken(): ResponseEntity<Void> {
        return ResponseEntity.status(200).build()
    }

    @SecurityRequirement(name = "Bearer")
    @GetMapping("/example/com-token")
    fun getTestandoComToken(@RequestHeader("Authorization") token: String): ResponseEntity<String> {
        return ResponseEntity.status(200).body(jwtTokenManager.getUsernameFromToken(token.substring(7)))
    }
}