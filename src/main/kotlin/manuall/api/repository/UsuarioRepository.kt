package manuall.api.repository

import manuall.api.domain.Usuario
import manuall.api.dto.routine.crm.PrestadoresTimeResult
import manuall.api.dto.routine.crm.PrestadoresTimesResult
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

    @Query("""
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
    """)
    fun aprovacoesPendentes(): List<AprovacaoSubDto>

    @Query("""
        SELECT
        new manuall.api.dto.routine.crm.PrestadoresTimesResult(
        u.id, u.email, MAX(s.dataInicio), MAX(cl.inicioContato)
        )
        FROM Usuario u
        INNER JOIN Solicitacao s ON u.id = s.prestador.id
        LEFT JOIN CrmLog cl ON u.id = cl.usuario.id
        WHERE TYPE(u) = Prestador
        GROUP BY u.id, u.email
    """)
    fun findPrestadoresTimes(): List<PrestadoresTimesResult>

    @Query("""
        SELECT
        new manuall.api.dto.routine.crm.PrestadoresTimeResult(
        sub.id, sub.email, sub.lastCrmDate
        )
        FROM (
            SELECT
            s.prestador.id AS id,
            s.prestador.email AS email,
            COUNT(*) AS total,
            MAX(cl.inicioContato) AS lastCrmDate
            FROM Solicitacao s
            LEFT JOIN CrmLog cl ON s.prestador.id = cl.usuario.id
            WHERE TYPE(s.prestador) = Prestador
            AND s.dataInicio BETWEEN (current_timestamp - (1/24/60) * 30) AND current_timestamp
            GROUP BY s.prestador.id
        ) sub
        WHERE sub.total >= 18
    """)
    fun findHeavy(): List<PrestadoresTimeResult>

    @Query("""
        SELECT
        new manuall.api.dto.routine.crm.PrestadoresTimesResult(
        s.contratante.id,
        s.contratante.email,
        MAX(s.dataInicio) AS lastSolicitacaoDate,
        MAX(cl.inicioContato) AS lastCrmDate
        FROM Solicitacao s
        LEFT JOIN CrmLog cl ON s.contratante.id = cl.usuario.id
        WHERE TYPE(s.contratante) = Contratante
        AND s.dataInicio BETWEEN (current_timestamp - (1/24/60) * 1) AND current_timestamp
        GROUP BY s.contratante.id
    """)
    fun findContratantesRecentes(): List<PrestadoresTimesResult>
}
