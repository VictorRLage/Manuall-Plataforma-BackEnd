package manuall.newproject.repository

import manuall.newproject.domain.DescServicos
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface DescServicosRepository: JpaRepository<DescServicos, Int> {

    fun deleteByUsuarioId(id: Int)
}