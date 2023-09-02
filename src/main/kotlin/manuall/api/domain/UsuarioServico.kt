package manuall.api.domain

import jakarta.persistence.*

@Entity
@Table(name = "usuario_servico")
class UsuarioServico {

    @field:Id
    @field:GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    var id: Int = 0

    @ManyToOne
    var usuario: Prestador = Prestador()

    @ManyToOne
    var servico: Servico = Servico()
}