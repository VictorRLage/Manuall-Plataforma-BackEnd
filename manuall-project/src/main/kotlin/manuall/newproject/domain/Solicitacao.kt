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
    var tamanho: Int? = null

    @Column(name="unidade_medida", length = 20)
    var unidadeMedida:String? = null

    @Column(name = "descricao", length = 120)
    var descricao: String? = null

    @Column(name = "status")
    var status: Int? = null

    @ManyToOne
    var tipoServico: TipoServico = TipoServico()

}