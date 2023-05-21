package manuall.newproject.repository

import manuall.newproject.domain.Avaliacao
import manuall.newproject.dto.AvaliacaoDTO
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface AvaliacaoRepository: JpaRepository<Avaliacao, Int> {

    @Query("SELECT new manuall.newproject.dto.AvaliacaoDTO(a.contratanteUsuario.nome, a.contratanteUsuario.anexoPfp, a.nota, a.descricao) FROM Avaliacao a WHERE a.prestadorUsuario.id = ?1")
    fun findByPrestadorUsuarioId(prestadorId: Int): List<AvaliacaoDTO>

}