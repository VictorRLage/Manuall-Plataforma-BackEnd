package manuall.restApioficial.models

import javax.persistence.*

@Entity
@Table(name = "area")
data class Area (

    @field:Id
    @field:GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    val id:Int,

    @Column(name = "nome", length = 30)
    val nome:String
)