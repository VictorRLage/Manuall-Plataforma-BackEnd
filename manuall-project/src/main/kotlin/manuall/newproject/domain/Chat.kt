package manuall.newproject.domain

import java.util.Date
import jakarta.persistence.*

@Entity
@Table(name = "chat")
class Chat {

    @field:Id
    @field:GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    var id: Int = 0

    @ManyToOne
    var contratanteUsuario: Usuario = Usuario()

    @ManyToOne
    var prestadorUsuario: Usuario = Usuario()

    @Column(name = "mensagem", length = 150)
    var mensagem: String? = null

    @Column(name = "horario")
    var horario: Date? = null
}