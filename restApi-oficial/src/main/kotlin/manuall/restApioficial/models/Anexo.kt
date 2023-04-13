package manuall.restApioficial.models

import java.sql.Blob
import javax.persistence.*

@Entity
@Table(name = "anexo")
data class Anexo (

    @field:Id
    @field:GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_anexo")
    val idAnexo:Int,

    @Column(name = "arquivo")
    val Arquivo:Blob,

    @ManyToOne
    val fkMensagem:Chat
)