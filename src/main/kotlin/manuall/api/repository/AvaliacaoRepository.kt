package manuall.api.repository

import manuall.api.domain.Avaliacao
import manuall.api.dto.solicitacao.AvaliacaoDto
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface AvaliacaoRepository: JpaRepository<Avaliacao, Int> {

    @Query("""
        SELECT
        new manuall.api.dto.solicitacao.AvaliacaoDto(
        s.contratante.nome,
        a.nota,
        a.descricao
        )
        FROM Avaliacao a
        JOIN Solicitacao s ON s.avaliacao.id = a.id
        WHERE s.prestador.id = ?1
    """)
    fun findByPrestadorId(prestadorId: Int): List<AvaliacaoDto>

    @Query("""
        SELECT a.descricao FROM Avaliacao a
        WHERE a.solicitacao.prestador.id = :prestadorId
        AND a.solicitacao.dataFim BETWEEN :startDate AND :endDate
    """)
    fun findDescricaoByPrestadorIdAndInterval(
        @Param("prestadorId") prestadorId: Int,
        @Param("startDate") startDate: Date,
        @Param("endDate") endDate: Date
    ): List<String>
}