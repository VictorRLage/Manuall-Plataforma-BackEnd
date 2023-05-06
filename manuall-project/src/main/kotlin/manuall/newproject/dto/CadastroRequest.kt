package manuall.newproject.dto

import manuall.newproject.domain.*

data class CadastroRequest (
    val areaUsuario: List<AreaUsuario>,
    val dadosBancarios: DadosBancarios,
    val dadosEndereco: DadosEndereco,
    val descServicos: List<DescServicos>,
    val usuario: Usuario
)