package manuall.api.repository

import manuall.api.domain.Avaliacao
import manuall.api.dto.solicitacao.AvaliacaoDto
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface AvaliacaoRepository: JpaRepository<Avaliacao, Int> {

    @Query("""
        SELECT
        new manuall.api.dto.solicitacao.AvaliacaoDto(
        s.contratanteUsuario.nome,
        a.nota,
        a.descricao
        )
        FROM Avaliacao a
        JOIN Solicitacao s ON s.avaliacao.id = a.id
        WHERE s.prestadorUsuario.id = ?1
    """)
    fun findByPrestadorUsuarioId(prestadorId: Int): List<AvaliacaoDto>
}