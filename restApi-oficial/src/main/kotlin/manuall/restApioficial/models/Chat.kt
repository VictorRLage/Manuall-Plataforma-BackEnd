package manuall.restApioficial.models

import java.util.Date
import jakarta.persistence.*

@Entity
@Table(name = "chat")
data class Chat (

    @field:Id
    @field:GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    val id:Int,

    @ManyToOne
    val contratanteUsuario:Usuario,

    @ManyToOne
    val prestadorUsuario:Usuario,

    @Column(name = "mensagem", length = 150)
    val mensagem:String,

    @Column(name = "horario")
    val horario:Date
)