package manuall.api.domain

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "recuperacao_senha")
class RecuperacaoSenha {

    @field:Id
    @field:GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    var id: Int = 0

    @Column(name = "codigo", length = 6)
    var codigo: String? = null

    @Column(name = "dt_envio")
    var dtEnvio: LocalDateTime? = null

    @Column(name = "email")
    var email: String? = null
}