package manuall.api.dto.usuario

data class AprovacaoDto (
    val dados: AprovacaoSubDto,
    val servicos: List<String>
)