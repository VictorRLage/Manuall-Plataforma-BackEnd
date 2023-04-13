package manuall.restApioficial.models

import java.sql.Blob
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.OneToOne
import javax.persistence.Table

@Entity
@Table(name = "imagem")
data class Imagem (

    @field:Id
    @field:GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    val id:Int,

    @Column(name = "perfil")
    val perfil:Blob,

    @Column(name = "rg_frente")
    val rgFrente:Blob,

    @Column(name = "rg_verso")
    val rgVerso:Blob,

    @OneToOne
    val usuario:Usuario
)