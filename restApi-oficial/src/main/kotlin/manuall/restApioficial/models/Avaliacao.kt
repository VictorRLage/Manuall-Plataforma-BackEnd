package manuall.restApioficial.models

import javax.persistence.*

@Entity
@Table(name = "avaliacao")
data class Avaliacao (

    @ManyToOne
    val fkContratante:Usuario,

    @ManyToOne
    val fkPrestador:Usuario,

    @field:Id
    @field:GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_avaliacao")
    val idAvaliacao:Int,

    @Column(name = "nota")
    val nota:Int,

    @Column(name = "descricao", length = 45)
    val descricao:String
)