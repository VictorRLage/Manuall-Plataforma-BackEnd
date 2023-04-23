package manuall.restApioficial.repositories

import manuall.restApioficial.models.Solicitacao
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface SolicitacaoRepository:JpaRepository<Solicitacao, Int> {
}