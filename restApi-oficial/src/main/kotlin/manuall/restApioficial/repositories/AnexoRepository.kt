package manuall.restApioficial.repositories

import manuall.restApioficial.models.Anexo
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface AnexoRepository : JpaRepository<Anexo, Int> {
}