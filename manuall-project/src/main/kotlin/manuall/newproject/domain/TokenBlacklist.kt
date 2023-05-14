package manuall.newproject.domain

import jakarta.persistence.*

@Entity
@Table(name = "token_blacklist")
class TokenBlacklist {

    @field:Id
    @field:GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    var id: Int = 0

    @Column(name = "token")
    var token: String? = null
}