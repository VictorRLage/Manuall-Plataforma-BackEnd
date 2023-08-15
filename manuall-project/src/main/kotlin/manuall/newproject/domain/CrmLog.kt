package manuall.newproject.domain

import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "crm_log")
class CrmLog {

    @field:Id
    @field:GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    var id: Int = 0

    @ManyToOne
    var usuario: Usuario = Usuario()

    @Column(name = "inicio_contato")
    var inicioContato: Date? = null

    @Column(name = "hist_msgs", length = 30)
    var histMsgs: String? = null

    @Column(name = "acessou_url")
    var acessouUrl: Boolean? = null

    @Column(name = "processo_finalizado")
    var processoFinalizado: Boolean? = null
}