package manuall.api.repository

import manuall.api.domain.Solicitacao
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
        new manuall.api.dto.chat.ChatPegarDadosDestinatarioDto(s.contratante.id, u.nome)
        from Solicitacao s, Usuario u
        where u.id = s.contratante.id
        and s.id = ?1
    """)
    fun getDadosContratanteById(id: Int): Optional<ChatPegarDadosDestinatarioDto>

    @Query("""
        select
        new manuall.api.dto.chat.ChatPegarDadosDestinatarioDto(s.prestador.id, u.nome)
        from Solicitacao s, Usuario u
        where u.id = s.prestador.id
        and s.id = ?1
    """)
    fun getDadosPrestadorById(id: Int): Optional<ChatPegarDadosDestinatarioDto>

    @Query("""
        select
        new manuall.api.dto.chat.ChatPegarDadosDestinatariosDto(s.id, s.prestador.id, u.nome)
        from Solicitacao s, Usuario u
        where u.id = s.prestador.id
        and s.contratante.id = ?1
        and s.status = 2
    """)
    fun getPrestadoresByContratanteId(id: Int): List<ChatPegarDadosDestinatariosDto>

    @Query("""
        select
        new manuall.api.dto.chat.ChatPegarDadosDestinatariosDto(s.id, s.contratante.id, u.nome)
        from Solicitacao s, Usuario u
        where u.id = s.contratante.id
        and s.prestador.id = ?1
        and s.status = 2
    """)
    fun getContratantesByPrestadorId(id: Int): List<ChatPegarDadosDestinatariosDto>


    @Query("""
        select
        new manuall.api.dto.dashboard.PegarRegiaoDto(e.cidade, e.estado)
        from Solicitacao s, Usuario u, DadosEndereco e
        where u.id = s.contratante.id and u.id = e.usuario.id
        and s.status = 2
    """)
    fun findByContratante():List<PegarRegiaoDto>

    fun findByContratanteIdOrderByIdDesc(id: Int): List<Solicitacao>

    fun findByPrestadorIdOrderByIdDesc(id: Int): List<Solicitacao>
}
