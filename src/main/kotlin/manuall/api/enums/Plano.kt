package manuall.api.enums

enum class Plano(
    val id: Int,
    val texto: String,
) {
    BASICO(
        id = 1,
        texto = "Básico",
    ),
    PREMIUM(
        id = 2,
        texto = "Premium",
    ),
    ADVANCED(
        id = 3,
        texto = "Advanced",
    );

    companion object {

        fun fromIdToTexto(id: Int): String =
            values().first { it.id == id }.texto

        fun fromTextoToId(texto: String): Int =
            values().first { it.texto == texto }.id

    }
}