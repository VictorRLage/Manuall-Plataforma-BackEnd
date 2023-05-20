package manuall.newproject.domain

import jakarta.persistence.*

@Entity
@Table(name = "usuario_img")
class UsuarioImg {

    @field:Id
    @field:GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    var id: Int = 0

    @ManyToOne
    var usuario: Usuario = Usuario()

    @Column(name = "anexo", length = 90)
    var anexo: String? = ""
}