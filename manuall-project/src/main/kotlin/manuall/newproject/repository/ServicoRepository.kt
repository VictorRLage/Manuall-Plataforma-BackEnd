package manuall.newproject.repository

import manuall.newproject.domain.Servico
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import org.springframework.web.bind.annotation.PathVariable

@Repository
interface ServicoRepository: JpaRepository<Servico, Int> {

    fun findAllByAreaId(id:Int):List<Servico>
}