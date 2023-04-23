package manuall.restApioficial.models

import jakarta.persistence.*

@Entity
@Table(name = "contato")
data class Contato (

    @field:Id
    @field:GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    val id:Int,

    @Column(name = "nome", length = 60)
    val nome:String,

    @Column(name = "email", length = 256)
    val email:String,

    @Column(name = "mensagem", length = 120)
    val mensagem:String
)