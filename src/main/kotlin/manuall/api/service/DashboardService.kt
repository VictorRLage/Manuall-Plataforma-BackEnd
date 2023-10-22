package manuall.api.service

import manuall.api.dto.dashboard.DashboardDto
import manuall.api.repository.*
import manuall.api.security.JwtTokenManager
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.util.*
import java.util.concurrent.TimeUnit

@Service
class DashboardService(
    val solicitacaoRepository: SolicitacaoRepository,
    val jwtTokenManager: JwtTokenManager,
    val chatRepository: ChatRepository,
    val formOrcamentoRepository: FormOrcamentoRepository,
    val avaliacaoRepository: AvaliacaoRepository
) {

    fun getDashboard(token: String?, from: Date, to: Date): ResponseEntity<DashboardDto> {

        val usuario = jwtTokenManager.validateToken(token)
            ?: return ResponseEntity.status(480).build()

        val mensagens = chatRepository.findByPrestadorIdAndInterval(usuario.id, from, to)
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

        val now = LocalDate.now()

        val startDate = now.minusMonths(11).withDayOfMonth(1)
        val endDate = now.withDayOfMonth(now.lengthOfMonth())

        val startDateConverted: Date = Date.from(startDate.atStartOfDay().toInstant(java.time.ZoneOffset.UTC))
        val endDateConverted: Date = Date.from(endDate.atStartOfDay().toInstant(java.time.ZoneOffset.UTC))

        val solicitacoesMensais = solicitacaoRepository.countByPrestadorIdAndIntervalGroupByMonth(
            usuario.id,
            startDateConverted,
            endDateConverted,
        )

        return ResponseEntity.status(200).body(
            DashboardDto(
                solicitacaoRepository.countByPrestadorIdAndInterval(usuario.id, from, to),
                averageMinutes,
                formOrcamentoRepository.findByPrestadorIdAndInterval(usuario.id, from, to),
                solicitacaoRepository.findNotaAndCountServicosByPrestadorIdAndInterval(usuario.id, from, to),
                avaliacaoRepository.findDescricaoByPrestadorIdAndInterval(usuario.id, from, to),
                solicitacoesMensais,
            )
        )
    }
}