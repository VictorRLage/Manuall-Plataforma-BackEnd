package manuall.restApioficial.repositories

import manuall.restApioficial.models.DadosEndereco
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface DadosEnderecoRepository: JpaRepository<DadosEndereco, Int> {
    fun deleteByUsuarioId(id: Int)
}