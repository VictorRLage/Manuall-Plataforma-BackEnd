package manuall.restApioficial.models

import javax.persistence.*

@Entity
@Table(name = "avaliacao")
data class Avaliacao (

    @field:Id
    @field:GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    val id:Int,

    @ManyToOne
    val contratanteUsuario:Usuario,

    @ManyToOne
    val prestadorUsuario:Usuario,

    @Column(name = "nota")
    val nota:Int,

    @Column(name = "descricao", length = 75)
    val descricao:String
)