package manuall.newproject.service

import manuall.newproject.domain.Usuario
import manuall.newproject.repository.UsuarioRepository
import org.springframework.stereotype.Service
import java.text.DecimalFormat

@Service
class DashboardService(
    val usuarioRepository: UsuarioRepository
) {

    fun usuariosCanal(tipoUsuario:Int): List<String> {
        var usuario = usuarioRepository.countByTipoUsuarioGroupByCanal(tipoUsuario)

        val totalUsuarios = usuario.sum().toDouble()
        val porcentagem = mutableListOf<String>()
        for (i in usuario) {
            val df = DecimalFormat("#.##")
            porcentagem.add(df.format((i.toDouble() * 100) / totalUsuarios))

        }
        return porcentagem
    }


    fun pegarRegiao() {

    }

    fun taxaComplitudeCadastro() {

    }
}