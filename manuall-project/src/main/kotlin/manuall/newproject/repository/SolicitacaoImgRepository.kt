package manuall.newproject.repository

import manuall.newproject.domain.SolicitacaoImg
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface SolicitacaoImgRepository: JpaRepository<SolicitacaoImg, Int> {
}