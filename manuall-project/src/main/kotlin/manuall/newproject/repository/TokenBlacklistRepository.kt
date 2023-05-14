package manuall.newproject.repository

import manuall.newproject.domain.TokenBlacklist
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface TokenBlacklistRepository: JpaRepository<TokenBlacklist, Int> {

    fun findByToken(token: String?): Optional<TokenBlacklist>
}