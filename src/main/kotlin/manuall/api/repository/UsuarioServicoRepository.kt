package manuall.api.repository

import manuall.api.domain.UsuarioServico
import manuall.api.dto.perfil.PerfilServicoServiceDto
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface UsuarioServicoRepository: JpaRepository<UsuarioServico, Int> {

    @Query("select u.servico.id from UsuarioServico u where u.prestador.id = ?1")
    fun findServicosIdByUsuarioId(id: Int): List<Int>

    @Query("select u.servico.nome from UsuarioServico u where u.prestador.id = ?1")
    fun findServicosNomeByUsuarioId(id: Int): List<String>

    @Query("""
        select
        new manuall.api.dto.perfil.PerfilServicoServiceDto(
        u.servico.id, u.servico.nome
        ) from UsuarioServico u where u.prestador.id = ?1
    """)
    fun findServicosByUsuarioId(id: Int): List<PerfilServicoServiceDto>
}