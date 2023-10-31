package manuall.api.domain

import java.util.Date
import jakarta.persistence.*
import jakarta.validation.constraints.NotBlank

@Entity
@Table(name = "mensagem")
class Mensagem {

    @field:Id
    @field:GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    var id: Int = 0

    @ManyToOne
    var solicitacao: Solicitacao = Solicitacao()

    @Column(name = "id_remetente")
    var idRemetente: Int = 0

    @Column(name = "texto", length = 150)
    @field:NotBlank
    var texto: String? = null

    @Column(name = "horario")
    var horario: Date? = null

    @Column(name = "anexo", length = 500)
    var anexo: String? = ""

    @Column(name = "visto")
    var visto: Boolean = false
}