package manuall.newproject.domain

import jakarta.persistence.*

@Entity
@Table(name = "usuario_servico")
class UsuarioServico {

    @field:Id
    @field:GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    var id: Int = 0

    @ManyToOne
    var usuario: Usuario = Usuario()

    @ManyToOne
    var servico: Servico = Servico()
}