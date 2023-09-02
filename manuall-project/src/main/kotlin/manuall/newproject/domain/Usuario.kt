package manuall.newproject.domain

import jakarta.persistence.*

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo_usuario", discriminatorType = DiscriminatorType.INTEGER)
@Table(name = "usuario")
abstract class Usuario {

    @field:Id
    @field:GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
//    @field:NotNull
    var id: Int = 0

    @Column(name = "nome", length = 60)
//    @field:NotBlank
//    @field:Size(max = 60)
    var nome: String? = null

    @Column(name = "email", length = 256)
//    @field:NotBlank
//    @field:Email
    var email: String? = null

    @Column(name = "senha", length = 60)
//    @field:NotBlank
//    @field:Size(min = 60, max = 60)
    var senha: String? = null

    @Column(name = "cpf", length = 11)
//    @field:CPF
    var cpf: String? = null

    @Column(name="telefone", length = 11)
//    @field:NotBlank
    var telefone: String? = null

    @Column(name = "status")
    var status: Int? = null
    // 1: Aprovação pendente
    // 2: Inscrição aprovada
    // 4: Inscrição recusada

    @Column(name = "canal")
    var canal: Int? = null
    // 0: Website
    // 1: Redes Sociais
    // 2: Pesquisa
    // 3: Indicação
    // 4: Tela de contato (no Website)
}
