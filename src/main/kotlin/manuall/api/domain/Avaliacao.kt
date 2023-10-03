package manuall.api.domain

import jakarta.persistence.*

@Entity
@Table(name = "avaliacao")
class Avaliacao {

    @field:Id
    @field:GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    var id: Int = 0

    @Column(name = "nota")
    var nota: Int? = null

    @Column(name = "descricao", length = 75)
    var descricao: String? = null

    @OneToOne(mappedBy = "avaliacao")
    var solicitacao: Solicitacao = Solicitacao()
}