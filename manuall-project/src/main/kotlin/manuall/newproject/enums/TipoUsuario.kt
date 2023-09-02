package manuall.newproject.enums

import manuall.newproject.domain.Administrador
import manuall.newproject.domain.Contratante
import manuall.newproject.domain.Prestador
import manuall.newproject.domain.Usuario

enum class TipoUsuario(
    val inteiro: Int,
    val string: String,
    val classe: Class<out Usuario>
) {
    CONTRATANTE(
        inteiro = 1,
        string = "1",
        classe = Contratante::class.java,
    ),
    PRESTADOR(
        inteiro = 2,
        string = "2",
        classe = Prestador::class.java,
    ),
    ADMINISTRADOR(
        inteiro = 3,
        string = "3",
        classe = Administrador::class.java,
    );

    companion object {

        fun fromClassToInt(classe: Class<out Usuario>): Int =
            values().first { it.classe == classe }.inteiro
        fun fromClassToString(classe: Class<out Usuario>): String =
            values().first { it.classe == classe }.string
        fun fromClassToObject(classe: Class<out Usuario>): Usuario =
            values().first { it.classe == classe }.classe.getDeclaredConstructor().newInstance()

        fun fromIntToClass(inteiro: Int): Class<out Usuario> =
            values().first { it.inteiro == inteiro }.classe
        fun fromIntToString(inteiro: Int): String =
            values().first { it.inteiro == inteiro }.string
        fun fromIntToObject(inteiro: Int): Usuario =
            values().first { it.inteiro == inteiro }.classe.getDeclaredConstructor().newInstance()

        fun fromStringToClass(string: String): Class<out Usuario> =
            values().first { it.string == string }.classe
        fun fromStringToInt(string: String): Int =
            values().first { it.string == string }.inteiro
        fun fromStringToObject(string: String): Usuario =
            values().first { it.string == string }.classe.getDeclaredConstructor().newInstance()

        fun fromObjectToClass(objeto: Usuario): Class<out Usuario> =
            values().first { it.classe == objeto::class.java }.classe
        fun fromObjectToInt(objeto: Usuario): Int =
            values().first { it.classe == objeto::class.java }.inteiro
        fun fromObjectToString(objeto: Usuario): String =
            values().first { it.classe == objeto::class.java }.string

    }
}