package manuall.api.domain

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
    open var id: Int = 0

    @Column(name = "nome", length = 60)
//    @field:NotBlank
//    @field:Size(max = 60)
    open var nome: String? = null

    @Column(name = "email", length = 256)
//    @field:NotBlank
//    @field:Email
    open var email: String? = null

    @Column(name = "senha", length = 60)
//    @field:NotBlank
//    @field:Size(min = 60, max = 60)
    open var senha: String? = null

    @Column(name = "cpf", length = 11)
//    @field:CPF
    open var cpf: String? = null

    @Column(name="telefone", length = 11)
//    @field:NotBlank
    open var telefone: String? = null

    @Column(name = "status")
    open var status: Int? = null
    // 1: Aprovação pendente
    // 2: Inscrição aprovada
    // 4: Inscrição recusada

    @Column(name = "canal")
    open var canal: Int? = null
    // 0: Website
    // 1: Redes Sociais
    // 2: Pesquisa
    // 3: Indicação
    // 4: Tela de contato (no Website)
}
