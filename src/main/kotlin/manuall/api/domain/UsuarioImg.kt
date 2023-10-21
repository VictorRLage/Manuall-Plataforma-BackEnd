package manuall.api.domain

import jakarta.persistence.*
import jakarta.validation.constraints.NotBlank
import org.hibernate.validator.constraints.URL

@Entity
@Table(name = "usuario_img")
class UsuarioImg {

    @field:Id
    @field:GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    var id: Int = 0

    @ManyToOne
    var prestador: Prestador = Prestador()

    @Column(name = "anexo", length = 150)
    @field:NotBlank
    var anexo: String? = ""
}