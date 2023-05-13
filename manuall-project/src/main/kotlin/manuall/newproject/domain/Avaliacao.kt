package manuall.newproject.domain

import jakarta.persistence.*

@Entity
@Table(name = "avaliacao")
class Avaliacao {

    @field:Id
    @field:GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    var id: Int = 0

    @OneToOne
    var solicitacao: Solicitacao = Solicitacao()

    @OneToOne
    var solicitacaoContratanteUsuario: Solicitacao = Solicitacao()

    @OneToOne
    var solicitacaoPrestadorUsuario: Solicitacao = Solicitacao()

    @Column(name = "nota")
    var nota: Int? = null

    @Column(name = "descricao", length = 75)
    var descricao: String? = null

}