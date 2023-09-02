package manuall.api.dto.perfil

import io.swagger.v3.oas.annotations.media.Schema

data class AlterDescRequest (

    @Schema(example = "Template")
    val descricao: String
)