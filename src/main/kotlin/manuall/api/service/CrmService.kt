package manuall.api.service

import manuall.api.domain.*
import manuall.api.dto.crm.DadosContratanteCrm
import manuall.api.dto.crm.DadosPrestadorCrm
import manuall.api.enums.Plano
import manuall.api.repository.CrmLogMensagemRepository
import manuall.api.repository.CrmLogRepository
import manuall.api.repository.ProspectRepository
import manuall.api.security.JwtTokenManager
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

@Service
class CrmService (
    val prospectRepository: ProspectRepository,
    val crmLogRepository: CrmLogRepository,
    val crmLogMensagemRepository: CrmLogMensagemRepository,
    val jwtTokenManager: JwtTokenManager
) {

    fun buscarTodos(token: String?): ResponseEntity<List<Prospect>> {

        val usuario = jwtTokenManager.validateToken(token)
            ?: return ResponseEntity.status(480).build()

        if (usuario !is Administrador) return ResponseEntity.status(480).build()

        return ResponseEntity.status(200).body(prospectRepository.findAll())
    }

    fun buscarMensagensManuel(token: String?): ResponseEntity<List<Int>> {

        val usuario = jwtTokenManager.validateToken(token)
            ?: return ResponseEntity.status(480).build()

        val contatoMaisRecente = crmLogRepository.findByUsuarioIdOrderByInicioContatoDesc(usuario.id)
            ?: return ResponseEntity.status(404).build()

        return ResponseEntity.status(200).body(
            crmLogMensagemRepository.findByCrmLogId(
                contatoMaisRecente.id
            ).map { it.mensagem!! }
        )
    }

    fun postarMensagemManuel(token: String?, crmMsgs: List<Int>): ResponseEntity<Unit> {

        val usuario = jwtTokenManager.validateToken(token)
            ?: return ResponseEntity.status(480).build()

        val crmLogAtual = crmLogRepository.findByUsuarioIdOrderByInicioContatoDesc(usuario.id)
            ?: return ResponseEntity.status(404).build()

        val msgsCrmLogAtual = crmLogMensagemRepository.findByCrmLogId(
            crmLogAtual.id
        )

        var i = 0
        while (i < crmMsgs.size) {

            if (msgsCrmLogAtual.size > i) {
                if (msgsCrmLogAtual[i].mensagem != crmMsgs[i]) {
                    msgsCrmLogAtual[i].mensagem = crmMsgs[i]
                    crmLogMensagemRepository.save(msgsCrmLogAtual[i])
                }
            } else {
                val crmLogMensagem = CrmLogMensagem()
                crmLogMensagem.mensagem = crmMsgs[i]
                crmLogMensagem.crmLog = crmLogAtual
                crmLogMensagemRepository.save(crmLogMensagem)
            }

            i++
        }

        return ResponseEntity.status(201).build()
    }

    fun buscarDadosContratante(token: String?): ResponseEntity<DadosContratanteCrm> {

        val usuario = (
                jwtTokenManager.validateToken(token)
                    ?: return ResponseEntity.status(480).build()
                ) as Contratante

        return ResponseEntity.status(200).body(
            DadosContratanteCrm(
                usuario.nome!!,
            )
        )
    }

    fun buscarDadosPrestador(token: String?): ResponseEntity<DadosPrestadorCrm> {

        val usuario = (
                jwtTokenManager.validateToken(token)
                    ?: return ResponseEntity.status(480).build()
                ) as Prestador

        return ResponseEntity.status(200).body(
            DadosPrestadorCrm(
                usuario.nome!!,
                usuario.plano!!
            )
        )
    }
}