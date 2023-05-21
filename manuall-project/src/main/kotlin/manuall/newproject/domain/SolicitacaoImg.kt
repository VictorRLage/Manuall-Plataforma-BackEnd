package manuall.newproject.domain

import jakarta.persistence.*
import jakarta.validation.constraints.NotBlank
import org.hibernate.validator.constraints.URL

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
    @field:URL
    @field:NotBlank
    var anexo: String? = ""
}