package manuall.api.dto.cadastro

data class Cadastrar2Dto (
    val cep:String,
    val estado:String,
    val cidade:String,
    val bairro:String,
    val rua:String,
    val numero:Int,
    val complemento:String?
)