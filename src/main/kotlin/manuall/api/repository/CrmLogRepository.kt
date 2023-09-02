package manuall.api.repository

import manuall.api.domain.CrmLog
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface CrmLogRepository: JpaRepository<CrmLog, Int> {

    @Query("select cl.histMsgs from CrmLog cl where cl.usuario.id = ?1 order by cl.inicioContato limit 1")
    fun findMsgsByUsuarioId(id: Int): String

    fun findByUsuarioId(id: Int): CrmLog
}