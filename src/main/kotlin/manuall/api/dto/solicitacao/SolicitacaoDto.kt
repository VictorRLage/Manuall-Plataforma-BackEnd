package manuall.api.dto.solicitacao

import io.swagger.v3.oas.annotations.media.Schema

data class SolicitacaoDto (

    @Schema(example = "2")
    val idPrestador: Int,

    @Schema(example = "6")
    val idServico: Int,

    @Schema(example = "10")
    val tamanho: Double,

    @Schema(example = "cm")
    val medida: String,

    @Schema(example = "pode trocar uma lâmpada aqui amigo? ta complicado :/")
    val descricao: String,

    @Schema(example = """
        [
            "meuTeto.png"
        ]
    """)
    val anexo: List<String>
)