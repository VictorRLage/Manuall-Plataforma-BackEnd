package manuall.logdemo.dataClass

class VerContratanteResponse (
    val nome:String,
    val dtNasc:String,
    val fone:String,
    val email:String,
    val senha:String
) {
    constructor(contratante:Contratante): this (
        contratante.nome,
        contratante.dtNasc,
        contratante.fone,
        contratante.email,
        contratante.senha
    )
}