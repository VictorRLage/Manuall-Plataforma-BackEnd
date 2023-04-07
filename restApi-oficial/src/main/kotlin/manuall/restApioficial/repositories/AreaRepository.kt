package manuall.restApioficial.repositories

import manuall.restApioficial.models.Area
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface AreaRepository: JpaRepository<Area, Int> {
}