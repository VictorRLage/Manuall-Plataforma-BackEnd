package manuall.newproject.repository

import manuall.newproject.domain.TipoServico
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import org.springframework.web.bind.annotation.PathVariable

@Repository
interface TipoServicoRepository: JpaRepository<TipoServico, Int> {

    fun findAllByAreaId(@PathVariable id:Int):List<TipoServico>
}