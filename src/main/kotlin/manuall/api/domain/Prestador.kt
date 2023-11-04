package manuall.api.domain

import com.fasterxml.jackson.annotation.JsonIgnore
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
    // 1: Plano Basic
    // 2: Plano Advanced
    // 3: Plano Premium

    @Column(name = "anexo_pfp", length = 500)
    // @field:NotBlank
    // @field:Size(max = 90)
    var anexoPfp: String? = ""

    @Column(name = "acessos")
    var acessos: Int? = null

    @Column(name = "status_processo_aprovacao")
    var statusProcessoAprovacao: Int? = null
    // 1: Pendente
    // 2: Agendado
    // 3: Finalizado

    @ManyToOne
    var area: Area? = null

    @JsonIgnore
    @OneToMany(mappedBy = "prestador")
    var usuarioImg: List<UsuarioImg> = listOf()

    @JsonIgnore
    @OneToMany(mappedBy = "prestador")
    var usuarioServico: List<UsuarioServico> = listOf()
}