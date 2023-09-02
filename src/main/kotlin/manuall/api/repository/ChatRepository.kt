package manuall.api.repository

import manuall.api.domain.Chat
import manuall.api.dto.chat.ChatMensagemResponse
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface ChatRepository: JpaRepository<Chat, Int> {

    @Query("""
        select
        new manuall.api.dto.chat.ChatMensagemResponse(c.id, CASE c.idRemetente WHEN ?1 THEN true ELSE false END, c.mensagem, c.horario, c.anexo)
        from Chat c
        where c.solicitacao.contratanteUsuario.id = ?1
        and c.solicitacao.id = ?2
    """)
    fun getMsgsByUsuarioIdAndSolicitacaoIdContratante(idUsuario: Int, idSolicitacao: Int): List<ChatMensagemResponse>

    @Query("""
        select
        new manuall.api.dto.chat.ChatMensagemResponse(c.id, CASE c.idRemetente WHEN ?1 THEN true ELSE false END, c.mensagem, c.horario, c.anexo)
        from Chat c
        where c.solicitacao.prestadorUsuario.id = ?1
        and c.solicitacao.id = ?2
    """)
    fun getMsgsByUsuarioIdAndSolicitacaoIdPrestador(idUsuario: Int, idSolicitacao: Int): List<ChatMensagemResponse>

    @Query("""
        select
        new manuall.api.dto.chat.ChatMensagemResponse(c.id, CASE c.idRemetente WHEN ?1 THEN true ELSE false END, c.mensagem, c.horario, c.anexo)
        from Chat c
        where c.solicitacao.contratanteUsuario.id = ?1
        and c.solicitacao.id = ?2
        and c.id > ?3
    """)
    fun getBySolicitacaoIdWhereSolicitacaoIdHigherThanContratante(idUsuario: Int, idSolicitacao: Int, idMsgAtual: Int): List<ChatMensagemResponse>

    @Query("""
        select
        new manuall.api.dto.chat.ChatMensagemResponse(c.id, CASE c.idRemetente WHEN ?1 THEN true ELSE false END, c.mensagem, c.horario, c.anexo)
        from Chat c
        where c.solicitacao.prestadorUsuario.id = ?1
        and c.solicitacao.id = ?2
        and c.id > ?3
    """)
    fun getBySolicitacaoIdWhereSolicitacaoIdHigherThanPrestador(idUsuario: Int, idSolicitacao: Int, idMsgAtual: Int): List<ChatMensagemResponse>

    fun deleteBySolicitacaoId(id: Int)
}