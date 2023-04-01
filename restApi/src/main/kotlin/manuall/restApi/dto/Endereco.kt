package manuall.restApi.dto

data class Endereco (
    val estado:String,
    val cidade:String,
    val cep:Int,
    val bairro:String,
    val rua:String,
    val numero:Int,
    val complemento:String
)