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

    companion object {
        fun getOrder(cb: CriteriaBuilder, path: Path<*>, crescente: Boolean): Order =
            if (crescente) cb.asc(path) else cb.desc(path)
        fun getOrder(cb: CriteriaBuilder, path: Expression<*>, crescente: Boolean): Order =
            if (crescente) cb.asc(path) else cb.desc(path)
    }

    fun filtrar(
        idArea: Int,
        filtro: String,
        crescente: Boolean
    ): List<PrestadorCardDto> {

        val cb: CriteriaBuilder = entityManager.criteriaBuilder
        val cq: CriteriaQuery<PrestadorCardDto> = cb.createQuery(PrestadorCardDto::class.java)

        val dadosEnderecoRoot: Root<DadosEndereco> = cq.from(DadosEndereco::class.java)
        val prestadorJoin: Join<DadosEndereco, Prestador> = dadosEnderecoRoot.join("usuario")
        val solicitacaoJoin: Join<Prestador, Solicitacao> = prestadorJoin.join("solicitacao", JoinType.LEFT)
        val avaliacaoJoin: Join<Solicitacao, Avaliacao> = solicitacaoJoin.join("avaliacao")

        cq.where(cb.equal(prestadorJoin.type(), Prestador::class.java))

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
            cb.avg(avaliacaoJoin.get<Double>("nota")),
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

        if (idArea > 0)
            cq.where(cb.equal(prestadorJoin.get<Int>("area").get<Int>("id"), idArea))

        val filtroOrder: Order? = when (filtro) {
            "Nota" ->
                getOrder(cb, cb.avg(avaliacaoJoin.get<Double>("nota")), crescente)
            "Alfabetica" ->
                getOrder(cb, prestadorJoin.get<String>("nome"), crescente)
            "PrecoMax" ->
                getOrder(cb, prestadorJoin.get<Double>("orcamentoMax"), crescente)
            "PrecoMin" ->
                getOrder(cb, prestadorJoin.get<Double>("orcamentoMin"), crescente)
            "Servico" ->
                cb.asc(prestadorJoin.get<Boolean>("prestaAula"))
            "ServicoAula" ->
                cb.desc(prestadorJoin.get<Boolean>("prestaAula"))
            else -> null
        }

        val planoOrder: Order =
            getOrder(cb, prestadorJoin.get<String>("plano"), crescente)

        if (filtroOrder !== null)
            cq.orderBy(filtroOrder, planoOrder)
        else
            cq.orderBy(planoOrder)

        return entityManager.createQuery(cq).resultList
    }
}