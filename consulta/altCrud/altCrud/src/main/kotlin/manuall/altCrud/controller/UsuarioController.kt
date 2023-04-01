package manuall.altCrud.controller

import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
import manuall.altCrud.dao.DAO
import manuall.altCrud.dao.UsuarioJdbcDAO
import manuall.altCrud.dto.UpdateSenhaRequest
import manuall.altCrud.dto.Usuario
import manuall.altCrud.dto.UsuarioResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException

@Api(tags = ["Usuário"])
@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping("/usuarios")
class UsuarioController {

    val dao: DAO<Usuario> = UsuarioJdbcDAO()

    @ApiOperation("Cadastrar usuário")
    @ApiResponses(value = [
        ApiResponse(code = 201, message = "Usuário cadastrado com sucesso"),
        ApiResponse(code = 500, message = "Erro no cadastro")
    ])
    @PostMapping("/cadastrar")
    fun cadastrar(@RequestBody usuario: Usuario): ResponseEntity<UsuarioResponse> {
        if (dao.create(usuario) == null) {
            throw ResponseStatusException(500, "Erro no cadastro", null)
        }
        return ResponseEntity.status(201).body(UsuarioResponse(usuario))
    }

    @ApiOperation("Listar usuários")
    @ApiResponses(value = [
        ApiResponse(code = 200, message = "Usuários listados com sucesso"),
        ApiResponse(code = 204, message = "Nenhum usuário encontrado")
    ])
    @GetMapping("/listar")
    fun listar():ResponseEntity<List<Usuario>> {
        val usuarios = dao.read()
        return if (usuarios.isEmpty()) {
            ResponseEntity.status(204).body(usuarios)
        } else {
            ResponseEntity.status(200).body(usuarios)
        }
    }

    @ApiOperation("Alterar usuário")
    @ApiResponses(value = [
        ApiResponse(code = 200, message = "Usuário alterado com sucesso"),
        ApiResponse(code = 500, message = "Erro na alteração")
    ])
    @PutMapping("/alterar/{id}")
    fun alterar(@PathVariable id: Int, @RequestBody usuario: Usuario): ResponseEntity<UsuarioResponse> {
        val usuarioAlterado = dao.update(usuario, id) ?:
        throw ResponseStatusException(500, "Erro na alteração", null)
        return ResponseEntity.status(200).body(UsuarioResponse(usuarioAlterado))
    }

    @ApiOperation("Alterar senha do usuário")
    @ApiResponses(value = [
        ApiResponse(code = 200, message = "Senha do usuário alterada com sucesso"),
        ApiResponse(code = 401, message = "Senha errada"),
        ApiResponse(code = 404, message = "ID não corresponde a nenhum usuário"),
        ApiResponse(code = 500, message = "Erro na alteração")
    ])
    @PatchMapping("/alterarSenha/{id}")
    fun alterarSenha(@PathVariable id: Int, @RequestBody senhas: UpdateSenhaRequest): ResponseEntity<Usuario> {
        val usuarioAtual = dao.findId(id)
        if (usuarioAtual == null) {
            throw ResponseStatusException(404, "ID não corresponde a nenhum usuário", null)
        } else {
            if (usuarioAtual.senha == senhas.senhaAtual) {
                if (dao.update(Usuario(
                        0,
                        usuarioAtual.nome,
                        usuarioAtual.email,
                        senhas.senhaNova
                    ), id) == null) {
                    throw ResponseStatusException(500, "Erro na alteração", null)
                } else {
                    return ResponseEntity.status(200).body(dao.findId(id))
                }
            } else {
                throw ResponseStatusException(401, "Senha errada", null)
            }
        }
    }

    @ApiOperation("Deletar usuário")
    @ApiResponses(value = [
        ApiResponse(code = 200, message = "Usuário deletado com sucesso"),
        ApiResponse(code = 500, message = "Erro na exclusão")
    ])
    @DeleteMapping("/deletar/{id}")
    fun deletar(@PathVariable id: Int): ResponseEntity<Int> {
        val usuarioDeletado = dao.delete(id) ?:
        throw ResponseStatusException(500, "Erro na exclusão", null)
        return ResponseEntity.status(200).body(usuarioDeletado)
    }
}