package manuall.newproject.dto

data class UsuariosFilteredList (
    val id: Int,
    val nome: String,
    val anexoPfp: String?,
    val idArea: String?,
    val orcamentoMin: Double?,
    val orcamentoMax: Double?,
    val cidade: String?,
    val prestaAula: Boolean?,
    //val mediaAvaliacoes: Double,
    val qntAvaliacoes: Int
)
// {
//    constructor(
//        id: Int,
//        nome: String,
//        anexoPfp: String?,
//        idArea: String?,
//        orcamentoMin: Double?,
//        orcamentoMax: Double?,
//        cidade: String?,
//        prestaAula: Boolean?,
//        qntAvaliacoes: Int
//    ) : this(
//        id,
//        nome,
//        anexoPfp,
//        idArea,
//        orcamentoMin,
//        orcamentoMax,
//        cidade,
//        prestaAula,
//        qntAvaliacoes
//    )
//}