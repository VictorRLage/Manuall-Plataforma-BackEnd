package manuall.newproject.controller

import io.swagger.v3.oas.annotations.security.SecurityRequirement
import jakarta.transaction.Transactional
import manuall.newproject.domain.Area
import manuall.newproject.domain.TipoServico
import manuall.newproject.domain.Usuario
import manuall.newproject.dto.*
import manuall.newproject.repository.*
import manuall.newproject.service.UsuarioService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/usuarios")
class UsuarioController (
    val usuarioService: UsuarioService
) {

    @PostMapping("/login")
    fun login(@RequestBody usuarioLoginRequest: UsuarioLoginRequest): ResponseEntity<Any> {
        return usuarioService.login(usuarioLoginRequest)
    }

    @SecurityRequirement(name = "Bearer")
    @PostMapping("/logoff")
    fun logoff(@RequestHeader("Authorization") token: String): ResponseEntity<Void> {
        return usuarioService.logoff(token)
    }

    @SecurityRequirement(name = "Bearer")
    @PatchMapping("/alterar/senha")
    fun atualizarSenha(@RequestHeader("Authorization") token: String, @RequestBody alterSenhaRequest: AlterSenhaRequest): ResponseEntity<Usuario> {
        return usuarioService.atualizarSenha(token, alterSenhaRequest)
    }

    @SecurityRequirement(name = "Bearer")
    @PatchMapping("/alterar/desc")
    fun atualizarDesc(@RequestHeader("Authorization") token: String, @RequestBody alterDescRequest: AlterDescRequest): ResponseEntity<Usuario> {
        return usuarioService.atualizarDesc(token, alterDescRequest)
    }

    @Transactional
    @SecurityRequirement(name = "Bearer")
    @DeleteMapping("/deletar")
    fun deletar(@RequestHeader("Authorization") token: String): ResponseEntity<String> {
        return usuarioService.deletar(token)
    }

    @PostMapping("/cadastrar/1")
    fun cadastrar1(@RequestBody cadastrar1DTO: Cadastrar1DTO ): ResponseEntity<Int> {
        return usuarioService.cadastrar1(cadastrar1DTO)
    }

    @PutMapping("/cadastrar/2/contratante/{id}")
    fun cadastrar2Cont(@PathVariable id: Int, @RequestBody cadastrar2ContDTO: Cadastrar2ContDTO): ResponseEntity<String> {
        return usuarioService.cadastrar2Cont(id, cadastrar2ContDTO)
    }

    @PutMapping("/cadastrar/2/prestador/{id}")
    fun cadastrar2Prest(@PathVariable id: Int, @RequestBody cadastrar2PrestDTO: Cadastrar2PrestDTO): ResponseEntity<String> {
        return usuarioService.cadastrar2Prest(id, cadastrar2PrestDTO)
    }

    @GetMapping("/cadastrar/3/prestador/areas")
    fun buscarArea(): List<Area> {
        return usuarioService.buscarArea()
    }

    @GetMapping("/cadastrar/3/prestador/servicos/{id}")
    fun buscarTiposServico(@PathVariable id:Int): List<TipoServico> {
        return usuarioService.buscarTiposServico(id)
    }
//    @PutMapping("/cadastrar/3/prestador/{id}")
//    fun cadastrar3Prest(@PathVariable id:Int,@RequestBody) {
//
//    }

}