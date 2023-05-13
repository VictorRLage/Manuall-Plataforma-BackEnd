package manuall.newproject.repository

import manuall.newproject.domain.DadosEndereco
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface DadosEnderecoRepository: JpaRepository<DadosEndereco, Int> {

    fun deleteByUsuarioId(id: Int)

    fun findByUsuarioId(id: Int): List<DadosEndereco>
}