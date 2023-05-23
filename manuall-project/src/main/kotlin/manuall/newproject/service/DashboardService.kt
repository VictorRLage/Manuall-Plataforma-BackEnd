package manuall.newproject.service

import manuall.newproject.domain.Usuario
import manuall.newproject.dto.*
import manuall.newproject.repository.SolicitacaoRepository
import manuall.newproject.repository.UsuarioRepository
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import java.text.DecimalFormat

@Service
class DashboardService(
    val usuarioRepository: UsuarioRepository,
    val solicitacaoRepository: SolicitacaoRepository
) {

    fun usuariosCanal(tipoUsuario:Int): List<String> {
        val usuario = usuarioRepository.countByTipoUsuarioGroupByCanal(tipoUsuario)

        val totalUsuarios = usuario.sum().toDouble()
        val porcentagem = mutableListOf<String>()
        for (i in usuario) {
            val df = DecimalFormat("#.##")
            porcentagem.add(df.format((i.toDouble() * 100) / totalUsuarios))

        }
        return porcentagem
    }


    fun pegarRegiao():List<PegarRegiaoDTO> {
        val usuario = solicitacaoRepository.findByContratanteUsuarioId()
        return usuario

    }

    fun taxaComplitudeCadastro(): ResponseEntity<DashboardComplitudeCadastroDto> {

        usuarioRepository

        return ResponseEntity.status(200).body(DashboardComplitudeCadastroDto(
            DashboardComplitudeCadastroContratanteDto(
                2,
                3
            ),
            DashboardComplitudeCadastroPrestadorDto(
                5,
                4,
                3,
                1
            )
        ))
    }
}