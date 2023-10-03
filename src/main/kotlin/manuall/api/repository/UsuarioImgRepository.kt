package manuall.api.repository

import manuall.api.domain.UsuarioImg
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface UsuarioImgRepository: JpaRepository<UsuarioImg, Int> {

    @Query("SELECT ui.anexo FROM UsuarioImg ui WHERE ui.prestador.id = :usuarioId")
    fun findUrlsByUsuarioId(usuarioId: Int): List<String>

    fun findByAnexoAndPrestadorId(anexo: String, usuarioId: Int): Optional<UsuarioImg>
}