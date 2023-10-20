package manuall.api.repository

import manuall.api.domain.Chat
import manuall.api.dto.chat.ChatMensagemResponse
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.util.*


@Repository
interface ChatRepository: JpaRepository<Chat, Int> {

    @Query("""
        SELECT c FROM Chat c
        WHERE c.solicitacao.prestador.id = :prestadorId
        AND c.idRemetente = :prestadorId
        AND c.horario BETWEEN :startDate AND :endDate
        ORDER BY c.horario ASC
    """)
    fun findMessagesBySolicitacaoId(
        @Param("prestadorId") prestadorId: Int,
        @Param("startDate") startDate: Date,
        @Param("endDate") endDate: Date
    ): List<Chat>

    @Query("""
        select
        new manuall.api.dto.chat.ChatMensagemResponse(c.id, CASE c.idRemetente WHEN ?1 THEN true ELSE false END, c.mensagem, c.horario, c.anexo)
        from Chat c
        where c.solicitacao.contratante.id = ?1
        and c.solicitacao.id = ?2
    """)
    fun getMsgsByUsuarioIdAndSolicitacaoIdContratante(idUsuario: Int, idSolicitacao: Int): List<ChatMensagemResponse>

    @Query("""
        select
        new manuall.api.dto.chat.ChatMensagemResponse(c.id, CASE c.idRemetente WHEN ?1 THEN true ELSE false END, c.mensagem, c.horario, c.anexo)
        from Chat c
        where c.solicitacao.prestador.id = ?1
        and c.solicitacao.id = ?2
    """)
    fun getMsgsByUsuarioIdAndSolicitacaoIdPrestador(idUsuario: Int, idSolicitacao: Int): List<ChatMensagemResponse>

    @Query("""
        select
        new manuall.api.dto.chat.ChatMensagemResponse(c.id, CASE c.idRemetente WHEN ?1 THEN true ELSE false END, c.mensagem, c.horario, c.anexo)
        from Chat c
        where c.solicitacao.contratante.id = ?1
        and c.solicitacao.id = ?2
        and c.id > ?3
    """)
    fun getBySolicitacaoIdWhereSolicitacaoIdHigherThanContratante(idUsuario: Int, idSolicitacao: Int, idMsgAtual: Int): List<ChatMensagemResponse>

    @Query("""
        select
        new manuall.api.dto.chat.ChatMensagemResponse(c.id, CASE c.idRemetente WHEN ?1 THEN true ELSE false END, c.mensagem, c.horario, c.anexo)
        from Chat c
        where c.solicitacao.prestador.id = ?1
        and c.solicitacao.id = ?2
        and c.id > ?3
    """)
    fun getBySolicitacaoIdWhereSolicitacaoIdHigherThanPrestador(idUsuario: Int, idSolicitacao: Int, idMsgAtual: Int): List<ChatMensagemResponse>

    fun deleteBySolicitacaoId(id: Int)
}