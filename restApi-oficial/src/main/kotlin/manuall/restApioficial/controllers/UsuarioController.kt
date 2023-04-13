package manuall.restApioficial.controllers

import manuall.restApioficial.models.Usuario
import manuall.restApioficial.repositories.*
import org.springframework.http.ResponseEntity
import org.springframework.http.ResponseEntity.status
import org.springframework.web.bind.annotation.*
import java.util.Optional

@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping("/usuarios")
class UsuarioController(
        val usuarioRepository: UsuarioRepository,
        val dadosBancariosRepository: DadosBancariosRepository,
        val avaliacaoRepository: AvaliacaoRepository,
        val solicitacaoRepository: SolicitacaoRepository,
        val imagemRepository: ImagemRepository,
        val enderecoRepository: EnderecoRepository,
        val areaRepository: AreaRepository
) {

    @GetMapping
    fun get():String {
        return "Safe"
    }
}