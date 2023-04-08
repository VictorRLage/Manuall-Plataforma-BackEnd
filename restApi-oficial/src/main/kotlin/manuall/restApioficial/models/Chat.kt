package manuall.restApioficial.models

import java.util.Date
import javax.persistence.*

@Entity
@Table(name = "chat")
data class Chat (

    @ManyToOne
    val fkRemetente:Usuario,

    @ManyToOne
    val fkDestinatario:Usuario,

    @field:Id
    @field:GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_mensagem")
    val idMensagem:Int,

    @Column(name = "mensagem", length = 80)
    val mensagem:String,

    @Column(name = "horario")
    val horario:Date
)