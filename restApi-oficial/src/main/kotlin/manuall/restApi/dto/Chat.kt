package manuall.restApi.dto

data class Chat (
    val idRemetente:Int,
    val idDestinatario:Int,
    val idMensagem:Int,
    val mensagem:String
)