package manuall.restApioficial.repositories

import manuall.restApioficial.models.Avaliacao
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface AvaliacaoRepository : JpaRepository<Avaliacao, Int> {
}