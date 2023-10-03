package manuall.api.repository

import manuall.api.domain.Solicitacao
import manuall.api.dto.dashboard.HistoricoDTO
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface HistoricoRepository : JpaRepository<Solicitacao, Long> {

    @Query("""
        SELECT new manuall.api.dto.dashboard.HistoricoDTO(
            s.prestadorUsuario.id,
            s.servico.id,
            s.formOrcamento.id,
            s.status,
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
            s.contratanteUsuario.id = :usuario_id
    """)
    fun buscarHistorico(@Param("usuario_id") usuario_id: Long): List<HistoricoDTO>
}
