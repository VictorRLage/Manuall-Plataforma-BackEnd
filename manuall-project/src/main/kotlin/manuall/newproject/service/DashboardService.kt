package manuall.newproject.service

import manuall.newproject.dto.DashboardComplitudeCadastroContratanteDto
import manuall.newproject.dto.DashboardComplitudeCadastroDto
import manuall.newproject.dto.DashboardComplitudeCadastroPrestadorDto
import manuall.newproject.dto.LoginResponse
import manuall.newproject.repository.UsuarioRepository
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import java.text.DecimalFormat

@Service
class DashboardService(
    val usuarioRepository: UsuarioRepository
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


    fun pegarRegiao() {
        TODO()
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