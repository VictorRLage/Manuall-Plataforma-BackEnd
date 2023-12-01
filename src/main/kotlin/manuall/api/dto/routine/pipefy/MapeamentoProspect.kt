package manuall.api.dto.routine.pipefy

data class MapeamentoProspect(
    val idCliente: Int,
    val status: Int,
    val tipoUsuario: Int?,
    val optCanal: Int?,
    val nome: String?,
    val email: String?,
    val fone: String?,
    val optCidade:Int?,
    val blnConheceManuall: Boolean?,
    val areaId: Int?,
    val blnAprender: Boolean?,
    val blnInteresseManuall: Boolean?,
    val blnInteresseEnsinar: Boolean?,
    val blnCupom: Boolean?,
    val msgDesistencia: String?,
)
