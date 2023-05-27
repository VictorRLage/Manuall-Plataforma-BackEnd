package manuall.newproject.repository

import manuall.newproject.domain.Usuario
import manuall.newproject.dto.usuario.UsuariosFilteredList
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface UsuarioRepository: JpaRepository<Usuario, Int> {

    fun findByEmail(
        email: String?
    ): List<Optional<Usuario>>

    fun findByEmailAndTipoUsuario(
        email: String?,
        tipoUsuario: Int?
    ): Optional<Usuario>

    @Query("""
        select count(u) from Usuario u where u.tipoUsuario =?1
        group by u.canal
    """)
    fun countByTipoUsuarioGroupByCanal(
        tipoUsuario: Int?
    ): List<Int>

    // QUERIES DA LISTAGEM DE PRESTADORES

    @Query("""
        select
        new manuall.newproject.dto.usuario.UsuariosFilteredList(u.id, u.nome, u.anexoPfp, u.area.id, u.orcamentoMin, u.orcamentoMax, d.cidade, u.prestaAula, COALESCE(avg(a.nota)), count(a.id))
        from Usuario u
        LEFT JOIN Avaliacao a ON u.id = a.prestadorUsuario.id
        JOIN DadosEndereco d ON u.id = d.usuario.id
        WHERE u.tipoUsuario = 2
        GROUP BY u.id, u.nome, u.anexoPfp, u.area.id, u.orcamentoMin, u.orcamentoMax, d.cidade, u.prestaAula
        ORDER BY u.plano
    """)
    fun findAllPrestadores(): List<UsuariosFilteredList>

    @Query("""
        select
        new manuall.newproject.dto.usuario.UsuariosFilteredList(u.id, u.nome, u.anexoPfp, u.area.id, u.orcamentoMin, u.orcamentoMax, d.cidade, u.prestaAula, COALESCE(avg(a.nota)), count(a.id))
        from Usuario u
        LEFT JOIN Avaliacao a ON u.id = a.prestadorUsuario.id
        JOIN DadosEndereco d ON u.id = d.usuario.id
        WHERE u.tipoUsuario = 2
        and u.area.id = ?1
        GROUP BY u.id, u.nome, u.anexoPfp, u.area.id, u.orcamentoMin, u.orcamentoMax, d.cidade, u.prestaAula
        ORDER BY u.plano
    """)
    fun findAllPrestadoresByArea(idArea: Int): List<UsuariosFilteredList>

    // Listar por nota
    @Query("""
        select new manuall.newproject.dto.usuario.UsuariosFilteredList(u.id, u.nome, u.anexoPfp, u.area.id, u.orcamentoMin, u.orcamentoMax, d.cidade, u.prestaAula, COALESCE(avg(a.nota)), count(a.id))
        from Usuario u
        LEFT JOIN Avaliacao a ON u.id = a.prestadorUsuario.id
        JOIN DadosEndereco d ON u.id = d.usuario.id
        where u.tipoUsuario = 2
        GROUP BY u.id, u.nome, u.anexoPfp, u.area.id, u.orcamentoMin, u.orcamentoMax, d.cidade, u.prestaAula
        order by a.nota asc
    """)
    fun findAllOrderByNotaAsc(): List<UsuariosFilteredList>
    @Query("""
        select new manuall.newproject.dto.usuario.UsuariosFilteredList(u.id, u.nome, u.anexoPfp, u.area.id, u.orcamentoMin, u.orcamentoMax, d.cidade, u.prestaAula, COALESCE(avg(a.nota)), count(a.id))
        from Usuario u
        LEFT JOIN Avaliacao a ON u.id = a.prestadorUsuario.id
        JOIN DadosEndereco d ON u.id = d.usuario.id
        where u.tipoUsuario = 2
        GROUP BY u.id, u.nome, u.anexoPfp, u.area.id, u.orcamentoMin, u.orcamentoMax, d.cidade, u.prestaAula
        order by a.nota desc
    """)
    fun findAllOrderByNotaDesc(): List<UsuariosFilteredList>
    @Query("""
        select new manuall.newproject.dto.usuario.UsuariosFilteredList(u.id, u.nome, u.anexoPfp, u.area.id, u.orcamentoMin, u.orcamentoMax, d.cidade, u.prestaAula, COALESCE(avg(a.nota)), count(a.id))
        from Usuario u
        LEFT JOIN Avaliacao a ON u.id = a.prestadorUsuario.id
        JOIN DadosEndereco d ON u.id = d.usuario.id
        WHERE u.area.id = ?1
        and u.tipoUsuario = 2
        GROUP BY u.id, u.nome, u.anexoPfp, u.area.id, u.orcamentoMin, u.orcamentoMax, d.cidade, u.prestaAula
        order by a.nota asc
    """)
    fun findByAreaIdOrderByNotaAsc(areaId: Int): List<UsuariosFilteredList>
    @Query("""
        select new manuall.newproject.dto.usuario.UsuariosFilteredList(u.id, u.nome, u.anexoPfp, u.area.id, u.orcamentoMin, u.orcamentoMax, d.cidade, u.prestaAula, COALESCE(avg(a.nota)), count(a.id))
        from Usuario u
        LEFT JOIN Avaliacao a ON u.id = a.prestadorUsuario.id
        JOIN DadosEndereco d ON u.id = d.usuario.id
        WHERE u.area.id = ?1
        and u.tipoUsuario = 2
        GROUP BY u.id, u.nome, u.anexoPfp, u.area.id, u.orcamentoMin, u.orcamentoMax, d.cidade, u.prestaAula
        order by a.nota desc
    """)
    fun findByAreaIdOrderByNotaDesc(areaId: Int): List<UsuariosFilteredList>

    // Listar por preço máximo
    @Query("""
        select new manuall.newproject.dto.usuario.UsuariosFilteredList(u.id, u.nome, u.anexoPfp, u.area.id, u.orcamentoMin, u.orcamentoMax, d.cidade, u.prestaAula, COALESCE(avg(a.nota)), count(a.id))
        from Usuario u
        LEFT JOIN Avaliacao a ON u.id = a.prestadorUsuario.id
        JOIN DadosEndereco d ON u.id = d.usuario.id
        where u.tipoUsuario = 2
        GROUP BY u.id, u.nome, u.anexoPfp, u.area.id, u.orcamentoMin, u.orcamentoMax, d.cidade, u.prestaAula
        order by u.orcamentoMax asc
    """)
    fun findAllOrderByPrecoMaxAsc(): List<UsuariosFilteredList>
    @Query("""
        select new manuall.newproject.dto.usuario.UsuariosFilteredList(u.id, u.nome, u.anexoPfp, u.area.id, u.orcamentoMin, u.orcamentoMax, d.cidade, u.prestaAula, COALESCE(avg(a.nota)), count(a.id))
        from Usuario u
        LEFT JOIN Avaliacao a ON u.id = a.prestadorUsuario.id
        JOIN DadosEndereco d ON u.id = d.usuario.id
        where u.tipoUsuario = 2
        GROUP BY u.id, u.nome, u.anexoPfp, u.area.id, u.orcamentoMin, u.orcamentoMax, d.cidade, u.prestaAula
        order by u.orcamentoMax desc
    """)
    fun findAllOrderByPrecoMaxDesc(): List<UsuariosFilteredList>
    @Query("""
        select new manuall.newproject.dto.usuario.UsuariosFilteredList(u.id, u.nome, u.anexoPfp, u.area.id, u.orcamentoMin, u.orcamentoMax, d.cidade, u.prestaAula, COALESCE(avg(a.nota)), count(a.id))
        from Usuario u
        LEFT JOIN Avaliacao a ON u.id = a.prestadorUsuario.id
        JOIN DadosEndereco d ON u.id = d.usuario.id
        WHERE u.area.id = ?1
        and u.tipoUsuario = 2
        GROUP BY u.id, u.nome, u.anexoPfp, u.area.id, u.orcamentoMin, u.orcamentoMax, d.cidade, u.prestaAula
        order by u.orcamentoMax asc
    """)
    fun findByAreaIdOrderByPrecoMaxAsc(areaId: Int): List<UsuariosFilteredList>
    @Query("""
        select new manuall.newproject.dto.usuario.UsuariosFilteredList(u.id, u.nome, u.anexoPfp, u.area.id, u.orcamentoMin, u.orcamentoMax, d.cidade, u.prestaAula, COALESCE(avg(a.nota)), count(a.id))
        from Usuario u
        LEFT JOIN Avaliacao a ON u.id = a.prestadorUsuario.id
        JOIN DadosEndereco d ON u.id = d.usuario.id
        WHERE u.area.id = ?1
        and u.tipoUsuario = 2
        GROUP BY u.id, u.nome, u.anexoPfp, u.area.id, u.orcamentoMin, u.orcamentoMax, d.cidade, u.prestaAula
        order by u.orcamentoMax desc
    """)
    fun findByAreaIdOrderByPrecoMaxDesc(areaId: Int): List<UsuariosFilteredList>

    // Listar por preço mínimo
    @Query("""
        select new manuall.newproject.dto.usuario.UsuariosFilteredList(u.id, u.nome, u.anexoPfp, u.area.id, u.orcamentoMin, u.orcamentoMax, d.cidade, u.prestaAula, COALESCE(avg(a.nota)), count(a.id))
        from Usuario u
        LEFT JOIN Avaliacao a ON u.id = a.prestadorUsuario.id
        JOIN DadosEndereco d ON u.id = d.usuario.id
        where u.tipoUsuario = 2
        GROUP BY u.id, u.nome, u.anexoPfp, u.area.id, u.orcamentoMin, u.orcamentoMax, d.cidade, u.prestaAula
        order by u.orcamentoMin asc
    """)
    fun findAllOrderByPrecoMinAsc(): List<UsuariosFilteredList>
    @Query("""
        select new manuall.newproject.dto.usuario.UsuariosFilteredList(u.id, u.nome, u.anexoPfp, u.area.id, u.orcamentoMin, u.orcamentoMax, d.cidade, u.prestaAula, COALESCE(avg(a.nota)), count(a.id))
        from Usuario u
        LEFT JOIN Avaliacao a ON u.id = a.prestadorUsuario.id
        JOIN DadosEndereco d ON u.id = d.usuario.id
        where u.tipoUsuario = 2
        GROUP BY u.id, u.nome, u.anexoPfp, u.area.id, u.orcamentoMin, u.orcamentoMax, d.cidade, u.prestaAula
        order by u.orcamentoMin desc
    """)
    fun findAllOrderByPrecoMinDesc(): List<UsuariosFilteredList>
    @Query("""
        select new manuall.newproject.dto.usuario.UsuariosFilteredList(u.id, u.nome, u.anexoPfp, u.area.id, u.orcamentoMin, u.orcamentoMax, d.cidade, u.prestaAula, COALESCE(avg(a.nota)), count(a.id))
        from Usuario u
        LEFT JOIN Avaliacao a ON u.id = a.prestadorUsuario.id
        JOIN DadosEndereco d ON u.id = d.usuario.id
        WHERE u.area.id = ?1
        and u.tipoUsuario = 2
        GROUP BY u.id, u.nome, u.anexoPfp, u.area.id, u.orcamentoMin, u.orcamentoMax, d.cidade, u.prestaAula
        order by u.orcamentoMin asc
    """)
    fun findByAreaIdOrderByPrecoMinAsc(areaId: Int): List<UsuariosFilteredList>
    @Query("""
        select new manuall.newproject.dto.usuario.UsuariosFilteredList(u.id, u.nome, u.anexoPfp, u.area.id, u.orcamentoMin, u.orcamentoMax, d.cidade, u.prestaAula, COALESCE(avg(a.nota)), count(a.id))
        from Usuario u
        LEFT JOIN Avaliacao a ON u.id = a.prestadorUsuario.id
        JOIN DadosEndereco d ON u.id = d.usuario.id
        WHERE u.area.id = ?1
        and u.tipoUsuario = 2
        GROUP BY u.id, u.nome, u.anexoPfp, u.area.id, u.orcamentoMin, u.orcamentoMax, d.cidade, u.prestaAula
        order by u.orcamentoMin desc
    """)
    fun findByAreaIdOrderByPrecoMinDesc(areaId: Int): List<UsuariosFilteredList>

    // Listar por Alfabetica
    @Query("""
        select new manuall.newproject.dto.usuario.UsuariosFilteredList(u.id, u.nome, u.anexoPfp, u.area.id, u.orcamentoMin, u.orcamentoMax, d.cidade, u.prestaAula, COALESCE(avg(a.nota)), count(a.id))
        from Usuario u
        LEFT JOIN Avaliacao a ON u.id = a.prestadorUsuario.id
        JOIN DadosEndereco d ON u.id = d.usuario.id
        where u.tipoUsuario = 2
        GROUP BY u.id, u.nome, u.anexoPfp, u.area.id, u.orcamentoMin, u.orcamentoMax, d.cidade, u.prestaAula
        order by u.nome asc
    """)
    fun findAllOrderByAlfabeticaAsc(): List<UsuariosFilteredList>
    @Query("""
        select new manuall.newproject.dto.usuario.UsuariosFilteredList(u.id, u.nome, u.anexoPfp, u.area.id, u.orcamentoMin, u.orcamentoMax, d.cidade, u.prestaAula, COALESCE(avg(a.nota)), count(a.id))
        from Usuario u
        LEFT JOIN Avaliacao a ON u.id = a.prestadorUsuario.id
        JOIN DadosEndereco d ON u.id = d.usuario.id
        where u.tipoUsuario = 2
        GROUP BY u.id, u.nome, u.anexoPfp, u.area.id, u.orcamentoMin, u.orcamentoMax, d.cidade, u.prestaAula
        order by u.nome desc
    """)
    fun findAllOrderByAlfabeticaDesc(): List<UsuariosFilteredList>
    @Query("""
        select new manuall.newproject.dto.usuario.UsuariosFilteredList(u.id, u.nome, u.anexoPfp, u.area.id, u.orcamentoMin, u.orcamentoMax, d.cidade, u.prestaAula, COALESCE(avg(a.nota)), count(a.id))
        from Usuario u
        LEFT JOIN Avaliacao a ON u.id = a.prestadorUsuario.id
        JOIN DadosEndereco d ON u.id = d.usuario.id
        WHERE u.area.id = ?1
        and u.tipoUsuario = 2
        GROUP BY u.id, u.nome, u.anexoPfp, u.area.id, u.orcamentoMin, u.orcamentoMax, d.cidade, u.prestaAula
        order by u.nome asc
    """)
    fun findByAreaIdOrderByAlfabeticaAsc(areaId: Int): List<UsuariosFilteredList>
    @Query("""
        select new manuall.newproject.dto.usuario.UsuariosFilteredList(u.id, u.nome, u.anexoPfp, u.area.id, u.orcamentoMin, u.orcamentoMax, d.cidade, u.prestaAula, COALESCE(avg(a.nota)), count(a.id))
        from Usuario u
        LEFT JOIN Avaliacao a ON u.id = a.prestadorUsuario.id
        JOIN DadosEndereco d ON u.id = d.usuario.id
        WHERE u.area.id = ?1
        and u.tipoUsuario = 2
        GROUP BY u.id, u.nome, u.anexoPfp, u.area.id, u.orcamentoMin, u.orcamentoMax, d.cidade, u.prestaAula
        order by u.nome desc
    """)
    fun findByAreaIdOrderByAlfabeticaDesc(areaId: Int): List<UsuariosFilteredList>

    // Listar por Servico
    @Query("""
        select new manuall.newproject.dto.usuario.UsuariosFilteredList(u.id, u.nome, u.anexoPfp, u.area.id, u.orcamentoMin, u.orcamentoMax, d.cidade, u.prestaAula, COALESCE(avg(a.nota)), count(a.id))
        from Usuario u
        LEFT JOIN Avaliacao a ON u.id = a.prestadorUsuario.id
        JOIN DadosEndereco d ON u.id = d.usuario.id
        WHERE u.prestaAula = false
        and u.tipoUsuario = 2
        GROUP BY u.id, u.nome, u.anexoPfp, u.area.id, u.orcamentoMin, u.orcamentoMax, d.cidade, u.prestaAula
        order by u.nome asc
    """)
    fun findAllOrderByServicoAsc(): List<UsuariosFilteredList>
    @Query("""
        select new manuall.newproject.dto.usuario.UsuariosFilteredList(u.id, u.nome, u.anexoPfp, u.area.id, u.orcamentoMin, u.orcamentoMax, d.cidade, u.prestaAula, COALESCE(avg(a.nota)), count(a.id))
        from Usuario u
        LEFT JOIN Avaliacao a ON u.id = a.prestadorUsuario.id
        JOIN DadosEndereco d ON u.id = d.usuario.id
        WHERE u.prestaAula = false
        and u.tipoUsuario = 2
        GROUP BY u.id, u.nome, u.anexoPfp, u.area.id, u.orcamentoMin, u.orcamentoMax, d.cidade, u.prestaAula
        order by u.nome desc
    """)
    fun findAllOrderByServicoDesc(): List<UsuariosFilteredList>
    @Query("""
        select new manuall.newproject.dto.usuario.UsuariosFilteredList(u.id, u.nome, u.anexoPfp, u.area.id, u.orcamentoMin, u.orcamentoMax, d.cidade, u.prestaAula, COALESCE(avg(a.nota)), count(a.id))
        from Usuario u
        LEFT JOIN Avaliacao a ON u.id = a.prestadorUsuario.id
        JOIN DadosEndereco d ON u.id = d.usuario.id
        WHERE u.prestaAula = false
        and u.area.id = ?1
        and u.tipoUsuario = 2
        GROUP BY u.id, u.nome, u.anexoPfp, u.area.id, u.orcamentoMin, u.orcamentoMax, d.cidade, u.prestaAula
        order by u.nome asc
    """)
    fun findByAreaIdOrderByServicoAsc(areaId: Int): List<UsuariosFilteredList>
    @Query("""
        select new manuall.newproject.dto.usuario.UsuariosFilteredList(u.id, u.nome, u.anexoPfp, u.area.id, u.orcamentoMin, u.orcamentoMax, d.cidade, u.prestaAula, COALESCE(avg(a.nota)), count(a.id))
        from Usuario u
        LEFT JOIN Avaliacao a ON u.id = a.prestadorUsuario.id
        JOIN DadosEndereco d ON u.id = d.usuario.id
        WHERE u.prestaAula = false
        and u.area.id = ?1
        and u.tipoUsuario = 2
        GROUP BY u.id, u.nome, u.anexoPfp, u.area.id, u.orcamentoMin, u.orcamentoMax, d.cidade, u.prestaAula
        order by u.nome desc
    """)
    fun findByAreaIdOrderByServicoDesc(areaId: Int): List<UsuariosFilteredList>

    // Listar por ServicoAula
    @Query("""
        select new manuall.newproject.dto.usuario.UsuariosFilteredList(u.id, u.nome, u.anexoPfp, u.area.id, u.orcamentoMin, u.orcamentoMax, d.cidade, u.prestaAula, COALESCE(avg(a.nota)), count(a.id))
        from Usuario u
        LEFT JOIN Avaliacao a ON u.id = a.prestadorUsuario.id
        JOIN DadosEndereco d ON u.id = d.usuario.id
        WHERE u.prestaAula = true
        and u.tipoUsuario = 2
        GROUP BY u.id, u.nome, u.anexoPfp, u.area.id, u.orcamentoMin, u.orcamentoMax, d.cidade, u.prestaAula
        order by u.nome asc
    """)
    fun findAllOrderByServicoAulaAsc(): List<UsuariosFilteredList>
    @Query("""
        select new manuall.newproject.dto.usuario.UsuariosFilteredList(u.id, u.nome, u.anexoPfp, u.area.id, u.orcamentoMin, u.orcamentoMax, d.cidade, u.prestaAula, COALESCE(avg(a.nota)), count(a.id))
        from Usuario u
        LEFT JOIN Avaliacao a ON u.id = a.prestadorUsuario.id
        JOIN DadosEndereco d ON u.id = d.usuario.id
        WHERE u.prestaAula = true
        and u.tipoUsuario = 2
        GROUP BY u.id, u.nome, u.anexoPfp, u.area.id, u.orcamentoMin, u.orcamentoMax, d.cidade, u.prestaAula
        order by u.nome desc
    """)
    fun findAllOrderByServicoAulaDesc(): List<UsuariosFilteredList>
    @Query("""
        select new manuall.newproject.dto.usuario.UsuariosFilteredList(u.id, u.nome, u.anexoPfp, u.area.id, u.orcamentoMin, u.orcamentoMax, d.cidade, u.prestaAula, COALESCE(avg(a.nota)), count(a.id))
        from Usuario u
        LEFT JOIN Avaliacao a ON u.id = a.prestadorUsuario.id
        JOIN DadosEndereco d ON u.id = d.usuario.id
        WHERE u.prestaAula = true
        and u.area.id = ?1
        and u.tipoUsuario = 2
        GROUP BY u.id, u.nome, u.anexoPfp, u.area.id, u.orcamentoMin, u.orcamentoMax, d.cidade, u.prestaAula
        order by u.nome asc
    """)
    fun findByAreaIdOrderByServicoAulaAsc(areaId: Int): List<UsuariosFilteredList>
    @Query("""
        select new manuall.newproject.dto.usuario.UsuariosFilteredList(u.id, u.nome, u.anexoPfp, u.area.id, u.orcamentoMin, u.orcamentoMax, d.cidade, u.prestaAula, COALESCE(avg(a.nota)), count(a.id))
        from Usuario u
        LEFT JOIN Avaliacao a ON u.id = a.prestadorUsuario.id
        JOIN DadosEndereco d ON u.id = d.usuario.id
        WHERE u.prestaAula = true
        and u.area.id = ?1
        and u.tipoUsuario = 2
        GROUP BY u.id, u.nome, u.anexoPfp, u.area.id, u.orcamentoMin, u.orcamentoMax, d.cidade, u.prestaAula
        order by u.nome desc
    """)
    fun findByAreaIdOrderByServicoAulaDesc(areaId: Int): List<UsuariosFilteredList>

}
