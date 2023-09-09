package manuall.api.repository

import manuall.api.domain.CrmLogMensagem
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CrmLogMensagemRepository: JpaRepository<CrmLogMensagem, Int> {

    fun findByCrmLogId(id: Int): List<CrmLogMensagem>
}