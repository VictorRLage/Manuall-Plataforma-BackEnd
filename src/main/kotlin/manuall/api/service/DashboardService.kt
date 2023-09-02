package manuall.api.service

import manuall.api.dto.dashboard.DashboardComplitudeCadastroContratanteDto
import manuall.api.dto.dashboard.DashboardComplitudeCadastroDto
import manuall.api.dto.dashboard.DashboardComplitudeCadastroPrestadorDto
import manuall.api.dto.dashboard.PegarRegiaoDto
import manuall.api.enums.TipoUsuario
import manuall.api.repository.SolicitacaoRepository
import manuall.api.repository.UsuarioRepository
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import java.text.DecimalFormat

@Service
class DashboardService(
    val usuarioRepository: UsuarioRepository,
    val solicitacaoRepository: SolicitacaoRepository
) {

    fun usuariosCanal(tipoUsuario: Int): List<String> {
        val usuario = usuarioRepository.countByTipoUsuarioGroupByCanal(
            TipoUsuario.fromIntToClass(tipoUsuario)
        )

        val totalUsuarios = usuario.sum().toDouble()
        val porcentagem = mutableListOf<String>()
        for (i in usuario) {
            val df = DecimalFormat("#.##")
            porcentagem.add(df.format((i.toDouble() * 100) / totalUsuarios))
        }
        return porcentagem
    }

    fun pegarRegiao():List<PegarRegiaoDto> {
        val usuario = solicitacaoRepository.findByContratanteUsuario()
        return usuario
        // TODO: count da quantidade de cont em cada região 
    }

    fun taxaComplitudeCadastro(): ResponseEntity<DashboardComplitudeCadastroDto> {

        usuarioRepository

        return ResponseEntity.status(200).body(
            DashboardComplitudeCadastroDto(
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
        )
        )
    }
}