package manuall.api.domain

import jakarta.persistence.*

@Entity
@Table(name = "area")
class Area {

    @field:Id
    @field:GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    var id: Int = 0

    @Column(name = "nome", length = 30)
    var nome: String? = null

    @OneToMany(mappedBy = "area")
    var servico: List<Servico> = listOf()

    @OneToMany(mappedBy = "area")
    var prospect: List<Prospect> = listOf()

    @OneToMany(mappedBy = "area")
    var usuario: List<Prestador> = listOf()
}