package manuall.altCrud.dto

data class Usuario (
    val idUsuario:Int,
    var nome:String,
    var email:String,
    var senha:String
) {
    constructor() : this(0,"","","")
}