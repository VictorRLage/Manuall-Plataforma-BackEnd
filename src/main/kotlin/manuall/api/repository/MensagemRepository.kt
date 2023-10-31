package manuall.api.repository

import manuall.api.domain.Mensagem
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.util.*


@Repository
interface MensagemRepository: JpaRepository<Mensagem, Int> {

    @Query(
        """
        SELECT c FROM Mensagem c
        WHERE c.solicitacao.prestador.id = :prestadorId
        AND c.idRemetente = :prestadorId
        AND c.horario BETWEEN :startDate AND :endDate
        ORDER BY c.horario ASC
    """
    )
    fun findByPrestadorIdAndInterval(
        @Param("prestadorId") prestadorId: Int,
        @Param("startDate") startDate: Date,
        @Param("endDate") endDate: Date
    ): List<Mensagem>

    fun deleteBySolicitacaoId(id: Int)
}