package manuall.api.dto.resetsenha

data class Email(
    val to: String,
    val subject: String,
    val text: String
)