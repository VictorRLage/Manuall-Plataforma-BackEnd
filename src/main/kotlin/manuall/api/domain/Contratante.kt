package manuall.api.domain

import jakarta.persistence.DiscriminatorValue
import jakarta.persistence.Entity
import jakarta.persistence.OneToMany

@Entity
@DiscriminatorValue("1")
class Contratante: Usuario()