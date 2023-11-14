package manuall.api.repository

import manuall.api.domain.Usuario
import manuall.api.dto.usuario.AprovacaoDto
import manuall.api.dto.usuario.AprovacaoSubDto
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface UsuarioRepository: JpaRepository<Usuario, Int> {

    fun findByEmail(
        email: String?
    ): List<Usuario>

    @Query("select u from Usuario u where u.email = ?1 and TYPE(u) = ?2")
    fun findByEmailAndTipoUsuario(
        email: String?,
        @Param("tipoUsuario") tipoUsuario: Class<out Usuario>
    ): Optional<Usuario>

    @Query("select u from Usuario u where u.email = ?1 and TYPE(u) = ?2")
    fun findByEmailAndTipoUsuarioList(
        email: String?,
        @Param("tipoUsuario") tipoUsuario: Class<out Usuario>
    ): List<Usuario>

    @Query("select count(u) from Usuario u where TYPE(u) = ?1 group by u.canal")
    fun countByTipoUsuarioGroupByCanal(
        @Param("tipoUsuario") tipoUsuario: Class<out Usuario>
    ): List<Int>

    @Query(
        """
        select
        new manuall.api.dto.usuario.AprovacaoSubDto(
            u.id,
            u.nome,
            u.email,
            u.telefone,
            u.cpf,
            u.dadosEndereco.cidade,
            u.dadosEndereco.estado,
            u.area.nome,
            u.orcamentoMin,
            u.orcamentoMax,
            u.prestaAula,
            u.statusProcessoAprovacao,
            u.status
        )
        from Prestador u
        where TYPE(u) = Prestador
        AND u.prestaAula IS NOT NULL
        AND u.status = 1
    """
    )
    fun aprovacoesPendentes(): List<AprovacaoSubDto>
}
