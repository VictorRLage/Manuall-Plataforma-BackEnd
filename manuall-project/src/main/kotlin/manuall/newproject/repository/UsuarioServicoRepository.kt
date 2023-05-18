package manuall.newproject.repository

import manuall.newproject.domain.UsuarioServico
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UsuarioServicoRepository: JpaRepository<UsuarioServico, Int> {
}