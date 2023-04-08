package manuall.restApioficial.models

import java.util.Date
import javax.persistence.*

@Entity
@Table(name = "solicitacao")
data class Solicitacao (

    @ManyToOne
    val fkRemetente:Usuario,

    @ManyToOne
    val fkDestinatario:Usuario,

    @field:Id
    @field:GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_solicitacao")
    val idSolicitacao:Int,

    @Column(name = "dt_solicitacao")
    val dtSolicitacao:Date,

    @Column(name = "assunto", length = 15)
    val assunto:String,

    @Column(name = "mensagem", length = 45)
    val mensagem:String,

    @Column(name = "aprovado")
    val aprovado:Boolean? = null
)