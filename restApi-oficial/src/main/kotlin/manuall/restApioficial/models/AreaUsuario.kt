package manuall.restApioficial.models

import javax.persistence.*

@Entity
@Table(name = "area_usuario")
data class AreaUsuario (

    @field:Id
    @field:GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_area_usuario")
    val idAreaUsuario:Int,

    @ManyToOne
    val fkUsuario:Usuario,

    @ManyToOne
    val fkArea:Area
)