package manuall.api.repository

import manuall.api.domain.Servico
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ServicoRepository: JpaRepository<Servico, Int> {

    fun findAllByAreaId(id:Int):List<Servico>
}