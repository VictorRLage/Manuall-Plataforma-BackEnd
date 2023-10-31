package manuall.api.dto.mensagem

data class ChatResponse(
    val solicitacaoId: Int,
    val usuarioNome: String?,
    val usuarioPfp: String?,
    val mensagens: List<MensagemDto>,
)