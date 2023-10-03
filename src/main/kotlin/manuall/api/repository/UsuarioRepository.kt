package manuall.api.repository

import manuall.api.domain.Usuario
import manuall.api.dto.usuario.AprovacaoSubDto
import manuall.api.dto.usuario.FilteredUsuario
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface UsuarioRepository: JpaRepository<Usuario, Int>, JpaSpecificationExecutor<Usuario> {

    fun findByEmail(
        email: String?
    ): List<Optional<Usuario>>

    @Query("select u from Usuario u where u.email = ?1 and TYPE(u) = ?2")
    fun findByEmailAndTipoUsuario(
        email: String?,
        @Param("tipoUsuario") tipoUsuario: Class<out Usuario>
    ): Optional<Usuario>

    @Query("select count(u) from Usuario u where TYPE(u) = ?1 group by u.canal")
    fun countByTipoUsuarioGroupByCanal(
        @Param("tipoUsuario") tipoUsuario: Class<out Usuario>
    ): List<Int>

    @Query("""
        select
        new manuall.api.dto.usuario.AprovacaoSubDto(
        u.id, u.nome, u.anexoPfp, u.email, u.telefone, u.cpf, de.cidade, de.estado, de.cep, de.bairro, de.rua, de.numero, de.complemento, u.area.nome, u.prestaAula, u.orcamentoMin, u.orcamentoMax
        )
        from Usuario u
        join DadosEndereco de
        on u.id = de.usuario.id
        where u.prestaAula IS NOT NULL
        AND u.status = 1
    """)
    fun aprovacoesPendentes(): List<AprovacaoSubDto>
}
