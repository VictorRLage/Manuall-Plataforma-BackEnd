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

    @OneToMany
    var usuarioServico: List<UsuarioServico> = listOf()

    @OneToMany
    var solicitacao: List<Solicitacao> = listOf()

}