package manuall.restApioficial.models

import javax.persistence.*

@Entity
@Table(name = "usuario")
data class Usuario (

    @field:Id
    @field:GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    var idUsuario:Int,

    @Column(name = "nome", length = 45)
    val nome:String,

    @Column(name = "email", length = 45)
    val email:String,

    @Column(name = "senha", length = 45)
    val senha:String,

    @Column(name = "cpf", length = 11)
    val cpf:String,

    @Column(name = "aprovado")
    val aprovado:Boolean = false,

    @OneToOne
    val fkPerfis:Perfis
)