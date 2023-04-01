package manuall.altCrud.dto

data class UsuarioResponse (
    var nome:String,
    var email:String,
    var senha:String
) {
    constructor(usuario: Usuario): this(
        usuario.nome,
        usuario.email,
        usuario.senha
    )
}