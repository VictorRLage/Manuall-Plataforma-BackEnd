package manuall.api.repository

import manuall.api.domain.SolicitacaoImg
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface SolicitacaoImgRepository: JpaRepository<SolicitacaoImg, Int> {

    fun deleteBySolicitacaoId(id: Int)
}