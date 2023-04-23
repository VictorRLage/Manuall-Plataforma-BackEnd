package manuall.restApioficial.models

import jakarta.persistence.*

@Entity
@Table(name = "desc_servicos")
data class DescServicos (

    @field:Id
    @field:GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    val id:Int,

    @Column(name = "topico", length = 30)
    val topico:String,

    @OneToOne
    val usuario:Usuario
)