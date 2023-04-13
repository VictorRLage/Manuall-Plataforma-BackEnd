package manuall.restApioficial.models

import java.sql.Blob
import javax.persistence.*

@Entity
@Table(name = "anexo")
data class Anexo (

    @field:Id
    @field:GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    val id:Int,

    @Column(name = "arquivo")
    val arquivo:Blob,

    @ManyToOne
    val chat:Chat
)