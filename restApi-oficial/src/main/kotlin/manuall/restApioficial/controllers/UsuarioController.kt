package manuall.restApioficial.controllers

import manuall.restApioficial.models.Usuario
import manuall.restApioficial.repositories.UsuarioRepository
import org.springframework.http.ResponseEntity
import org.springframework.http.ResponseEntity.status
import org.springframework.web.bind.annotation.*
import java.util.Optional

@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping("/usuarios")
class UsuarioController(val repository: UsuarioRepository) {

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

    @GetMapping("/demo/listarDemo")
    fun listarUsuariosDemo(): ResponseEntity<List<Usuario>> {
        val usuarios = repository.findAll()
        return if (usuarios.isEmpty()) {
            status(204).body(usuarios)
        } else {
            status(200).body(usuarios)
        }
    }

    @GetMapping("/demo/{id}")
    fun buscarUsuarioPorIdDemo(@PathVariable id: Int): ResponseEntity<Optional<Usuario>> {
        val usuario = repository.findById(id)
        return status(200).body(usuario)
    }

    @PostMapping
    fun criarUsuario(@RequestBody usuario: Usuario): ResponseEntity<Usuario> {
        val usuarioSalva = repository.save(usuario)
        return status(201).body(usuarioSalva)
    }

    @PutMapping("/demo/{id}")
    fun atualizarUsuario(@PathVariable id: Int, @RequestBody usuario: Usuario): ResponseEntity<Optional<Usuario>> {
        return status(200).body(
            if (repository.findById(id).isPresent) {
                usuario.idUsuario = id
                Optional.of(repository.save(usuario))
            } else {
                Optional.empty()
            }
        )
    }

    @DeleteMapping("/demo/{id}")
    fun deletarUsuario(@PathVariable id: Int): ResponseEntity<Void> {
        repository.deleteById(id)
        return status(204).body(null)
    }

    // Utilizando as funções que eu fiz no Repository

    @GetMapping("/demo/aprovados")
    fun getUsuariosAprovados(): ResponseEntity<List<Usuario>> {
        val usuarios = repository.findByAprovadoTrue()
        return if (usuarios.isEmpty()) {
            status(204).body(usuarios)
        } else {
            status(200).body(usuarios)
        }
    }

    @GetMapping("/demo/aprovados/alfabetica")
    fun getUsuariosAprovadosAlfabeticamente(): ResponseEntity<List<Usuario>> {
        val usuarios = repository.findByAprovadoTrueOrderByNomeDesc()
        return if (usuarios.isEmpty()) {
            status(204).body(usuarios)
        } else {
            status(200).body(usuarios)
        }
    }
}