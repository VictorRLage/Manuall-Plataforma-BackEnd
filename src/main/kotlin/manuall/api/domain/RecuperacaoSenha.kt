package manuall.api.domain

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*

@Entity
@Table(name = "recuperacao_senha")
class RecuperacaoSenha {

    @field:Id
    @field:GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    var id: Int = 0

    @Column(name = "email")
    var email: String? = null

    @Column(name = "codigo", length = 7)
    var codigo: String? = null

    @Column(name = "dtEnvio")
    var dtEnvio: String? = null
}