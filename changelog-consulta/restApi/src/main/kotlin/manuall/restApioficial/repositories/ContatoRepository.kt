package manuall.restApioficial.repositories

import manuall.restApioficial.models.Contato
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ContatoRepository:JpaRepository<Contato, Int> {
}