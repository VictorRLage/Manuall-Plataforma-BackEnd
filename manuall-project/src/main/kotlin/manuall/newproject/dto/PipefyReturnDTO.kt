package manuall.newproject.dto

import manuall.newproject.domain.Area

class PipefyReturnDTO (){
    var nome:String? = null
    var telefone:String? = null
    var optCidade:String? = null
    var area:List<Area>? = null
    var blnInteresseEnsinar:Boolean? = null // falta retorno do Contratante
}