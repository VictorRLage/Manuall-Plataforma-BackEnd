package manuall.restApioficial.models

import java.util.Date
import javax.persistence.*

@Entity
@Table(name = "chat")
data class Chat (

    @ManyToOne
    val contratanteUsuario:Usuario,

    @ManyToOne
    val prestadorUsuario:Usuario,

    @field:Id
    @field:GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    val id:Int,

    @Column(name = "mensagem", length = 150)
    val mensagem:String,

    @Column(name = "horario")
    val horario:Date
)