package manuall.newproject.service

import manuall.newproject.domain.CrmLog
import manuall.newproject.dto.chatbot.NovoCrmLog
import manuall.newproject.repository.CrmLogRepository
import manuall.newproject.security.JwtTokenManager
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

@Service
class ChatbotService (
    val crmLogRepository: CrmLogRepository,
    val jwtTokenManager: JwtTokenManager
) {

    fun buscarMensagensManuel(token: String): ResponseEntity<String> {
        val usuarioEncontrado = if (jwtTokenManager.validarToken(token)) {
            jwtTokenManager.getUserFromToken(token) ?: return ResponseEntity.status(480).build()
        } else {
            return ResponseEntity.status(480).build()
        }

        return ResponseEntity.status(200).body(crmLogRepository.findMsgsByUsuarioId(usuarioEncontrado.id))
    }

    fun postarMensagemManuel(token: String, novoCrmLog: NovoCrmLog): ResponseEntity<Unit> {
        val usuarioEncontrado = if (jwtTokenManager.validarToken(token)) {
            jwtTokenManager.getUserFromToken(token) ?: return ResponseEntity.status(480).build()
        } else {
            return ResponseEntity.status(480).build()
        }

        val crmLog: CrmLog = crmLogRepository.findByUsuarioId(usuarioEncontrado.id)

        crmLog.histMsgs = novoCrmLog.log
        crmLogRepository.save(crmLog)

        return ResponseEntity.status(201).build()
    }
}