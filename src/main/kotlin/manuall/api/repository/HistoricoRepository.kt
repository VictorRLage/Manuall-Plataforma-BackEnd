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
        SELECT new manuall.api.dto.HistoricoDTO(
            s.data_inicio,
            s.data_fim,
            s.prestador_usuarioId,
            s.servico_id,
            s.formOrcamento_id,
            s.status,
            s.descricao,
            serv.area_id,
            serv.nome
        )
        FROM Solicitacao s
        JOIN s.servico serv
        WHERE s.contratante_usuario_id = :usuario_id
    """)
    fun buscarHistorico(@Param("usuario_id") usuario_id: Long): List<HistoricoDTO>
}
