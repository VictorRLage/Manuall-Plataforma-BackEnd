package manuall.api.service.routine

import manuall.api.domain.CrmLog
import manuall.api.domain.CrmLogMensagem
import manuall.api.dto.routine.crm.UsuarioCrm
import manuall.api.repository.CrmLogRepository
import manuall.api.repository.UsuarioRepository
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import java.util.Calendar

@Service
class CrmRoutineService(
    val usuarioRepository: UsuarioRepository,
    val crmLogRepository: CrmLogRepository,
) {

    fun getPrestadoresOciosos(): ResponseEntity<List<UsuarioCrm>> {
        val prestadores = usuarioRepository.findPrestadoresTimes()

        return ResponseEntity.status(200).body(
            prestadores.filter {
                val cal1 = Calendar.getInstance()
                cal1.add(Calendar.DATE, -90)
                val cal2 = Calendar.getInstance()
                cal2.add(Calendar.DATE, -30)

                (it.lastSolicitacaoDate == null || it.lastSolicitacaoDate.before(cal1.time)) &&
                        (it.lastCrmDate == null || it.lastCrmDate.before(cal2.time))
            }.map {
                UsuarioCrm(it.id, it.email)
            }
        )
    }

    fun getHeavyPrestadores(): ResponseEntity<List<UsuarioCrm>> {
        return ResponseEntity.status(200).body(
            usuarioRepository.findHeavy().filter {
                val cal = Calendar.getInstance()
                cal.add(Calendar.DATE, -30)

                it.lastCrmDate == null || it.lastCrmDate.before(cal.time)
            }.map {
                UsuarioCrm(it.id, it.email)
            }
        )
    }

    fun getContratantesRecentes(): ResponseEntity<List<UsuarioCrm>> {
        return ResponseEntity.status(200).body(
            usuarioRepository.findContratantesRecentes().filter {
                val cal = Calendar.getInstance()
                cal.add(Calendar.DATE, -30)

                it.lastCrmDate == null || it.lastCrmDate.before(cal.time)
            }.map {
                UsuarioCrm(it.id, it.email)
            }
        )
    }

    fun iniciarCrm(tipo: String, usuarioId: Int): ResponseEntity<Unit> {

        val mensagem = when (tipo) {
            "ociosos" -> 21539
            "heavy" -> 10428
            "recentes" -> 20458
            else -> return ResponseEntity.status(400).build()
        }

        val newCrmLog = CrmLog()

        newCrmLog.usuario = usuarioRepository.findById(usuarioId).get()
        newCrmLog.inicioContato = Calendar.getInstance().time
        newCrmLog.processoFinalizado = false

        crmLogRepository.save(newCrmLog)

        val newCrmLogMensagem = CrmLogMensagem()

        newCrmLogMensagem.crmLog = newCrmLog
        newCrmLogMensagem.mensagem = mensagem

        return ResponseEntity.status(200).build()
    }
}