package manuall.api.dto.perfil

import io.swagger.v3.oas.annotations.media.Schema
import org.hibernate.validator.constraints.URL

data class UrlImagemDto (

    @field:URL
    @Schema(example = "https://source.unsplash.com/collection/2161550/1200x800")
    val imagem: String
)