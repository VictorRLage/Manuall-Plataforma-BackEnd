package manuall.newproject.domain

import jakarta.persistence.*

@Entity
@Table(name = "token_blacklist")
class TokenBlacklist {

    @field:Id
    @field:GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    var id: Int = 0

    @Column(name = "token", length = 512)
    var token: String? = null
}