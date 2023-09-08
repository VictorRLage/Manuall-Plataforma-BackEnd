package manuall.api.service

import manuall.api.domain.Administrador
import manuall.api.domain.CrmLog
import manuall.api.dto.crm.DadosContratanteCrm
import manuall.api.dto.crm.NovoCrmLog
import manuall.api.repository.CrmLogRepository
import manuall.api.repository.ProspectRepository
import manuall.api.security.JwtTokenManager
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

@Service
class CrmService (
    val prospectRepository: ProspectRepository,
    val crmLogRepository: CrmLogRepository,
    val jwtTokenManager: JwtTokenManager
) {

    fun buscarTodos(token: String?): ResponseEntity<List<Any>> {

        val usuario = jwtTokenManager.validateToken(token)
            ?: return ResponseEntity.status(480).build()

        if (usuario !is Administrador) return ResponseEntity.status(480).build()

        return ResponseEntity.status(200).body(prospectRepository.findAll())
    }

    fun buscarMensagensManuel(token: String?): ResponseEntity<String> {

        val usuario = jwtTokenManager.validateToken(token)
            ?: return ResponseEntity.status(480).build()

        return ResponseEntity.status(200).body(crmLogRepository.findMsgsByUsuarioId(usuario.id))
    }

    fun buscarDadosCliente(token: String?): ResponseEntity<DadosContratanteCrm> {

        if (jwtTokenManager.validateToken(token) == null)
            return ResponseEntity.status(480).build()

        return ResponseEntity.status(200).build()
    }

    fun postarMensagemManuel(token: String?, novoCrmLog: NovoCrmLog): ResponseEntity<Unit> {

        val usuario = jwtTokenManager.validateToken(token)
            ?: return ResponseEntity.status(480).build()

        val crmLog: CrmLog = crmLogRepository.findByUsuarioId(usuario.id)

        crmLog.histMsgs = novoCrmLog.log
        crmLogRepository.save(crmLog)

        return ResponseEntity.status(201).build()
    }
}