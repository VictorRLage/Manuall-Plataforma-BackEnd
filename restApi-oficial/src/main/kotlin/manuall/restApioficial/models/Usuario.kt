package manuall.restApioficial.models

import javax.persistence.*

@Entity
@Table(name = "usuario")
data class Usuario (

    @field:Id
    @field:GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    var id:Int,

    @Column(name = "nome", length = 45)
    val nome:String,

    @Column(name = "email", length = 45)
    val email:String,

    @Column(name = "senha", length = 45)
    val senha:String,

    @Column(name = "cpf", length = 11)
    val cpf:String,

    @Column(name = "orcamento_min")
    val orcamentoMin:Double,

    @Column(name = "orcamento_max")
    val orcamentoMax:Double,

    @Column(name = "status")
    val status:Int,

    @Column(name = "acessos")
    val acessos:Int,

    @Column(name = "tipo_usuario")
    val tipoUsuario:Int,

    @OneToOne
    val canal:Canal
)