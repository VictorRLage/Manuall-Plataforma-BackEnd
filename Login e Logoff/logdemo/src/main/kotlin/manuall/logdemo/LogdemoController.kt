package manuall.logdemo

import manuall.logdemo.dataClass.Contratante
import manuall.logdemo.dataClass.Prestador
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/logdemo")
class LogdemoController {
    val perfil = Perfil()

    @PostMapping("/contratante/cadastro")
    fun cadastrar(@RequestBody novoPerfil:Contratante):String {
        return if (perfil.addPerfil(novoPerfil, true)) {
            "Perfil cadastrado com sucesso"
        } else {
            "Email já cadastrado"
        }
    }

    @PostMapping("/prestador/cadastro")
    fun cadastrar(@RequestBody novoPerfil:Prestador):String {
        return if (perfil.addPerfil(novoPerfil, true)) {
            "Perfil cadastrado com sucesso"
        } else {
            "Email já cadastrado"
        }
    }

    @GetMapping("/contratante/login")
    fun login(@RequestBody dadosLogin:Contratante):String {
        return perfil.login(dadosLogin)
    }

    @GetMapping("/prestador/login")
    fun login(@RequestBody dadosLogin:Prestador):String {
        return perfil.login(dadosLogin)
    }

    @GetMapping("/contratante/logoff")
    fun logoffC():String {
        return perfil.logoff()
    }

    @GetMapping("/prestador/logoff")
    fun logoffP():String {
        return perfil.logoff()
    }

    @GetMapping("/contratante/deletar")
    fun excluirC():String {
        return perfil.excluirConta()
    }

    @GetMapping("/prestador/deletar")
    fun excluirP():String {
        return perfil.excluirConta()
    }

    @GetMapping
    fun mostrar():String {
        var varFinal = "Contratantes\r\n"
        perfil.contratantes.forEach {
            varFinal +=
                """
{
    id: ${it.id}
    Nome: ${it.nome}
    dtNasc: ${it.dtNasc}
    Fone: ${it.fone}
    Email: ${it.email}
    Senha: ${it.senha}
}

                    """.trimMargin()
        }
        varFinal += "Prestadores\r\n"
        perfil.prestadores.forEach {
            varFinal +=
                """
{
    id: ${it.id}
    Nome: ${it.nome}
    dtNasc: ${it.dtNasc}
    cidadeEstado: ${it.cidadeEstado}
    Fone: ${it.fone}
    Email: ${it.email}
    Senha: ${it.senha}
    Aprovação: ${if (it.aprovado) {"Aprovado"} else {"Pendente"}}
}

                    """.trimMargin()
        }
        return varFinal
    }

    // Versão de visualização de perfis para navegador
    @GetMapping("/index")
    fun index():String {
        var varFinal:String =
            """
                <style>
                    .card {
                        width: 337.5px;
                        height: 200px;
                        border: 1px solid black;
                        padding: 15px;
                        display: flex;
                    }
                    .card1 {
                        width: 33.33%;
                        height: 100%;
                        display: flex;
                        flex-direction: column;
                        align-items: center;
                        justify-content: space-between;
                    }
                    .pfp {
                        width: 100px;
                        height: 100px;
                        border: 1px solid black;
                        border-radius: 20px;
                        margin: 2px;
                    }
                    .nome {
                        font-family: 'Trebuchet MS';
                        font-weight: normal;
                        font-size: 15px;
                        text-align: center;
                        margin: 2px;
                        height: 16px;
                        overflow-y: hidden;
                    }
                    .data {
                        font-family: 'Trebuchet MS';
                        font-weight: lighter;
                        font-size: 10px;
                        text-align: center;
                        margin: 2px;
                        max-height: 9px;
                        overflow-y: hidden;
                    }
                    .cep {
                        font-family: 'Trebuchet MS';
                        font-weight: normal;
                        font-size: 15px;
                        text-align: center;
                        margin: 2px;
                        max-height: 51px;
                        overflow-y: hidden;
                    }
                    .card2 {
                        width: 66.66%;
                        height: 100%;
                        display: flex;
                        flex-direction: column;
                        justify-content: space-around;
                        align-items: center;
                    }
                    .area {
                        display: flex;
                        justify-content: center;
                        align-items: center;
                        flex-direction: column;
                    }
                    .titulo {
                        font-family: "Trebuchet MS";
                        font-weight: bold;
                        font-size: 10px;
                        text-align: center;
                    }
                    .fone {
                        font-family: 'Trebuchet MS';
                        font-weight: lighter;
                        font-size: 18px;
                        text-align: center;
                        margin: 2px;
                        max-height: 18px;
                        overflow-y: hidden;
                    }
                </style>
            """.trimIndent()
        varFinal += "<h2>Contratantes</h2>"
        if (perfil.contratantes.isNotEmpty()) {
            varFinal +=
                """
                    <div style="display: flex; overflow-x: scroll;">
                """.trimIndent()
            perfil.contratantes.forEach {
                varFinal +=
                    """
                        <div class="card" style="height: 150px;">
                            <div class="card1" style="justify-content: space-around;">
                                <span class="fone">id <b>${it.id}</b></span>
                                <span class="nome">${it.nome}</span>
                                <span class="fone">${it.dtNasc}</span>
                            </div>
                            <div class="card2">
                                <div class="area">
                                    <div class="titulo">Email</div>
                                    <span class="email">${it.email}</span>
                                </div>
                                <div class="area">
                                    <div class="titulo">Telefone</div>
                                    <span class="fone">${it.fone}</span>
                                </div>
                                <div class="area">
                                    <div class="titulo">Senha</div>
                                    <span class="senha">${it.senha}</span>
                                </div>
                            </div>
                        </div>
                    """.trimIndent()
            }
            varFinal += "</div>"
        } else {
            varFinal += "Nenhum contratante cadastrado"
        }
        varFinal += "<h2>Prestadores</h2>"
        if (perfil.prestadores.isNotEmpty()) {
            varFinal +=
            """
                <div style="display: flex; overflow-x: scroll;">
            """.trimIndent()
            perfil.prestadores.forEach {
                varFinal +=
                    """
                    <div class="card">
                        <div class="card1">
                            <img src="${it.pfp}" class="pfp">
                            <span class="nome">${it.nome}</span>
                            <span class="data">${it.dtNasc}</span>
                            <span class="cep">${it.cidadeEstado}</span>
                        </div>
                        <div class="card2">
                            <div class="area">
                                <div class="titulo">Email</div>
                                <span class="email">${it.email}</span>
                            </div>
                            <div class="area">
                                <div class="titulo">Telefone</div>
                                <span class="fone">${it.fone}</span>
                            </div>
                            <div class="area">
                                <div class="titulo">Senha</div>
                                <span class="senha">${it.senha}</span>
                            </div>
                            <div class="area">
                                <div class="titulo">Aprovação</div>
                                <span class='senha' style='color:
                """.trimIndent()
                varFinal += if (it.aprovado) {
                    "green;'>Aprovado"
                } else {
                    "red;'>Pendente"
                }
                varFinal +=
                    """
                        </span></div>
                        <div class="area">
                            <span>id <b>${it.id}</b></span>
                        </div>
                        </div></div>
                    """.trimIndent()
            }
            varFinal += "</div>"
        } else {
            varFinal += "Nenhum prestador cadastrado"
        }

        return varFinal
    }
}