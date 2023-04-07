package manuall.restApi.dto

import java.sql.Blob
import java.sql.Date

data class Usuario (
    val idUsuario:Int,
    val nome:String,
    val email:String,
    val senha:String,
    val cpf:String,
    val aprovado:Boolean,
    val enderecos:List<Endereco>,
    val tipoPerfil:Int,
    val ftPerfil:Blob,
    val rgFrente:Blob,
    val rgVerso:Blob,
    val areas:List<String>,
    val plano:Int,
    val nomeCartao:String,
    val numeroCartao:Int,
    val validade:Date,
    val cvv:String
)