package manuall.api.domain

import com.fasterxml.jackson.annotation.JsonIgnore
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

    @JsonIgnore
    @OneToMany(mappedBy = "area")
    var servico: List<Servico> = listOf()

    @JsonIgnore
    @OneToMany(mappedBy = "area")
    var prospect: List<Prospect> = listOf()

    @JsonIgnore
    @OneToMany(mappedBy = "area")
    var usuario: List<Prestador> = listOf()
}