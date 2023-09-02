package manuall.api.dto.cadastro

data class Cadastrar3Dto (
    val area:Int,
    val servico:List<Int>,
    val prestaAula: Boolean,
    val orcamentoMin:Double,
    val orcamentoMax:Double,
)