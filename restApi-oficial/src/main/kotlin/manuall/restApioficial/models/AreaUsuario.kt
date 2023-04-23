package manuall.restApioficial.models

import jakarta.persistence.*

@Entity
@Table(name = "area_usuario")
data class AreaUsuario (

    @field:Id
    @field:GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    val id:Int,

    @ManyToOne
    val usuario:Usuario,

    @ManyToOne
    val area:Area
)