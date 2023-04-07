package manuall.restApioficial.repositories

import manuall.restApioficial.models.DadosBancarios
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface DadosBancariosRepository: JpaRepository<DadosBancarios, Int> {
}