package manuall.api.service

import manuall.api.dto.dashboard.DashboardDto
import manuall.api.repository.ChatRepository
import manuall.api.repository.SolicitacaoRepository
import manuall.api.repository.UsuarioRepository
import manuall.api.security.JwtTokenManager
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import java.util.*
import java.util.concurrent.TimeUnit

@Service
class DashboardService(
    val usuarioRepository: UsuarioRepository,
    val solicitacaoRepository: SolicitacaoRepository,
    val jwtTokenManager: JwtTokenManager,
    val chatRepository: ChatRepository
) {

    fun getDashboard(token: String?, from: Date, to: Date): ResponseEntity<DashboardDto> {

        val usuario = jwtTokenManager.validateToken(token)
            ?: return ResponseEntity.status(480).build()

        val mensagens = chatRepository.findMessagesBySolicitacaoId(usuario.id, from, to)
        val averageMinutes = if (mensagens.size < 2) {
            null
        } else {
            var totalDifference: Long = 0
            for (i in 1 until mensagens.size)
                totalDifference += mensagens[i].horario!!.time - mensagens[i - 1].horario!!.time

            TimeUnit.MILLISECONDS.toMinutes(
                totalDifference / (mensagens.size - 1)
            )
        }

        return ResponseEntity.status(200).body(
            DashboardDto(
                solicitacaoRepository.findAllByPrestadorIdAndDataFimBetween(usuario.id, from, to),
                averageMinutes,
                0.0,
                listOf(),
                listOf(),
                listOf(),
                listOf(),
            )
        )
    }
}