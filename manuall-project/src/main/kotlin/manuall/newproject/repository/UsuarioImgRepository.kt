package manuall.newproject.repository

import manuall.newproject.domain.UsuarioImg
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface UsuarioImgRepository: JpaRepository<UsuarioImg, Int> {

    @Query("SELECT ui.anexo FROM UsuarioImg ui WHERE ui.usuario.id = :usuarioId")
    fun findUrlsByUsuarioId(usuarioId: Int): List<String>
}