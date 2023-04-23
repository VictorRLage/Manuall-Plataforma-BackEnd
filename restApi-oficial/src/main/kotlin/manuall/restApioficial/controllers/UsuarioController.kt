package manuall.restApioficial.controllers

import manuall.restApioficial.dtos.*
import manuall.restApioficial.models.Usuario
import manuall.restApioficial.repositories.*
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*
import javax.transaction.Transactional

@RestController
@RequestMapping("/usuarios")
class UsuarioController(
    val usuarioRepository: UsuarioRepository,
    val dadosBancariosRepository: DadosBancariosRepository,
    val dadosEnderecoRepository: DadosEnderecoRepository,
    val areaUsuarioRepository: AreaUsuarioRepository,
    val descServicosRepository: DescServicosRepository,
    //val authenticationManager: AuthenticationManager,
    //val jwtProvider: JwtProvider
) {

    @GetMapping("/findByEmail/{email}")
    fun findByEmail(@PathVariable email: String): List<Usuario?> {
        return usuarioRepository.findByEmail(email)
    }

    @GetMapping("/findByEmailAndTipoUsuario/{email}/{tipoUsuario}")
    fun findByEmailAndTipoUsuario(@PathVariable email: String, @PathVariable tipoUsuario: Int): Usuario? {
        return usuarioRepository.findByEmailAndTipoUsuario(email, tipoUsuario)
    }

    @GetMapping("/{id}")
    fun getById(@PathVariable id: Int): ResponseEntity<Optional<Usuario>> {
        return ResponseEntity.status(200).body(usuarioRepository.findById(id))
    }

    @PostMapping("/login-padrao")
    fun login(@RequestBody loginRequest: LoginRequest): ResponseEntity<LoginResponse> {

        val buscandoLogins: List<Usuario> = usuarioRepository.findByEmailAndSenha(loginRequest.email, loginRequest.senha)

        return if (buscandoLogins.isEmpty()) {
            ResponseEntity.status(401).body(LoginResponse(0, null))
        } else if (buscandoLogins.size == 1) {
            ResponseEntity.status(200).body(LoginResponse(1, buscandoLogins))
        } else {
            ResponseEntity.status(200).body(LoginResponse(2, buscandoLogins))
        }
    }

    @PostMapping("/cadastrar")
    fun cadastro(@RequestBody cadastroRequest: CadastroRequest):ResponseEntity<Void> {

        val usuarioCheck: Usuario? = usuarioRepository.findByEmailAndTipoUsuario(cadastroRequest.usuario.email, cadastroRequest.usuario.tipoUsuario)

        return if (usuarioCheck == null) {
            val usuarioAtual:Usuario = usuarioRepository.save(cadastroRequest.usuario)
            cadastroRequest.areaUsuario.usuario.id = usuarioAtual.id
            cadastroRequest.dadosBancarios.usuario.id = usuarioAtual.id
            cadastroRequest.dadosEndereco.usuario.id = usuarioAtual.id
            cadastroRequest.descServicos.usuario.id = usuarioAtual.id
            areaUsuarioRepository.save(cadastroRequest.areaUsuario)
            dadosBancariosRepository.save(cadastroRequest.dadosBancarios)
            dadosEnderecoRepository.save(cadastroRequest.dadosEndereco)
            descServicosRepository.save(cadastroRequest.descServicos)
            ResponseEntity.status(201).build()
        } else {
            ResponseEntity.status(409).build()
        }
    }

    @PatchMapping("/alterar/senha")
    fun atualizarSenha(@RequestBody alterSenhaRequest: AlterSenhaRequest): ResponseEntity<Usuario?> {
        val usuario: Usuario =
            usuarioRepository.findById(alterSenhaRequest.id).orElseThrow()
        usuario.senha = alterSenhaRequest.senha
        return ResponseEntity.status(200).body(usuarioRepository.save(usuario))
    }

    @PatchMapping("/alterar/desc")
    fun atualizarDesc(@RequestBody alterDescRequest: AlterDescRequest): ResponseEntity<Usuario?> {
        val usuario: Usuario =
            usuarioRepository.findById(alterDescRequest.id).orElseThrow()
        usuario.descricao = alterDescRequest.descricao
        return ResponseEntity.status(200).body(usuarioRepository.save(usuario))
    }

    @DeleteMapping("/deletar/{id}")
    @Transactional
    fun deletar(@PathVariable id: Int): ResponseEntity<Void> {
        descServicosRepository.deleteByUsuarioId(id)
        dadosBancariosRepository.deleteByUsuarioId(id)
        areaUsuarioRepository.deleteByUsuarioId(id)
        dadosEnderecoRepository.deleteByUsuarioId(id)
        usuarioRepository.deleteById(id)
        return ResponseEntity.status(200).body(null)
    }

//    @PostMapping("/login-jwt")
//    fun loginJwt(@RequestBody loginRequest: LoginRequest): ResponseEntity<String> {
//
//        println("teste")
//
////        val credentials: UsernamePasswordAuthenticationToken = UsernamePasswordAuthenticationToken(
////            loginRequest.email,
////            loginRequest.senha
////        )
////
////        val authentication: Authentication = this.authenticationManager.authenticate(credentials)
////
////        val usuarioAutenticado: Usuario =
////            usuarioRepository.findByEmail(loginRequest.email)
////                ?: throw ResponseStatusException(404, "Nenhum email encontrado neste endereço", null)
////
////        SecurityContextHolder.getContext().authentication = authentication
////
////        val token: String = jwtProvider.generateToken(authentication)
//
//        return ResponseEntity.status(200).body("a")
//    }
}