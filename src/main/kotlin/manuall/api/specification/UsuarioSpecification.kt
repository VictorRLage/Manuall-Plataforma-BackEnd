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
        crescente: Boolean,
        tipo: String
    ): List<PrestadorCardDto> {

        val cb: CriteriaBuilder = entityManager.criteriaBuilder
        val cq: CriteriaQuery<PrestadorCardDto> = cb.createQuery(PrestadorCardDto::class.java)

        val dadosEnderecoRoot: Root<DadosEndereco> = cq.from(DadosEndereco::class.java)
        val prestadorJoin: Join<DadosEndereco, Prestador> = dadosEnderecoRoot.join("usuario")
        val solicitacaoJoin: Join<Prestador, Solicitacao> = prestadorJoin.join("solicitacao", JoinType.LEFT)
        val avaliacaoJoin: Join<Solicitacao, Avaliacao> = solicitacaoJoin.join("avaliacao", JoinType.LEFT)
        
        val id = prestadorJoin.get<Int>("id")
        val nome = prestadorJoin.get<String>("nome")
        val anexoPfp = prestadorJoin.get<String>("anexoPfp")
        val area = prestadorJoin.get<Int>("area").get<Int>("id")
        val orcamentoMin = prestadorJoin.get<Double>("orcamentoMin")
        val orcamentoMax = prestadorJoin.get<Double>("orcamentoMax")
        val prestaAula = prestadorJoin.get<Boolean>("prestaAula")
        val cidade = dadosEnderecoRoot.get<String>("cidade")

        val nota: Expression<Double> = cb.avg(avaliacaoJoin.get<Double>("nota"))
        val notaCoalesce: CriteriaBuilder.Coalesce<Double> = cb.coalesce()
        notaCoalesce.value(nota).value(0.0)

        val parametros = mutableListOf<Predicate>()

        parametros.add(cb.equal(prestadorJoin.type(), Prestador::class.java))
        parametros.add(cb.isNotNull(prestadorJoin.get<Int>("plano")))
        if (idArea > 0)
            parametros.add(cb.equal(area, idArea))
        when (tipo) {
            "apenasServico" ->
                parametros.add(cb.equal(prestaAula, false))
            "servicoAula" ->
                parametros.add(cb.equal(prestaAula, true))
        }

        cq.where(*parametros.toTypedArray())

        cq.select(cb.construct(
            PrestadorCardDto::class.java,
            id,
            nome,
            anexoPfp,
            area,
            orcamentoMin,
            orcamentoMax,
            prestaAula,
            cidade,
            notaCoalesce,
        ))

        cq.groupBy(
            id,
            nome,
            anexoPfp,
            area,
            orcamentoMin,
            orcamentoMax,
            prestaAula,
            cidade
        )

        val filtroOrder: Order? = when (filtro) {
            "Nota" ->
                getOrder(cb, notaCoalesce, crescente)
            "Alfabetica" ->
                getOrder(cb, nome, crescente)
            "Preco" ->
                getOrder(cb, orcamentoMax, crescente)
            else -> null
        }

        val planoOrder: Order =
            getOrder(cb, prestadorJoin.get<Int>("plano"), crescente)

        if (filtroOrder !== null)
            cq.orderBy(filtroOrder, planoOrder)
        else
            cq.orderBy(planoOrder)

        return entityManager.createQuery(cq).resultList
    }
}