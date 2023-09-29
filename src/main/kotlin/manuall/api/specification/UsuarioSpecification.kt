package manuall.api.specification

import jakarta.persistence.criteria.JoinType
import manuall.api.domain.*
import org.springframework.data.jpa.domain.Specification


class UsuarioSpecification {

    companion object {
        fun filtrarUsuarios(
            idArea: Int,
            filtro: String,
            crescente: Boolean
        ): Specification<Solicitacao> =
            Specification<Solicitacao> { root, _, builder ->

//                val solicitacaoJoin =
//                    root.join<Solicitacao, Usuario>("prestadorUsuario")

//                val avaliacaoJoin =
//                    solicitacaoJoin.join<Solicitacao, Avaliacao>("avaliacao")
//
//                val dadosEnderecoJoin =
//                    avaliacaoJoin.join<Usuario, DadosEndereco>("dadosEndereco")

                builder.like(root.get("nome"), "Joaquim Gimenes Pires")
            }

        fun isPrestadorWithType(): Specification<Usuario> {
            return Specification { root, _, cb ->
                cb.equal(root.type(), Prestador::class.java)
            }
        }

//        fun joinWithSolicitacao(): Specification<Usuario> {
//            return Specification { root, _, _ ->
//                root.join<Usuario, Solicitacao>("solicitacao", JoinType.LEFT)
//            }
//        }
//
//        fun joinWithAvaliacao(): Specification<Usuario> {
//            return Specification { root, _, _ ->
//                root.join<Usuario, Avaliacao>("avaliacao")
//            }
//        }
//
//        fun joinWithDadosEndereco(): Specification<Usuario> {
//            return Specification { root, _, _ ->
//                root.join<Usuario, DadosEndereco>("dadosEndereco")
//            }
//        }
    }
}