package manuall.api.repository

import manuall.api.domain.DadosEndereco
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface DadosEnderecoRepository : JpaRepository<DadosEndereco, Int> {

    fun deleteByUsuarioId(id: Int)

    fun findByUsuarioId(id: Int): Optional<DadosEndereco>
}
