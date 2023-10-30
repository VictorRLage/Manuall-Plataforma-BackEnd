package manuall.api.repository

import manuall.api.domain.UsuarioImg
import manuall.api.dto.perfil.PerfilImagemDto
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface UsuarioImgRepository: JpaRepository<UsuarioImg, Int> {

    @Query("""
        SELECT
        new manuall.api.dto.perfil.PerfilImagemDto(ui.id, ui.anexo)
        FROM UsuarioImg ui WHERE ui.prestador.id = :usuarioId
    """)
    fun findUrlsByUsuarioId(usuarioId: Int): List<PerfilImagemDto>

    fun findByAnexoAndPrestadorId(anexo: String, usuarioId: Int): Optional<UsuarioImg>
}