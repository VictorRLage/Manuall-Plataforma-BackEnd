package manuall.api.repository

import manuall.api.domain.Prospect
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface ProspectRepository: JpaRepository<Prospect, Int> {

    fun findByEmail(email: String):Prospect?

    fun findByEmailAndTipoUsuario(email: String, tipoUsuario: Int): Optional<Prospect>

}