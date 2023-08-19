package manuall.newproject.repository

import manuall.newproject.domain.CrmLog
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CrmLogRepository: JpaRepository<CrmLog, Int>