package manuall.newproject.repository

import manuall.newproject.domain.Solicitacao
import manuall.newproject.domain.Usuario
import manuall.newproject.dto.ChatPegarDadosDestinatarioDto
import manuall.newproject.dto.ChatPegarDadosDestinatariosDto
import manuall.newproject.dto.PegarRegiaoDTO
import manuall.newproject.dto.SolicitacaoDto
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface SolicitacaoRepository: JpaRepository<Solicitacao, Int> {

    // perguntar pro reis sobre essa rota
//    @Query("""
//        select
//        new manuall.newproject.dto.ChatPegarDadosDestinatarioDto(CASE ?2 WHEN 1 THEN (s.contratanteUsuario.id) ELSE (s.prestadorUsuario.id) END, u.nome)
//        from Solicitacao s, Usuario u
//        where u.id = s.contratanteUsuario.id
//        and s.id = ?1
//    """)
//    fun getDadosById(id: Int, tipoUsuario: Int): Optional<ChatPegarDadosDestinatarioDto>

    @Query("""
        select
        new manuall.newproject.dto.ChatPegarDadosDestinatarioDto(s.contratanteUsuario.id, u.nome)
        from Solicitacao s, Usuario u
        where u.id = s.contratanteUsuario.id
        and s.id = ?1
    """)
    fun getDadosContratanteById(id: Int): Optional<ChatPegarDadosDestinatarioDto>

    @Query("""
        select
        new manuall.newproject.dto.ChatPegarDadosDestinatarioDto(s.prestadorUsuario.id, u.nome)
        from Solicitacao s, Usuario u
        where u.id = s.prestadorUsuario.id
        and s.id = ?1
    """)
    fun getDadosPrestadorById(id: Int): Optional<ChatPegarDadosDestinatarioDto>

    @Query("""
        select
        new manuall.newproject.dto.ChatPegarDadosDestinatariosDto(s.id, s.prestadorUsuario.id, u.nome)
        from Solicitacao s, Usuario u
        where u.id = s.prestadorUsuario.id
        and s.contratanteUsuario.id = ?1
        and s.status = 2
    """)
    fun getPrestadoresByContratanteUsuarioId(id: Int): List<ChatPegarDadosDestinatariosDto>

    @Query("""
        select
        new manuall.newproject.dto.ChatPegarDadosDestinatariosDto(s.id, s.contratanteUsuario.id, u.nome)
        from Solicitacao s, Usuario u
        where u.id = s.contratanteUsuario.id
        and s.prestadorUsuario.id = ?1
        and s.status = 2
    """)
    fun getContratantesByPrestadorUsuarioId(id: Int): List<ChatPegarDadosDestinatariosDto>


    @Query("""
        select
        new manuall.newproject.dto.PegarRegiaoDTO(e.cidade, e.estado)
        from Solicitacao s, Usuario u, DadosEndereco e
        where u.id = s.contratanteUsuario.id and u.id = e.usuario.id
        and s.status = 2
    """)
    fun findByContratanteUsuarioId():List<PegarRegiaoDTO>


    @Query("SELECT new manuall.newproject.dto.SolicitacaoDto(s.contratanteUsuario.id, s.prestadorUsuario.id, s.tamanho, s.medida, s.descricao, s.status, s.servico.id, s.avaliacao.id) FROM Solicitacao s WHERE s.contratanteUsuario.id = ?1 OR s.prestadorUsuario.id = ?1")
    fun findAllByUsuarioId(usuarioId: Int): List<SolicitacaoDto>
}