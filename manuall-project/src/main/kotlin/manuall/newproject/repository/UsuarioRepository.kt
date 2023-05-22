package manuall.newproject.repository

import manuall.newproject.domain.Usuario
import manuall.newproject.dto.UsuariosFilteredList
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface UsuarioRepository: JpaRepository<Usuario, Int> {

    fun findByEmail(
        email: String?
    ): List<Optional<Usuario>>

    fun findByEmailAndSenha(
        email: String?,
        senha: String?
    ): Optional<Usuario>

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

    // lembrar de perguntar ao reis
//    @Query("""
//        select
//        new manuall.newproject.dto.UsuariosFilteredList(u.id, u.nome, u.anexoPfp, u.area.id, u.orcamentoMin, u.orcamentoMax, d.cidade, u.prestaAula, count(a))
//        from Usuario u, Avaliacao a, DadosEndereco d where u.id = a.prestadorUsuario.id and d.usuario.id = u.id order by a.nota asc
//    """)
//    fun orderByNotaAsc(): List<UsuariosFilteredList>

//    @Query("""
//        select
//        new manuall.newproject.dto.UsuariosFilteredList(
//        u.id, u.nome, u.anexoPfp, u.area.id, u.orcamentoMin, u.orcamentoMax, d.cidade, u.prestaAula, avg(a.nota), count(a)
//        )
//        from Usuario u, Avaliacao a, DadosEndereco d where u.id = a.prestadorUsuario.id and d.usuario.id = u.id order by a.nota desc
//    """)
//    fun orderByNotaDesc(): List<UsuariosFilteredList>

}
