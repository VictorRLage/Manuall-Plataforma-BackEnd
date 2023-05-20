package manuall.newproject.dto

import manuall.newproject.domain.Servico

class Cadastrar3PrestDTO(
    val area:Int,
    val servico:List<Servico>,
    val prestaAula: Boolean,
    val orcamentoMin:Double,
    val orcamentoMax:Double,
) {
}