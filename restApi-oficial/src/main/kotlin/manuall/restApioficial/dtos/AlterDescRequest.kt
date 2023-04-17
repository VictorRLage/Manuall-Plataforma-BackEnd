package manuall.restApioficial.dtos

import manuall.restApioficial.models.DescServicos

data class AlterDescRequest (
    val id:Int,
    val descricao:String
)