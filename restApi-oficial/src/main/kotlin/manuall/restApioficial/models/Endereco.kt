package manuall.restApioficial.models

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.ManyToOne
import javax.persistence.Table

@Entity
@Table(name = "endereco")
data class Endereco (

    @field:Id
    @field:GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    val id:Int,

    @Column(name = "estado", length = 25)
    val estado:String,

    @Column(name = "cidade", length = 35)
    val cidade:String,

    @Column(name = "cep", length = 8)
    val cep:String,

    @Column(name = "bairro", length = 35)
    val bairro:String,

    @Column(name = "rua", length = 45)
    val rua:String,

    @Column(name = "numero")
    val numero:Int,

    @Column(name = "complemento", length = 25)
    val complemento:String,

    @ManyToOne
    val usuario:Usuario
)