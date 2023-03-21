package manuall.logdemo.dataClass

class VerPrestadorResponse(
    val nome:String,
    val dtNasc:String,
    val cidadeEstado:String,
    val fone:String,
    val email:String,
    val senha:String,
    val aprovado: String
) {
    constructor(prestador:Prestador): this (
        prestador.nome,
        prestador.dtNasc,
        prestador.cidadeEstado,
        prestador.fone,
        prestador.email,
        prestador.senha,
        if (prestador.aprovado) {"PERFIL APROVADO"} else {"PERFIL REPROVADO"}
    )
}