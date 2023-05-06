package manuall.newproject.repository

import manuall.newproject.domain.AreaUsuario
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface AreaUsuarioRepository: JpaRepository<AreaUsuario, Int> {
    fun deleteByUsuarioId(id: Int)
}