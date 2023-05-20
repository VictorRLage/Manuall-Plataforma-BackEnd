package manuall.newproject.domain

import jakarta.persistence.*

@Entity
@Table(name = "desc_servicos")
class DescServicos {

    @field:Id
    @field:GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    var id: Int = 0

    @OneToOne
    var usuario: Usuario = Usuario()

    @Column(name = "topico", length = 30)
    var topico: String? = null
}