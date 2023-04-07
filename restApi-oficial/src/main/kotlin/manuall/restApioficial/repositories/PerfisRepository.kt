package manuall.restApioficial.repositories

import manuall.restApioficial.models.Perfis
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PerfisRepository: JpaRepository<Perfis, Int> {
}