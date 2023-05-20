package manuall.newproject.domain

import jakarta.persistence.*

@Entity
class FormOrcamento {

    @field:Id
    @field:GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    var id: Int = 0

    @OneToOne
    var usuario: Usuario = Usuario()

    @Column(name = "mensagem", length = 135)
    var mensagem: String? = null

    @Column(name = "orcamento")
    var orcamento: Double? = 0.0
}