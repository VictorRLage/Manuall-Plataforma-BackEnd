package manuall.api.repository

import manuall.api.domain.RecuperacaoSenha
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface RecuperacaoSenhaRepository : JpaRepository<RecuperacaoSenha, Int> {
    fun findByEmailAndCodigo(email: String, codigo: String): RecuperacaoSenha?

    @Modifying
    @Query("UPDATE Usuario u SET u.senha = :novaSenha WHERE u.email = :email")
    fun alterarSenha(email: String, novaSenha: String)
}
