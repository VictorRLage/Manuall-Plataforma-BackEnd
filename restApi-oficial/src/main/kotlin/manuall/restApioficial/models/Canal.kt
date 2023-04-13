package manuall.restApioficial.models

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "canal")
data class Canal (

    @field:Id
    @field:GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_canal")
    val idCanal:Int,

    @Column(name = "nome", length = 45)
    val nome:String
)