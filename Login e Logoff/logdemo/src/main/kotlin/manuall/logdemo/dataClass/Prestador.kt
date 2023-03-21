package manuall.logdemo.dataClass

data class Prestador (
    var id:Int,
    val nome:String,
    val pfp:String,
    val dtNasc:String,
    val cidadeEstado:String,
    val fone:String,
    val email:String,
    val senha:String,
    var aprovado:Boolean = false
)