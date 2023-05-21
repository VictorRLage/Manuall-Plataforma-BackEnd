package manuall.newproject.controller

import manuall.newproject.domain.UsuarioServico
import manuall.newproject.service.SolicitacaoService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/solicitacoes")
@CrossOrigin("http://localhost:3000")
data class SolicitacaoController (
    val solicitacaoService: SolicitacaoService
) {

    @GetMapping("/servicos/{idPrestador}")
    fun getServicosPrestadorPorPrestador(@PathVariable idPrestador: Int): ResponseEntity<List<Int>> {
        return solicitacaoService.getServicosPrestadorPorPrestador(idPrestador)
    }
}