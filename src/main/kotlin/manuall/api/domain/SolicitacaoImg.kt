package manuall.api.domain

import jakarta.persistence.*
import jakarta.validation.constraints.NotBlank

@Entity
@Table(name = "solicitacao_img")
class SolicitacaoImg {

    @field:Id
    @field:GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    var id: Int = 0

    @ManyToOne
    var solicitacao: Solicitacao = Solicitacao()

    @Column(name = "anexo", length = 500)
    @field:NotBlank
    var anexo: String? = ""
}