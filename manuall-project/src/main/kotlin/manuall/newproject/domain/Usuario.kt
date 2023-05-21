package manuall.newproject.domain

import jakarta.persistence.*
import jakarta.validation.constraints.*
import org.hibernate.validator.constraints.URL
import org.hibernate.validator.constraints.br.CPF

@Entity
@Table(name = "usuario")
class Usuario {

    @field:Id
    @field:GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    var id: Int = 0

    @Column(name = "nome", length = 60)
    @field:NotBlank
    var nome: String? = null

    @Column(name = "email", length = 256)
    @field:NotBlank
    @field:Email
    var email: String? = null

    @Column(name = "senha", length = 60)
    @field:NotBlank
    @field:Size(min = 60, max = 60)
    var senha: String? = null

    @Column(name = "cpf")
    @field:CPF
    var cpf: String? = null

    @Column(name="telefone", length = 11)
    @field:NotBlank
    var telefone: String? = null

    @Column(name = "orcamento_min")
    @field:PositiveOrZero
    var orcamentoMin: Double? = null

    @Column(name = "orcamento_max")
    @field:PositiveOrZero
    var orcamentoMax: Double? = null

    @Column(name = "descricao", length = 270)
    @Size(max = 270)
    var descricao: String? = null

    @Column(name = "presta_aula")
    var prestaAula: Boolean? = null

    @Column(name = "plano")
    var plano: Int? = null
    // 1: Plano Básico
    // 2: Plano Advanced
    // 3: Plano Premium

    @Column(name = "status")
    var status: Int? = null
    // 1: Aprovação pendente
    // 2: Inscrição aprovada
    // 4: Inscrição recusada

    @Column(name = "anexo_pfp", length = 90)
    @field:URL
    var anexoPfp: String? = ""

    @Column(name = "acessos")
    var acessos: Int? = null

    @Column(name = "tipo_usuario")
    @field:NotNull
    @field:Min(1)
    @field:Max(3)
    var tipoUsuario: Int? = null
    // 1: Contratante
    // 2: Prestador
    // 3: Administrador

    @Column(name = "canal")
    var canal: Int? = null
    // 0: Website
    // 1: Redes Sociais
    // 2: Pesquisa
    // 3: Indicação
    // 4: Tela de contato (no Website)

    @ManyToOne
    var area: Area? = null
}
