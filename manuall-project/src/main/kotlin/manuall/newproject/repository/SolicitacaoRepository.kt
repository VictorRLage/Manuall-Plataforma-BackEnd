package manuall.newproject.repository

import manuall.newproject.domain.Solicitacao
import manuall.newproject.dto.*
import manuall.newproject.dto.avaliacao.NotificacaoDto
import manuall.newproject.dto.chat.ChatPegarDadosDestinatarioDto
import manuall.newproject.dto.chat.ChatPegarDadosDestinatariosDto
import manuall.newproject.dto.dashboard.PegarRegiaoDto
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface SolicitacaoRepository: JpaRepository<Solicitacao, Int> {

    // perguntar pro reis sobre essa rota
//    @Query("""
//        select
//        new manuall.newproject.dto.chat.ChatPegarDadosDestinatarioDto(CASE ?2 WHEN 1 THEN (s.contratanteUsuario.id) ELSE (s.prestadorUsuario.id) END, u.nome)
//        from Solicitacao s, Usuario u
//        where u.id = s.contratanteUsuario.id
//        and s.id = ?1
//    """)
//    fun getDadosById(id: Int, tipoUsuario: Int): Optional<ChatPegarDadosDestinatarioDto>

    @Query("""
        select
        new manuall.newproject.dto.chat.ChatPegarDadosDestinatarioDto(s.contratanteUsuario.id, u.nome)
        from Solicitacao s, Usuario u
        where u.id = s.contratanteUsuario.id
        and s.id = ?1
    """)
    fun getDadosContratanteById(id: Int): Optional<ChatPegarDadosDestinatarioDto>

    @Query("""
        select
        new manuall.newproject.dto.chat.ChatPegarDadosDestinatarioDto(s.prestadorUsuario.id, u.nome)
        from Solicitacao s, Usuario u
        where u.id = s.prestadorUsuario.id
        and s.id = ?1
    """)
    fun getDadosPrestadorById(id: Int): Optional<ChatPegarDadosDestinatarioDto>

    @Query("""
        select
        new manuall.newproject.dto.chat.ChatPegarDadosDestinatariosDto(s.id, s.prestadorUsuario.id, u.nome)
        from Solicitacao s, Usuario u
        where u.id = s.prestadorUsuario.id
        and s.contratanteUsuario.id = ?1
        and s.status = 2
    """)
    fun getPrestadoresByContratanteUsuarioId(id: Int): List<ChatPegarDadosDestinatariosDto>

    @Query("""
        select
        new manuall.newproject.dto.chat.ChatPegarDadosDestinatariosDto(s.id, s.contratanteUsuario.id, u.nome)
        from Solicitacao s, Usuario u
        where u.id = s.contratanteUsuario.id
        and s.prestadorUsuario.id = ?1
        and s.status = 2
    """)
    fun getContratantesByPrestadorUsuarioId(id: Int): List<ChatPegarDadosDestinatariosDto>


    @Query("""
        select
        new manuall.newproject.dto.dashboard.PegarRegiaoDto(e.cidade, e.estado)
        from Solicitacao s, Usuario u, DadosEndereco e
        where u.id = s.contratanteUsuario.id and u.id = e.usuario.id
        and s.status = 2
    """)
    fun findByContratanteUsuario():List<PegarRegiaoDto>

    fun findByContratanteUsuarioIdOrderByIdDesc(id: Int): List<Solicitacao>

    fun findByPrestadorUsuarioIdOrderByIdDesc(id: Int): List<Solicitacao>

    @Query("SELECT new manuall.newproject.dto.avaliacao.NotificacaoDto(s.id, s.prestadorUsuario.nome, s.descricao) FROM Solicitacao s WHERE s.prestadorUsuario.id = ?1")
    fun findAllByUsuarioId(usuarioId: Int): List<NotificacaoDto>
}
