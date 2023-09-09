package manuall.api.domain

import jakarta.persistence.*

@Entity
@Table(name = "crm_log_mensagem")
class CrmLogMensagem {

    @field:Id
    @field:GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    var id: Int = 0

    @Column(name = "mensagem", length = 5)
    var mensagem: Int? = null

    @ManyToOne
    var crmLog: CrmLog? = null
}