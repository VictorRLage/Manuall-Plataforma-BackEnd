package manuall.newproject.dto.usuario

import io.swagger.v3.oas.annotations.media.Schema

data class AlterPfpRequest (

    @Schema(example = "https://st.depositphotos.com/1010338/2099/i/600/depositphotos_20999947-stock-photo-tropical-island-with-palms.jpg")
    val novaUrl: String
)