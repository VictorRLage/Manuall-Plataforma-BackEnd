package manuall.newproject.domain

import jakarta.persistence.*

@Entity
@Table(name = "servico")
class Servico {

    @field:Id
    @field:GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    var id: Int = 0

    @Column(name = "nome", length = 90)
    var nome: String? = null

    @ManyToOne
    var area: Area = Area()

}