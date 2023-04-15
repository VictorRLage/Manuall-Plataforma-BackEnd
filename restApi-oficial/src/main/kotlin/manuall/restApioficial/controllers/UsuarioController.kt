package manuall.restApioficial.controllers

import manuall.restApioficial.dtos.AlterSenhaRequest
import manuall.restApioficial.dtos.CadastroRequest
import manuall.restApioficial.models.Usuario
import manuall.restApioficial.repositories.*
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping("/usuarios")
class UsuarioController(
    val usuarioRepository: UsuarioRepository,
    val dadosBancariosRepository: DadosBancariosRepository,
    val dadosEnderecoRepository: DadosEnderecoRepository,
    val areaUsuarioRepository: AreaUsuarioRepository,
    val descServicosRepository: DescServicosRepository
) {

    @PostMapping("/cadastrar")
    fun cadastro(@RequestBody cadastroRequest: CadastroRequest):ResponseEntity<Void> {
        val usuarioAtual:Usuario = usuarioRepository.save(cadastroRequest.usuario)
        cadastroRequest.areaUsuario.usuario.id = usuarioAtual.id
        cadastroRequest.dadosBancarios.usuario.id = usuarioAtual.id
        cadastroRequest.dadosEndereco.usuario.id = usuarioAtual.id
        cadastroRequest.descServicos.usuario.id = usuarioAtual.id
        areaUsuarioRepository.save(cadastroRequest.areaUsuario)
        dadosBancariosRepository.save(cadastroRequest.dadosBancarios)
        dadosEnderecoRepository.save(cadastroRequest.dadosEndereco)
        descServicosRepository.save(cadastroRequest.descServicos)
        return ResponseEntity.status(200).body(null)
    }

    @PatchMapping("/alterar/senha")
    fun atualizarSenha(@RequestBody alterSenhaRequest: AlterSenhaRequest): ResponseEntity<Usuario?> {
        val usuario: Usuario =
            usuarioRepository.findById(alterSenhaRequest.id).orElseThrow()
        usuario.nome = alterSenhaRequest.senha
        return ResponseEntity.status(200).body(usuarioRepository.save(usuario))
    }
}