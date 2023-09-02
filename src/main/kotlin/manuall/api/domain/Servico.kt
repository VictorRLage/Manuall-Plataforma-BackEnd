package manuall.api.domain

import jakarta.persistence.*

@Entity
@Table(name = "servico")
class Servico {

    @field:Id
    @field:GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    var id: Int = 0

    @ManyToOne
    var area: Area = Area()

    @Column(name = "nome", length = 90)
    var nome: String? = null

}