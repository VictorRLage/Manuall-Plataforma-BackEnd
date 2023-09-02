package manuall.api.dto.usuario

data class AprovacaoSubDto (
    val id: Int,
    val nome: String,
    val fotoPerfil: String?,
    val email: String,
    val telefone: String,
    val cpf: String,
    val cidade: String,
    val estado: String,
    val cep: String,
    val bairro: String,
    val rua: String,
    val numero: Int?,
    val complemento: String?,
    val area: String,
    val ensino: Boolean,
    val orcamentoMin: Double,
    val orcamentoMax: Double,
)