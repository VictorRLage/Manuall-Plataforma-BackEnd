package manuall.newproject.repository

import manuall.newproject.domain.TipoServico
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface TipoServicoRepository: JpaRepository<TipoServico, Int> {
}