package manuall.restApioficial.models

import javax.persistence.*

@Entity
@Table(name = "solicitacao")
data class Solicitacao (

        @ManyToOne
        val contratanteUsuario:Usuario,

        @ManyToOne
        val prestadorUsuario:Usuario,

        @field:Id
        @field:GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id")
        val id:Int,

        @Column(name = "assunto", length = 30)
        val assunto:String,

        @Column(name = "mensagem", length = 80)
        val mensagem:String,

        @Column(name = "status")
        val status:Int,

        @Column(name = "tipo_servico")
        val tipoServico:Int
)