package manuall.api.repository

import manuall.api.domain.Solicitacao
import manuall.api.dto.chat.ChatPegarDadosDestinatarioDto
import manuall.api.dto.chat.ChatResponse
import manuall.api.dto.dashboard.DashboardServicosDto
import manuall.api.dto.dashboard.SolicitacoesMensaisDto
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface SolicitacaoRepository: JpaRepository<Solicitacao, Int> {

    fun findByContratanteId(usuarioId: Int): List<Solicitacao>

    fun findByPrestadorId(usuarioId: Int): List<Solicitacao>

    @Query("SELECT COUNT(s) FROM Solicitacao s WHERE s.prestador.id = :prestadorId AND s.dataFim BETWEEN :startDate AND :endDate")
    fun countByPrestadorIdAndInterval(
        @Param("prestadorId") prestadorId: Int,
        @Param("startDate") startDate: Date,
        @Param("endDate") endDate: Date
    ): Long

    @Query("""
        SELECT
            new manuall.api.dto.dashboard.DashboardServicosDto(
                s.servico.nome, COUNT(s), COALESCE(AVG(s.avaliacao.nota), 0)
            )
        FROM Solicitacao s
        WHERE s.prestador.id = :prestadorId
        AND s.dataFim BETWEEN :startDate AND :endDate
        GROUP BY s.servico.id
    """)
    fun findNotaAndCountServicosByPrestadorIdAndInterval(
        @Param("prestadorId") prestadorId: Int,
        @Param("startDate") startDate: Date,
        @Param("endDate") endDate: Date
    ): List<DashboardServicosDto>

    @Query("""
        SELECT
            new manuall.api.dto.dashboard.SolicitacoesMensaisDto(
                YEAR(s.dataFim),
                MONTH(s.dataFim),
                COUNT(s.id),
                SUM(CASE WHEN s.status = 4 THEN 1 ELSE 0 END)
            )
        FROM Solicitacao s
        WHERE
            s.prestador.id = :prestadorId
            AND s.dataFim BETWEEN :startDate AND :endDate
        GROUP BY
            YEAR(s.dataFim),
            MONTH(s.dataFim)
        ORDER BY
            YEAR(s.dataFim) DESC,
            MONTH(s.dataFim) DESC
    """)
    fun countByPrestadorIdAndIntervalGroupByMonth(
        @Param("prestadorId") prestadorId: Int,
        @Param("startDate") startDate: Date,
        @Param("endDate") endDate: Date
    ): List<SolicitacoesMensaisDto>

    @Query("SELECT s from Solicitacao s where s.contratante.id = ?1 and s.status = 2 and s.dataFim is null")
    fun findByContratanteIdAndStatus2AndDataFimNull(usuarioId: Int): List<Solicitacao>

    @Query("SELECT s from Solicitacao s where s.prestador.id = ?1 and s.status = 2 and s.dataFim is null")
    fun findByPrestadorIdAndStatus2AndDataFimNull(usuarioId: Int): List<Solicitacao>
}
