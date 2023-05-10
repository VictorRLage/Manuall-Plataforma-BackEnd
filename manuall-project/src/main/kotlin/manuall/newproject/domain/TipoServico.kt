package manuall.newproject.domain

import jakarta.persistence.*

@Entity
@Table(name = "tipo_servico")
class TipoServico {

    @field:Id
    @field:GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    var id:Int = 0

    @Column(name = "nome", length = 80)
    var nome:String? = null

    @ManyToOne
    var area:Area = Area()



}