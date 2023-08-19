package manuall.newproject.dto.usuario

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.PositiveOrZero
import jakarta.validation.constraints.Size
import org.hibernate.validator.constraints.URL

data class FilteredUsuario (

    @field:NotNull
    val id: Int,

    @field:NotBlank
    @field:Size(max = 60)
    val nome: String,

    @field:NotBlank
    @field:Size(max = 90)
    @field:URL
    val anexoPfp: String?,

    val idArea: Int?,

    @field:PositiveOrZero
    val orcamentoMin: Double?,

    @field:PositiveOrZero
    val orcamentoMax: Double?,

    @field:NotBlank
    val cidade: String?,

    val prestaAula: Boolean?,

    val mediaAvaliacoes: Double?,

    val qntAvaliacoes: Long
)
