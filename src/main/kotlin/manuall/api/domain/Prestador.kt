package manuall.api.domain

import jakarta.persistence.Column
import jakarta.persistence.DiscriminatorValue
import jakarta.persistence.Entity
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToMany
import org.hibernate.validator.constraints.URL

@Entity
@DiscriminatorValue("2")
class Prestador: Usuario() {

    @Column(name = "orcamento_min")
    // @field:PositiveOrZero
    var orcamentoMin: Double? = null

    @Column(name = "orcamento_max")
    // @field:PositiveOrZero
    var orcamentoMax: Double? = null

    @Column(name = "descricao", length = 1000)
    // @field:Size(max = 270)
    var descricao: String? = null

    @Column(name = "presta_aula")
    var prestaAula: Boolean? = null

    @Column(name = "plano")
    var plano: Int? = null
    // 1: Plano Básico
    // 2: Plano Advanced
    // 3: Plano Premium

    @Column(name = "anexo_pfp", length = 500)
    @field:URL
    // @field:NotBlank
    // @field:Size(max = 90)
    // @field:URL
    var anexoPfp: String? = ""

    @Column(name = "acessos")
    var acessos: Int? = null

    @ManyToOne
    var area: Area? = null

    @OneToMany(mappedBy = "prestador")
    var usuarioImg: List<UsuarioImg> = listOf()

    @OneToMany(mappedBy = "prestador")
    var usuarioServico: List<UsuarioServico> = listOf()
}