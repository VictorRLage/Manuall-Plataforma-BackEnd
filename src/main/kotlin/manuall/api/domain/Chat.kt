package manuall.api.domain

import java.util.Date
import jakarta.persistence.*
import jakarta.validation.constraints.NotBlank
import org.hibernate.validator.constraints.URL

@Entity
@Table(name = "chat")
class Chat {

    @field:Id
    @field:GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    var id: Int = 0

    @OneToOne
    var solicitacao: Solicitacao = Solicitacao()

    @Column(name = "id_remetente")
    var idRemetente: Int = 0

    @Column(name = "mensagem", length = 150)
    @field:NotBlank
    var mensagem: String? = null

    @Column(name = "horario")
    var horario: Date? = null

    @Column(name = "anexo", length = 500)
    var anexo: String? = ""
}