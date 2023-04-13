package manuall.restApioficial.models

import java.sql.Date
import javax.persistence.*

@Entity
@Table(name = "dados_bancarios")
data class DadosBancarios (

    @field:Id
    @field:GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    val id:Int,

    @Column(name = "plano")
    val plano:Int,

    @Column(name = "nome", length = 60)
    val nome:String,

    @Column(name = "numero", length = 16)
    val numero:Int,

    @Column(name = "validade")
    val validade: Date,

    @Column(name = "cvv", length = 3)
    val cvv:String,

    @OneToOne
    val usuario:Usuario
)