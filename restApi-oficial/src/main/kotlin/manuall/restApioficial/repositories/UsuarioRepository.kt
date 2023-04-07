package manuall.restApioficial.repositories

import manuall.restApioficial.models.Usuario
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UsuarioRepository: JpaRepository<Usuario, Int> {

    // Essas funções estão sendo criadas a partir do nome delas, tem que dar uma boa estudada pra ver
    // como fazer todas as funções do banco por nome de função, mas essas duas funções básicas que eu fiz fazem
    // o seguinte:

    // findBy: indica que esse método é responsável por buscar um ou mais registros no banco de dados de acordo com os critérios definidos após esse prefixo
    // Aprovado: é o nome da propriedade disponivel da classe Usuario
    // True: indica que o valor da propriedade disponivel deve ser true
    // OrderByNomeDesc: ordena os registros pelo nome em ordem alfabética decrescente

    fun findByAprovadoTrue(): List<Usuario>

    fun findByAprovadoTrueOrderByNomeDesc(): List<Usuario>
}