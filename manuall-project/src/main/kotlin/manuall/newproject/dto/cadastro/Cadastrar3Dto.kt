package manuall.newproject.dto.cadastro

data class Cadastrar3Dto (
    val area:Int,
    val servico:List<Int>,
    val prestaAula: Boolean,
    val orcamentoMin:Double,
    val orcamentoMax:Double,
)