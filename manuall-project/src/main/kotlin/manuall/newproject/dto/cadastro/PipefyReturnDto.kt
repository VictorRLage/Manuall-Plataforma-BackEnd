package manuall.newproject.dto.cadastro

import manuall.newproject.domain.Area

class PipefyReturnDto {
    var nome:String? = null
    var telefone:String? = null
    var optCidade:String? = null
    var area:List<Area>? = null
    var blnInteresseEnsinar:Boolean? = null // falta retorno do Contratante
}