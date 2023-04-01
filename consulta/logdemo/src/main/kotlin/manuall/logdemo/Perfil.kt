package manuall.logdemo

import manuall.logdemo.dataClass.Contratante
import manuall.logdemo.dataClass.Prestador

class Perfil {
    val contratantes = mutableListOf<Contratante>()
    val prestadores = mutableListOf<Prestador>()
    private var autoIncrementC = 0
    private var autoIncrementP = 0
    private var contaLogadaC:Contratante? = null
    private var contaLogadaP:Prestador? = null

    fun addPerfil(novoPerfil:Contratante, autoIncrementID:Boolean):Boolean {
        return if (contratantes.any { it.email == novoPerfil.email }) {
            false
        } else {
            if (autoIncrementID) {
                autoIncrementC++
                novoPerfil.id = autoIncrementC
            }
            contratantes.add(novoPerfil)
            true
        }
    }

    fun addPerfil(novoPerfil:Prestador, autoIncrementID:Boolean):Boolean {
        return if (prestadores.any { it.email == novoPerfil.email }) {
            false
        } else {
            if (autoIncrementID) {
                autoIncrementP++
                novoPerfil.id = autoIncrementP
            }
            prestadores.add(novoPerfil)
            true
        }
    }

    fun login(dadosLogin:Contratante):String {
        return if (contaLogadaC == null && contaLogadaP == null) {
            val contaEncontrada = contratantes.filter { it.email == dadosLogin.email }
            if (contaEncontrada.isNotEmpty()) {
                if (contaEncontrada[0].senha == dadosLogin.senha) {
                    contaLogadaC = contaEncontrada[0]
                    "Login bem sucedido: $contaLogadaC"
                } else {
                    "Senha incorreta"
                }
            } else {
                "Email não cadastrado"
            }
        } else {
            "Login já realizado"
        }
    }

    fun login(dadosLogin:Prestador):String {
        return if (contaLogadaC == null && contaLogadaP == null) {
            val contaEncontrada = prestadores.filter { it.email == dadosLogin.email }
            if (contaEncontrada.isNotEmpty()) {
                if (contaEncontrada[0].senha == dadosLogin.senha) {
                    contaLogadaP = contaEncontrada[0]
                    "Login bem sucedido: $contaLogadaP"
                } else {
                    "Senha incorreta"
                }
            } else {
                "Email não cadastrado"
            }
        } else {
            "Login já realizado"
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
}