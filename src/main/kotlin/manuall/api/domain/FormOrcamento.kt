package manuall.api.domain

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*

@Entity
@Table(name = "form_orcamento")
class FormOrcamento {

    @field:Id
    @field:GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    var id: Int = 0

    @Column(name = "mensagem", length = 135)
    var mensagem: String? = null

    @Column(name = "orcamento")
    var orcamento: Double? = 0.0

    @JsonIgnore
    @OneToOne(mappedBy = "formOrcamento")
    var solicitacao: Solicitacao = Solicitacao()
}