package manuall.restApioficial.repositories

import manuall.restApioficial.models.Prospect
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ProspectRepository : JpaRepository<Prospect, Int> {
}