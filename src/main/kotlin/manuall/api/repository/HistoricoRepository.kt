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
            s.prestador.id,
            s.servico.id,   
            s.formOrcamento.id,
            s.status,
            s.dataInicio,
            s.dataFim,
            s.descricao,
            servico.area.id,
            servico.nome,
            formOrcamento.orcamento
        )
        FROM
            Solicitacao s
        JOIN
            s.servico servico
        LEFT JOIN
            s.formOrcamento formOrcamento 
        WHERE
            s.contratante.id = :usuarioId
    """)
    fun buscarHistorico(@Param("usuarioId") usuarioId: Int): List<HistoricoDTO>
}
