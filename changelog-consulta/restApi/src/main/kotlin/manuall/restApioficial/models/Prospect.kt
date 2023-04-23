package manuall.restApioficial.models

import java.util.Date
import javax.persistence.*

@Entity
@Table(name = "prospect")
data class Prospect (

        @field:Id
        @field:GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id")
        val id:Int,

        @Column(name = "id_cliente")
        val idCliente:Int,

        @Column(name = "nome", length = 60)
        val nome:String?,

        @Column(name = "email", length = 256)
        val email:String?,

        @Column(name = "fone", length = 13)
        val fone:String?,

        @Column(name = "como_cobra", length = 45)
        val comoCobra:String?,

        @Column(name = "dt_nascimento")
        val dtNascimento:Date?,

        @Column(name = "opt_reside")
        val optReside:Int?,

        @Column(name = "opt_tamanho")
        val optTamanho:Int?,

        @Column(name = "opt_contratar")
        val optContratar:Int?,

        @Column(name = "opt_buscando")
        val optBuscando:Int?,

        @Column(name = "opt_experiencia")
        val optExperiencia:Int?,

        @Column(name = "opt_canal")
        val optCanal:Int?,

        @Column(name = "opt_interesse_loja")
        val optInteresseLoja:Int?,

        @Column(name = "opt_interesse_plat")
        val optInteressePlat:Int?,

        @Column(name = "bln_interesse_ensinar")
        val blnInteresseEnsinar:Boolean?,

        @Column(name = "bln_ja_contratou")
        val blnJaContratou:Boolean?,

        @Column(name = "bln_aprender")
        val blnAprender:Boolean?,

        @Column(name = "bln_contratou")
        val blnContratou:Boolean?,

        @Column(name = "bln_divulga")
        val blnDivulga:Boolean?,

        @Column(name = "bln_divulgara")
        val blnDivulgara:Boolean?,

        @Column(name = "msg_desistencia", length = 90)
        val msgDesistencia:String?,

        @Column(name = "status")
        val status:Int,

        @Column(name = "tipo_usuario")
        val tipoUsuario:Int
)