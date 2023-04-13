package manuall.restApioficial.models

import javax.persistence.*

@Entity
@Table(name = "avaliacao")
data class Avaliacao (

    @ManyToOne
    val contratanteUsuario:Usuario,

    @ManyToOne
    val prestadorUsuario:Usuario,

    @field:Id
    @field:GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    val id:Int,

    @Column(name = "nota")
    val nota:Int,

    @Column(name = "descricao", length = 45)
    val descricao:String
)