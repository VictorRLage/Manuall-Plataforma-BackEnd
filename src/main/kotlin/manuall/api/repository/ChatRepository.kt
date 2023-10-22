package manuall.api.repository

import manuall.api.domain.Chat
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.util.*


@Repository
interface ChatRepository: JpaRepository<Chat, Int> {

    @Query("""
        SELECT c FROM Chat c
        WHERE c.solicitacao.prestador.id = :prestadorId
        AND c.idRemetente = :prestadorId
        AND c.horario BETWEEN :startDate AND :endDate
        ORDER BY c.horario ASC
    """)
    fun findByPrestadorIdAndInterval(
        @Param("prestadorId") prestadorId: Int,
        @Param("startDate") startDate: Date,
        @Param("endDate") endDate: Date
    ): List<Chat>

    fun deleteBySolicitacaoId(id: Int)
}