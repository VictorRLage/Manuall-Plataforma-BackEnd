package manuall.api.domain

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
    var usuario: Usuario? = null

    @Column(name = "inicio_contato")
    var inicioContato: Date? = null

    @Column(name = "acessou_url")
    var acessouUrl: Boolean? = null

    @Column(name = "processo_finalizado")
    var processoFinalizado: Boolean? = null

    @OneToMany(mappedBy = "crmLog")
    var crmLogMensagem: List<CrmLogMensagem> = listOf()
}