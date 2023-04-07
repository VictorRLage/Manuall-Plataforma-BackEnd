package manuall.restApioficial.models

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.ManyToOne
import javax.persistence.Table

@Entity
@Table(name = "area_usuario")
data class AreaUsuario (

    @ManyToOne
    @Column(name = "fk_usuario")
    val fkUsuario:Usuario,

    @ManyToOne
    @Column(name = "fk_area")
    val fkArea:Area
)