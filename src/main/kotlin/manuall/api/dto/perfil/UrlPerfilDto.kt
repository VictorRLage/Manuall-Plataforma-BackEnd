package manuall.api.dto.perfil

import io.swagger.v3.oas.annotations.media.Schema

data class UrlPerfilDto (
    @Schema(example = "[\"https://source.unsplash.com/collection/2161550/1200x800\", \"https://loremflickr.com/800/600\", \"https://picsum.photos/seed/picsum/300/200\"]")
    val imagens: List<String>
)