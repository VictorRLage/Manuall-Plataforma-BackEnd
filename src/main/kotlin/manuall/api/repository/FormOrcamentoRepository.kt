package manuall.api.repository

import manuall.api.domain.FormOrcamento
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface FormOrcamentoRepository: JpaRepository<FormOrcamento, Int> {

    @Query("""
        SELECT COALESCE(SUM(f.orcamento), 0) FROM FormOrcamento f
        WHERE f.solicitacao.prestador.id = :prestadorId
        AND f.solicitacao.dataFim BETWEEN :startDate AND :endDate
    """)
    fun findByPrestadorIdAndInterval(
        @Param("prestadorId") prestadorId: Int,
        @Param("startDate") startDate: Date,
        @Param("endDate") endDate: Date
    ): Double
}