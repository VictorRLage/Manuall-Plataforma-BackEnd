package manuall.newproject.repository

import manuall.newproject.domain.Area
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface AreaRepository: JpaRepository<Area, Int> {

    @Query("SELECT a.nome FROM Usuario u JOIN u.area a WHERE u.id = :usuarioId")
    fun findAreaNomeByUsuarioId(usuarioId: Int): String?

}