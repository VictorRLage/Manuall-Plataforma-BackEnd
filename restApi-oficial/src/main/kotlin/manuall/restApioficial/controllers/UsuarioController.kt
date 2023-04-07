package manuall.restApioficial.controllers

import manuall.restApioficial.models.Usuario
import manuall.restApioficial.services.UsuarioService
import org.springframework.http.ResponseEntity
import org.springframework.http.ResponseEntity.status
import org.springframework.web.bind.annotation.*
import java.util.Optional

@RestController
@RequestMapping("/usuarios")
class UsuarioController(val usuarioService: UsuarioService) {

    // Primeiro vou listar as solicitações que REALMENTE precisam vir da tabela usuário, e depois mostrar
    // algumas que eu fiz como demonstração básica

    @GetMapping
    fun listarUsuarios(): ResponseEntity<List<Usuario>> {
        TODO()
    }

    @GetMapping("/{id}")
    fun buscarUsuarioPorId(@PathVariable id: Int): ResponseEntity<Usuario> {
        TODO()
    }

    @PostMapping("/login")
    fun logar(@RequestBody /* vai precisar de uma DTO de login pra isso aq */ usuario: Usuario): ResponseEntity<Usuario> {
        TODO()
    }

    @PostMapping("/cadastrar")
    fun cadastrarUsuario(@RequestBody usuario: Usuario): ResponseEntity<Usuario> {
        TODO()
    }

    @PatchMapping("/aprovar/{id}")
    fun aprovarUsuario(@PathVariable id: Int): ResponseEntity<Usuario> {
        TODO()
    }

    // agora as DEMO
    // Utilizando as funções que o Repository traz por padrão

    @GetMapping("/listarDemo")
    fun listarUsuariosDemo(): ResponseEntity<List<Usuario>> {
        val usuarios = usuarioService.getAllUsuarios()
        return if (usuarios.isEmpty()) {
            status(204).body(usuarios)
        } else {
            status(200).body(usuarios)
        }
    }

    @GetMapping("/{id}")
    fun buscarUsuarioPorIdDemo(@PathVariable id: Int): ResponseEntity<Optional<Usuario>> {
        val usuario = usuarioService.getUsuarioById(id)
        return status(200).body(usuario)
    }

    @PostMapping
    fun criarUsuario(@RequestBody usuario: Usuario): ResponseEntity<Usuario> {
        val usuarioSalva = usuarioService.createUsuario(usuario)
        return status(201).body(usuarioSalva)
    }

    @PutMapping("/{id}")
    fun atualizarUsuario(@PathVariable id: Int, @RequestBody usuario: Usuario): ResponseEntity<Optional<Usuario>> {
        val usuarioAtualizada = usuarioService.updateUsuario(id, usuario)
        return status(200).body(usuarioAtualizada)
    }

    @DeleteMapping("/{id}")
    fun deletarUsuario(@PathVariable id: Int): ResponseEntity<Void> {
        usuarioService.deleteUsuario(id)
        return status(204).body(null)
    }

    // Utilizando as funções que eu fiz no Repository

    @GetMapping("/aprovados")
    fun getUsuariosAprovados(): ResponseEntity<List<Usuario>> {
        val usuarios = usuarioService.getUsuariosAprovados()
        return if (usuarios.isEmpty()) {
            status(204).body(usuarios)
        } else {
            status(200).body(usuarios)
        }
    }

    @GetMapping("/aprovados/alfabetica")
    fun getUsuariosAprovadosAlfabeticamente(): ResponseEntity<List<Usuario>> {
        val usuarios = usuarioService.getUsuariosAprovadosOrdemAlfabetica()
        return if (usuarios.isEmpty()) {
            status(204).body(usuarios)
        } else {
            status(200).body(usuarios)
        }
    }
}