package manuall.api.repository

import manuall.api.domain.Solicitacao
import manuall.api.dto.dashboard.HistoricoDTO
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface HistoricoRepository : JpaRepository<Solicitacao, Int> {

    @Query("""
        SELECT new manuall.api.dto.dashboard.HistoricoDTO(
            s.status,
            s.dataInicio,
            s.dataFim,
            s.prestador.nome,
            s.servico.nome,
            formOrcamento.orcamento,
            s.prestador.anexoPfp,
            s.descricao,
            avaliacao.nota,
            s.id
        )
        FROM
            Solicitacao s
        JOIN
            s.servico servico
        LEFT JOIN
            s.formOrcamento formOrcamento
        LEFT JOIN s.avaliacao avaliacao
        WHERE
            s.contratante.id = :usuarioId
        ORDER BY s.dataInicio DESC
    """)
    fun buscarHistorico(@Param("usuarioId") usuarioId: Int): List<HistoricoDTO>
}
