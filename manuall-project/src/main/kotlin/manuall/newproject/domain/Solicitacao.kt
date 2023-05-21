package manuall.newproject.domain

import jakarta.persistence.*

@Entity
@Table(name = "solicitacao")
class Solicitacao {

    @field:Id
    @field:GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    var id: Int = 0

    @ManyToOne
    var contratanteUsuario: Usuario = Usuario()

    @ManyToOne
    var prestadorUsuario: Usuario = Usuario()

    @Column(name = "tamanho")
    var tamanho: Double? = null

    @Column(name="medida", length = 10)
    var medida:String? = null

    @Column(name = "descricao", length = 120)
    var descricao: String? = null

    @Column(name = "status")
    var status: Int? = null
    // 1: Proposta enviada
    // 2: Proposta aprovada
    // 4: Proposta recusada

    @ManyToOne
    var servico: Servico = Servico()

    @OneToOne
    var avaliacao: Avaliacao? = null

}