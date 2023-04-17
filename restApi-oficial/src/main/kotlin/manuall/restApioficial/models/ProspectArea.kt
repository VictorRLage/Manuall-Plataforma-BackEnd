package manuall.restApioficial.models

import javax.persistence.*

@Entity
@Table(name = "prospect_area")
data class ProspectArea (

        @field:Id
        @field:GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id")
        val id:Int,

        @ManyToOne
        val prospect: Prospect,

        @ManyToOne
        val area: Area
)