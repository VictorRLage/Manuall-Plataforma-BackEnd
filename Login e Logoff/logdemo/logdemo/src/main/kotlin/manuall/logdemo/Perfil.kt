package manuall.logdemo

import manuall.logdemo.dataClass.*

class Perfil {
    val contratantes = mutableListOf<Contratante>()
    val prestadores = mutableListOf<Prestador>()
    private var autoIncrementC = 0
    private var autoIncrementP = 0
    private var contaLogadaC:Contratante? = null
    private var contaLogadaP:Prestador? = null

    fun listar():Array<List<Any>> {
        return arrayOf(contratantes, prestadores)
    }

    fun addPerfil(novoPerfil:Contratante):Boolean {
        return if (contratantes.any { it.email == novoPerfil.email }) {
            false
        } else {
            autoIncrementC++
            novoPerfil.id = autoIncrementC
            contratantes.add(novoPerfil)
            true
        }
    }

    fun addPerfil(novoPerfil:Prestador):Boolean {
        return if (prestadores.any { it.email == novoPerfil.email }) {
            false
        } else {
            autoIncrementP++
            novoPerfil.id = autoIncrementP
            prestadores.add(novoPerfil)
            true
        }
    }

    fun login(dadosLogin:LoginRequest):String {
        return if (contaLogadaC != null || contaLogadaP != null) {
            "Você já está logado"
        } else {
            if (contratantes.any { it.email == dadosLogin.email }) {
                if (prestadores.any { it.email == dadosLogin.email }) {
                    """
                        Esse endereço de email possui cadastro nos dois tipos de conta
                        
                        Para entrar como Contratante, acesse
                        (POST) http://localhost:8080/logdemo/login/contratante
                        
                        Para entrar como Prestador, acesse
                        (POST) http://localhost:8080/logdemo/login/prestador
                        
                        utilize o mesmo formulário atual
                    """.trimIndent()
                } else {
                    val conta = contratantes.filter { it.email == dadosLogin.email }[0]
                    if (conta.senha == dadosLogin.senha) {
                        contaLogadaC = conta
                        "Login realizado com sucesso"
                    } else {
                        "Senha incorreta"
                    }
                }
            } else if (prestadores.any { it.email == dadosLogin.email }) {
                val conta = prestadores.filter { it.email == dadosLogin.email }[0]
                if (conta.senha == dadosLogin.senha) {
                    contaLogadaP = conta
                    "Login realizado com sucesso"
                } else {
                    "Senha incorreta"
                }
            } else {
                "Nenhuma conta cadastrada neste endereço de email"
            }
        }
    }

    fun loginC(dadosLogin:LoginRequest):String {
        return if (contaLogadaC != null || contaLogadaP != null) {
            "Você já está logado"
        } else {
            val conta = contratantes.filter { it.email == dadosLogin.email }
            if (conta.isNotEmpty()) {
                if (conta[0].senha == dadosLogin.senha) {
                    contaLogadaC = conta[0]
                    "Login realizado com sucesso"
                } else {
                    "Senha incorreta"
                }
            } else {
                "Nenhuma conta cadastrada neste endereço de email"
            }
        }

    }

    fun loginP(dadosLogin:LoginRequest):String {
        return if (contaLogadaC != null || contaLogadaP != null) {
            "Você já está logado"
        } else {
            val conta = prestadores.filter { it.email == dadosLogin.email }
            if (conta.isNotEmpty()) {
                if (conta[0].senha == dadosLogin.senha) {
                    contaLogadaP = conta[0]
                    "Login realizado com sucesso"
                } else {
                    "Senha incorreta"
                }
            } else {
                "Nenhuma conta cadastrada neste endereço de email"
            }
        }
    }

    fun logoff():String {
        return if (contaLogadaC == null && contaLogadaP == null) {
            "Nenhum perfil logado"
        } else {
            contaLogadaC = null
            contaLogadaP = null
            "Você foi deslogado com sucesso"
        }
    }

    fun verConta():Any {
        return if (contaLogadaC != null) {
            VerContratanteResponse(contaLogadaC!!)
        } else if (contaLogadaP != null) {
            VerPrestadorResponse(contaLogadaP!!)
        } else {
            "Nenhuma conta logada"
        }
    }

    fun excluirConta():String {
        return if (contaLogadaC != null) {
            contratantes.removeAt(contratantes.indexOf(contaLogadaC))
            contaLogadaC = null
            "Conta excluída com sucesso"
        } else if (contaLogadaP != null) {
            prestadores.removeAt(prestadores.indexOf(contaLogadaP))
            contaLogadaP = null
            "Conta excluída com sucesso"
        } else {
            "Nenhuma conta logada"
        }
    }

    fun alterarSenha(senhas:AlterarSenhaRequest):String {
        return if (contaLogadaC != null) {
            if (contaLogadaC!!.senha == senhas.senhaAtual) {
                contaLogadaC!!.senha = senhas.novaSenha
                "Senha alterada com sucesso"
            } else {
                "Senha incorreta"
            }
        } else if (contaLogadaP != null) {
            if (contaLogadaP!!.senha == senhas.senhaAtual) {
                contaLogadaP!!.senha = senhas.novaSenha
                "Senha alterada com sucesso"
            } else {
                "Senha incorreta"
            }
        } else {
            "Nenhuma conta logada"
        }
    }

    fun aprovarPerfil(id:Int):String {
        val perfilEncontrado = prestadores.filter { it.id == id }
        return if (perfilEncontrado.isNotEmpty()) {
            if (perfilEncontrado[0].aprovado) {
                "Perfil já aprovado"
            } else {
                perfilEncontrado[0].aprovado = true
                "Perfil aprovado com sucesso"
            }
        } else {
            "Nenhum perfil encontrado neste ID"
        }
    }
}