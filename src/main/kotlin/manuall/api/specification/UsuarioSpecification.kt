package manuall.api.specification

import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import jakarta.persistence.TypedQuery
import jakarta.persistence.criteria.*
import manuall.api.domain.*
import manuall.api.dto.usuario.PrestadorCardDto
import org.springframework.stereotype.Service

@Service
class UsuarioSpecification(
    @PersistenceContext
    private val entityManager: EntityManager
) {

    fun filtrar(): List<PrestadorCardDto> {
        val cb: CriteriaBuilder = entityManager.criteriaBuilder
        val cq: CriteriaQuery<PrestadorCardDto> = cb.createQuery(PrestadorCardDto::class.java)

        val dadosEnderecoRoot: Root<DadosEndereco> = cq.from(DadosEndereco::class.java)
        val prestadorJoin: Join<DadosEndereco, Prestador> = dadosEnderecoRoot.join("usuario")
        val solicitacaoJoin: Join<Prestador, Solicitacao> = prestadorJoin.join("solicitacao", JoinType.LEFT)
        val avaliacaoJoin: Join<Solicitacao, Avaliacao> = solicitacaoJoin.join("avaliacao")

        cq.where(cb.equal(prestadorJoin.type(), Prestador::class.java))

        val avgNota: Expression<Double> = cb.avg(avaliacaoJoin.get<Double>("nota"))

        cq.select(cb.construct(
            PrestadorCardDto::class.java,
            prestadorJoin.get<Int?>("id"),
            prestadorJoin.get<String?>("nome"),
            prestadorJoin.get<String?>("anexoPfp"),
            prestadorJoin.get<Int?>("area").get<Int>("id"),
            prestadorJoin.get<Double?>("orcamentoMin"),
            prestadorJoin.get<Double?>("orcamentoMax"),
            prestadorJoin.get<Boolean?>("prestaAula"),
            dadosEnderecoRoot.get<String?>("cidade"),
            avgNota
        ))

        cq.groupBy(
            prestadorJoin.get<Int>("id"),
            prestadorJoin.get<String>("nome"),
            prestadorJoin.get<String>("anexoPfp"),
            prestadorJoin.get<Int>("area").get<Int>("id"),
            prestadorJoin.get<Double>("orcamentoMin"),
            prestadorJoin.get<Double>("orcamentoMax"),
            prestadorJoin.get<Boolean>("prestaAula"),
            dadosEnderecoRoot.get<String>("cidade")
        )

        val query: TypedQuery<PrestadorCardDto> = entityManager.createQuery(cq)

        return query.resultList
    }
}