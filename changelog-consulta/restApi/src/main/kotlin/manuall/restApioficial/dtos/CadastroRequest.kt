package manuall.restApioficial.dtos

import manuall.restApioficial.models.*

data class CadastroRequest (
    val areaUsuario: AreaUsuario,
    val dadosBancarios: DadosBancarios,
    val dadosEndereco: DadosEndereco,
    val descServicos: DescServicos,
    val usuario: Usuario
)