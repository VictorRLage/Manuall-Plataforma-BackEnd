package manuall.newproject.repository

import manuall.newproject.domain.UsuarioServico
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface UsuarioServicoRepository: JpaRepository<UsuarioServico, Int> {

    @Query("select u.servico.id from UsuarioServico u where u.usuario.id = ?1")
    fun findServicosByUsuarioId(id: Int): List<Int>
}