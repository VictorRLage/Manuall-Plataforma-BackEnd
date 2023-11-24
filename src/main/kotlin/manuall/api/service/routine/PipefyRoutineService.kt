package manuall.api.service.routine

import manuall.api.domain.Prospect
import manuall.api.dto.routine.pipefy.MapeamentoProspect
import manuall.api.repository.AreaRepository
import manuall.api.repository.ProspectRepository
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import java.util.*

@Service
class PipefyRoutineService(
    val prospectRepository: ProspectRepository,
    val areaRepository: AreaRepository,
) {

    fun postarProspects(mapeamentoProspect: List<MapeamentoProspect>): ResponseEntity<Unit> {

        val prospects = prospectRepository.findAll()

        mapeamentoProspect.forEach {
            var prospect = prospects.find { prospect -> prospect.idCliente == it.idCliente }
            if (prospect == null) {
                prospect = Prospect()
                prospect.dataTornouLead = Date()
            } else {
                if (prospect.status == it.status) return@forEach
            }
            prospect.status = it.status
            prospect.tipoUsuario = it.tipoUsuario
            prospect.optCanal = it.optCanal
            prospect.nome = it.nome
            prospect.email = it.email
            prospect.fone = it.fone
            prospect.optCidade = it.optCidade
            prospect.blnConheceManuall = it.blnConheceManuall
            prospect.area = it.areaId?.let { id -> areaRepository.findById(id).get() }
            prospect.blnAprender = it.blnAprender
            prospect.blnInteresseManuall = it.blnInteresseManuall
            prospect.blnInteresseEnsinar = it.blnInteresseEnsinar
            prospect.blnCupom = it.blnCupom
            prospect.msgDesistencia = it.msgDesistencia
            prospectRepository.save(prospect)
        }

        return ResponseEntity.status(200).build()
    }
}