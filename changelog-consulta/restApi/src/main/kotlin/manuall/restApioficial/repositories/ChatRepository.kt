package manuall.restApioficial.repositories

import manuall.restApioficial.models.Chat
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ChatRepository: JpaRepository<Chat, Int> {
}