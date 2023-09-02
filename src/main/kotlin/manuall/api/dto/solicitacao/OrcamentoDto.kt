package manuall.api.dto.solicitacao

import io.swagger.v3.oas.annotations.media.Schema

data class OrcamentoDto (
    @Schema(example = "1")
    var solicitacaoId: Int,

    @Schema(example = "Quero que você pinte toda a parede do meu banheiro")
    var mensagem: String,

    @Schema(example = "500.0")
    var orcamento: Double
)