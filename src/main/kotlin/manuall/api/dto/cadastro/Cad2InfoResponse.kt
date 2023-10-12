package manuall.api.dto.cadastro

data class Cad2InfoResponse(
    val cep:String?,
    val estado:String?,
    val cidade:String?,
    val bairro:String?,
    val rua:String?,
    val numero:Int?,
    val complemento:String?
)