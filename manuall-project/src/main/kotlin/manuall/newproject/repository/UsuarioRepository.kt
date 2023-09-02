package manuall.newproject.repository

import manuall.newproject.domain.Usuario
import manuall.newproject.dto.usuario.AprovacaoSubDto
import manuall.newproject.dto.usuario.FilteredUsuario
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface UsuarioRepository: JpaRepository<Usuario, Int> {

    fun findByEmail(
        email: String?
    ): List<Optional<Usuario>>

    @Query("""
        select u from from Usuario u where u.email = ?1 and TYPE(u) = ?2
    """)
    fun findByEmailAndTipoUsuario(
        email: String?,
        tipoUsuario: Class<out Usuario>
    ): Optional<Usuario>

    @Query("""
        select count(u) from Usuario u where TYPE(u) =?1
        group by u.canal
    """)
    fun countByTipoUsuarioGroupByCanal(
        tipoUsuario: Class<out Usuario>
    ): List<Int>

    @Query("""
        select
        new manuall.newproject.dto.usuario.AprovacaoSubDto(
        u.id, u.nome, u.anexoPfp, u.email, u.telefone, u.cpf, de.cidade, de.estado, de.cep, de.bairro, de.rua, de.numero, de.complemento, u.area.nome, u.prestaAula, u.orcamentoMin, u.orcamentoMax
        )
        from Usuario u
        join DadosEndereco de
        on u.id = de.usuario.id
        where u.prestaAula IS NOT NULL
        AND u.status = 1
    """)
    fun aprovacoesPendentes(): List<AprovacaoSubDto>

    // QUERIES DA LISTAGEM DE PRESTADORES

    companion object {
        const val STARTING_QUERY = """
            select
            new manuall.newproject.dto.usuario.FilteredUsuario(u.id, u.nome, u.anexoPfp, u.area.id, u.orcamentoMin, u.orcamentoMax, d.cidade, u.prestaAula, COALESCE(avg(a.nota)), count(a.id))
            from Usuario u
            LEFT JOIN Solicitacao s ON u.id = s.prestadorUsuario.id
            JOIN Avaliacao a ON a.id = s.avaliacao.id
            JOIN DadosEndereco d ON u.id = d.usuario.id
            WHERE TYPE(u) = Prestador
        """
        const val GROUP_BY = """
            GROUP BY u.id, u.nome, u.anexoPfp, u.area.id, u.orcamentoMin, u.orcamentoMax, d.cidade, u.prestaAula
        """
     }

    @Query("$STARTING_QUERY $GROUP_BY ORDER BY u.plano")
    fun findAllPrestadores(): List<FilteredUsuario>

    @Query("$STARTING_QUERY AND u.area.id = ?1 $GROUP_BY ORDER BY u.plano")
    fun findAllPrestadoresByArea(idArea: Int): List<FilteredUsuario>

    // Listar por nota
    @Query("$STARTING_QUERY $GROUP_BY ORDER BY a.nota asc")
    fun findAllOrderByNotaAsc(): List<FilteredUsuario>
    @Query("$STARTING_QUERY $GROUP_BY ORDER BY a.nota desc")
    fun findAllOrderByNotaDesc(): List<FilteredUsuario>
    @Query("$STARTING_QUERY AND u.area.id = ?1 $GROUP_BY ORDER BY a.nota asc")
    fun findByAreaIdOrderByNotaAsc(areaId: Int): List<FilteredUsuario>
    @Query("$STARTING_QUERY AND u.area.id = ?1 $GROUP_BY ORDER BY a.nota desc")
    fun findByAreaIdOrderByNotaDesc(areaId: Int): List<FilteredUsuario>

    // Listar por preço máximo
    @Query("$STARTING_QUERY $GROUP_BY ORDER BY u.orcamentoMax asc")
    fun findAllOrderByPrecoMaxAsc(): List<FilteredUsuario>
    @Query("$STARTING_QUERY $GROUP_BY ORDER BY u.orcamentoMax desc")
    fun findAllOrderByPrecoMaxDesc(): List<FilteredUsuario>
    @Query("$STARTING_QUERY AND u.area.id = ?1 $GROUP_BY ORDER BY u.orcamentoMax asc")
    fun findByAreaIdOrderByPrecoMaxAsc(areaId: Int): List<FilteredUsuario>
    @Query("$STARTING_QUERY AND u.area.id = ?1 $GROUP_BY ORDER BY u.orcamentoMax desc")
    fun findByAreaIdOrderByPrecoMaxDesc(areaId: Int): List<FilteredUsuario>

    // Listar por preço mínimo
    @Query("$STARTING_QUERY $GROUP_BY ORDER BY u.orcamentoMin asc")
    fun findAllOrderByPrecoMinAsc(): List<FilteredUsuario>
    @Query("$STARTING_QUERY $GROUP_BY ORDER BY u.orcamentoMin desc")
    fun findAllOrderByPrecoMinDesc(): List<FilteredUsuario>
    @Query("$STARTING_QUERY AND u.area.id = ?1 $GROUP_BY ORDER BY u.orcamentoMin asc")
    fun findByAreaIdOrderByPrecoMinAsc(areaId: Int): List<FilteredUsuario>
    @Query("$STARTING_QUERY AND u.area.id = ?1 $GROUP_BY ORDER BY u.orcamentoMin desc")
    fun findByAreaIdOrderByPrecoMinDesc(areaId: Int): List<FilteredUsuario>

    // Listar por Alfabetica
    @Query("$STARTING_QUERY $GROUP_BY ORDER BY u.nome asc")
    fun findAllOrderByAlfabeticaAsc(): List<FilteredUsuario>
    @Query("$STARTING_QUERY $GROUP_BY ORDER BY u.nome desc")
    fun findAllOrderByAlfabeticaDesc(): List<FilteredUsuario>
    @Query("$STARTING_QUERY AND u.area.id = ?1 $GROUP_BY ORDER BY u.nome asc")
    fun findByAreaIdOrderByAlfabeticaAsc(areaId: Int): List<FilteredUsuario>
    @Query("$STARTING_QUERY AND u.area.id = ?1 $GROUP_BY ORDER BY u.nome desc")
    fun findByAreaIdOrderByAlfabeticaDesc(areaId: Int): List<FilteredUsuario>

    // Listar por Servico
    @Query("$STARTING_QUERY AND u.prestaAula = false $GROUP_BY ORDER BY u.nome asc")
    fun findAllOrderByServicoAsc(): List<FilteredUsuario>
    @Query("$STARTING_QUERY AND u.prestaAula = false $GROUP_BY ORDER BY u.nome desc")
    fun findAllOrderByServicoDesc(): List<FilteredUsuario>
    @Query("$STARTING_QUERY AND u.prestaAula = false AND u.area.id = ?1 $GROUP_BY ORDER BY u.nome asc")
    fun findByAreaIdOrderByServicoAsc(areaId: Int): List<FilteredUsuario>
    @Query("$STARTING_QUERY AND u.prestaAula = false AND u.area.id = ?1 $GROUP_BY ORDER BY u.nome desc")
    fun findByAreaIdOrderByServicoDesc(areaId: Int): List<FilteredUsuario>

    // Listar por ServicoAula
    @Query("$STARTING_QUERY AND u.prestaAula = true $GROUP_BY ORDER BY u.nome asc")
    fun findAllOrderByServicoAulaAsc(): List<FilteredUsuario>
    @Query("$STARTING_QUERY AND u.prestaAula = true $GROUP_BY ORDER BY u.nome desc")
    fun findAllOrderByServicoAulaDesc(): List<FilteredUsuario>
    @Query("$STARTING_QUERY AND u.prestaAula = true AND u.area.id = ?1 $GROUP_BY ORDER BY u.nome asc")
    fun findByAreaIdOrderByServicoAulaAsc(areaId: Int): List<FilteredUsuario>
    @Query("$STARTING_QUERY AND u.prestaAula = true AND u.area.id = ?1 $GROUP_BY ORDER BY u.nome desc")
    fun findByAreaIdOrderByServicoAulaDesc(areaId: Int): List<FilteredUsuario>

}
