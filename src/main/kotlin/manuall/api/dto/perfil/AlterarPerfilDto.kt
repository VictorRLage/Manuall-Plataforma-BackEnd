package manuall.api.dto.perfil

import io.swagger.v3.oas.annotations.media.Schema
import manuall.api.domain.Area

data class AlterarPerfilDto(
    @Schema(example = "{\"id\": 2, \"nome\": \"Pintor\"}")
    val area: Area,

    @Schema(example = "Sou o melhor pintor do meu bairro")
    val descricao: String,

    @Schema(example = "https://st.depositphotos.com/1010338/2099/i/600/depositphotos_20999947-stock-photo-tropical-island-with-palms.jpg")
    val profile: String,

    @Schema(example = "Rodrigo Amaral")
    val nome: String,

    @Schema(example = "500.0")
    val orcamentoMin: Double,

    @Schema(example = "1400.0")
    val orcamentoMax: Double,

    @Schema(example = "false")
    val prestaAula: Boolean,

    @Schema(example = "São Paulo")
    val estado: String,

    @Schema(example = "São Paulo")
    val cidade: String,

    @Schema(example = "https://st.depositphotos.com/1010338/2099/i/600/depositphotos_20999947-stock-photo-tropical-island-with-palms.jpg")
    val imagens: List<String>,

    @Schema(example = "Pintura")
    val servicos: List<String>
)