package manuall.restApioficial.controllers

import manuall.restApioficial.models.Solicitacao
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping("/solicitacao")
class SolicitacaoController {

    @GetMapping("/listar")
    fun listarSolicitacoes(): ResponseEntity<List<Solicitacao>> {
        TODO()
    }

    @GetMapping("/{id}")
    fun buscarSolicitacaoPorId(@PathVariable id: Int): ResponseEntity<Solicitacao> {
        TODO()
    }

    @GetMapping("/{idSolicitacao}")
    fun buscarSolicitacoesPorIdSolicitacao(@PathVariable idSolicitacao: Int): ResponseEntity<List<Solicitacao>> {
        TODO()
    }

    @PostMapping
    fun enviarSolicitacao(@RequestBody solicitacao: Solicitacao): ResponseEntity<Solicitacao> {
        TODO()
    }

    @PatchMapping
    fun atualizarAprovacao(): ResponseEntity<Void> {
        TODO()
    }
}