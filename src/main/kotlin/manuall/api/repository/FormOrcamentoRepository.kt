package manuall.api.repository

import manuall.api.domain.FormOrcamento
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface FormOrcamentoRepository: JpaRepository<FormOrcamento, Int>