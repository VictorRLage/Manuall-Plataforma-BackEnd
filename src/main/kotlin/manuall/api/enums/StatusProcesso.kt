package manuall.api.enums

enum class StatusProcesso(
    val id: Int,
    val texto: String,
) {
    AGUARDANDO(
        id = 1,
        texto = "Pendente",
    ),
    PROCESSANDO(
        id = 2,
        texto = "Agendado",
    ),
    FINALIZADO(
        id = 3,
        texto = "Finalizado",
    );

    companion object {

        fun fromIdToTexto(id: Int): String =
            StatusProcesso.values().first { it.id == id }.texto

        fun fromTextoToId(texto: String): Int =
            StatusProcesso.values().first { it.texto == texto }.id
    }
}
