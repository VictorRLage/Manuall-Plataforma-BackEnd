package manuall.restApioficial.repositories

import manuall.restApioficial.models.ProspectArea
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ProspectAreaRepository : JpaRepository<ProspectArea, Int> {
}