package manuall.api.domain

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "solicitacao")
class Solicitacao {

    @field:Id
    @field:GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    var id: Int = 0

    @ManyToOne
    var contratante: Contratante = Contratante()

    @ManyToOne
    var prestador: Prestador = Prestador()

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

    @Column(name = "inclui_aula")
    var incluiAula: Boolean? = null

    @Column(name = "data_inicio")
    var dataInicio: Date? = null

    @Column(name = "data_fim")
    var dataFim: Date? = null

    @ManyToOne
    var servico: Servico = Servico()

    @OneToOne
    var avaliacao: Avaliacao? = null

    @OneToOne
    var formOrcamento: FormOrcamento? = null

    @JsonIgnore
    @OneToMany(mappedBy = "solicitacao")
    var solicitacaoImg: List<SolicitacaoImg> = listOf()

    @JsonIgnore
    @OneToMany(mappedBy = "solicitacao")
    var mensagem: List<Mensagem> = listOf()
}