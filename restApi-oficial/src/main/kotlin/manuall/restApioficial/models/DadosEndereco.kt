package manuall.restApioficial.models

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table

@Entity
@Table(name = "dados_endereco")
data class DadosEndereco (

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