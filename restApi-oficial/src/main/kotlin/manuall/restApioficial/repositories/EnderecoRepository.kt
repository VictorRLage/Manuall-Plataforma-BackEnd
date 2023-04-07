package manuall.restApioficial.repositories

import manuall.restApioficial.models.Endereco
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface EnderecoRepository: JpaRepository<Endereco, Int> {
}