package manuall.api.repository

import manuall.api.domain.Solicitacao
import manuall.api.dto.solicitacao.NotificacaoDto
import manuall.api.dto.chat.ChatPegarDadosDestinatarioDto
import manuall.api.dto.chat.ChatPegarDadosDestinatariosDto
import manuall.api.dto.dashboard.PegarRegiaoDto
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface SolicitacaoRepository: JpaRepository<Solicitacao, Int> {

    @Query("""
        select
        new manuall.api.dto.chat.ChatPegarDadosDestinatarioDto(s.contratanteUsuario.id, u.nome)
        from Solicitacao s, Usuario u
        where u.id = s.contratanteUsuario.id
        and s.id = ?1
    """)
    fun getDadosContratanteById(id: Int): Optional<ChatPegarDadosDestinatarioDto>

    @Query("""
        select
        new manuall.api.dto.chat.ChatPegarDadosDestinatarioDto(s.prestadorUsuario.id, u.nome)
        from Solicitacao s, Usuario u
        where u.id = s.prestadorUsuario.id
        and s.id = ?1
    """)
    fun getDadosPrestadorById(id: Int): Optional<ChatPegarDadosDestinatarioDto>

    @Query("""
        select
        new manuall.api.dto.chat.ChatPegarDadosDestinatariosDto(s.id, s.prestadorUsuario.id, u.nome)
        from Solicitacao s, Usuario u
        where u.id = s.prestadorUsuario.id
        and s.contratanteUsuario.id = ?1
        and s.status = 2
    """)
    fun getPrestadoresByContratanteUsuarioId(id: Int): List<ChatPegarDadosDestinatariosDto>

    @Query("""
        select
        new manuall.api.dto.chat.ChatPegarDadosDestinatariosDto(s.id, s.contratanteUsuario.id, u.nome)
        from Solicitacao s, Usuario u
        where u.id = s.contratanteUsuario.id
        and s.prestadorUsuario.id = ?1
        and s.status = 2
    """)
    fun getContratantesByPrestadorUsuarioId(id: Int): List<ChatPegarDadosDestinatariosDto>


    @Query("""
        select
        new manuall.api.dto.dashboard.PegarRegiaoDto(e.cidade, e.estado)
        from Solicitacao s, Usuario u, DadosEndereco e
        where u.id = s.contratanteUsuario.id and u.id = e.usuario.id
        and s.status = 2
    """)
    fun findByContratanteUsuario():List<PegarRegiaoDto>

    fun findByContratanteUsuarioIdOrderByIdDesc(id: Int): List<Solicitacao>

    fun findByPrestadorUsuarioIdOrderByIdDesc(id: Int): List<Solicitacao>

    @Query("SELECT new manuall.api.dto.solicitacao.NotificacaoDto(s.id, s.prestadorUsuario.nome, s.descricao) FROM Solicitacao s WHERE s.prestadorUsuario.id = ?1")
    fun findAllByUsuarioId(usuarioId: Int): List<NotificacaoDto>
}
