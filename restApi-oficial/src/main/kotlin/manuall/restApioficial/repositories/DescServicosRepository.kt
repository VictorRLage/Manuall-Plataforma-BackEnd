package manuall.restApioficial.repositories

import manuall.restApioficial.models.DescServicos
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface DescServicosRepository:JpaRepository<DescServicos, Int> {
}