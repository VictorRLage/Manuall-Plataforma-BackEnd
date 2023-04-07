package manuall.restApioficial.models

import javax.persistence.*

@Entity
@Table(name = "perfis")
data class Perfis (

    @field:Id
    @field:GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_perfis")
    val idPerfis:Int,

    @Column(name = "nome", length = 20)
    val nome:String
)