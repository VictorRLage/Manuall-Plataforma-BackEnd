package manuall.newproject.repository

import manuall.newproject.domain.FormOrcamento
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface FormOrcamentoRepository: JpaRepository<FormOrcamento, Int>