package manuall.api.repository

import manuall.api.domain.CrmLog
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface CrmLogRepository: JpaRepository<CrmLog, Int> {

    @Query("SELECT cl FROM CrmLog cl WHERE cl.usuario.id = ?1 ORDER BY cl.inicioContato DESC LIMIT 1")
    fun findByUsuarioIdOrderByInicioContatoDesc(id: Int): CrmLog?
}