package manuall.newproject.domain

import jakarta.persistence.*

@Entity
@Table(name = "solicitacao_img")
class SolicitacaoImg {

    @field:Id
    @field:GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    var id: Int = 0

    @ManyToOne
    var solicitacao: Solicitacao = Solicitacao()

    @Column(name = "anexo", length = 90)
    var anexo: String? = ""
}