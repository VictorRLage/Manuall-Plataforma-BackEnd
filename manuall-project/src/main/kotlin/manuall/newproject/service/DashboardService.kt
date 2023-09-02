package manuall.newproject.service

import manuall.newproject.domain.Administrador
import manuall.newproject.domain.Contratante
import manuall.newproject.domain.Prestador
import manuall.newproject.dto.dashboard.DashboardComplitudeCadastroContratanteDto
import manuall.newproject.dto.dashboard.DashboardComplitudeCadastroDto
import manuall.newproject.dto.dashboard.DashboardComplitudeCadastroPrestadorDto
import manuall.newproject.dto.dashboard.PegarRegiaoDto
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

    fun usuariosCanal(tipoUsuario: Int): List<String> {
        val usuario = usuarioRepository.countByTipoUsuarioGroupByCanal(
            when (tipoUsuario) {
                1 -> Contratante::class.java
                2 -> Prestador::class.java
                3 -> Administrador::class.java
                else -> Contratante::class.java
            }
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