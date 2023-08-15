package manuall.newproject.domain

import jakarta.persistence.*

@Entity
@Table(name = "prospect")
class Prospect {

        @field:Id
        @field:GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id")
        var id: Int = 0

        @Column(name = "id_cliente")
        var idCliente: Int = 0

        @Column(name = "nome", length = 60)
        var nome: String? = null

        @Column(name = "email", length = 256)
        var email: String? = null

        @Column(name = "fone", length = 13)
        var fone: String? = null

        @Column(name = "opt_canal")
        var optCanal: Int? = null

        @Column(name = "opt_cidade")
        var optCidade:Int? = null

        @Column(name = "bln_conhece_manuall")
        var blnConheceManuall: Boolean? = null

        @Column(name = "bln_aprender")
        var blnAprender: Boolean? = null

        @Column(name = "bln_interesse_manuall")
        var blnInteresseManuall: Boolean? = null

        @Column(name = "bln_interesse_ensinar")
        var blnInteresseEnsinar: Boolean? = null

        @Column(name = "bln_cupom")
        var blnCupom: Boolean? = null

        @Column(name = "msg_desistencia", length = 90)
        var msgDesistencia: String? = null

        @Column(name = "status")
        var status: Int? = null

        @Column(name = "tipo_usuario")
        var tipoUsuario: Int? = null

        @ManyToOne
        var area: Area? = null
}