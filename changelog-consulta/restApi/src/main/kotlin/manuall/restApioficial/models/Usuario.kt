package manuall.restApioficial.models

import javax.persistence.*

@Entity
@Table(name = "usuario")
data class Usuario (

    @field:Id
    @field:GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    var id:Int,

    @Column(name = "nome", length = 60)
    val nome:String,

    @Column(name = "email", length = 256)
    val email:String,

    @Column(name = "senha", length = 16)
    var senha:String,

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

    @Column(name = "descricao", length = 270)
    var descricao:String,

    @Column(name = "tipo_usuario")
    val tipoUsuario:Int,

    @Column(name = "canal")
    val canal:Int
)