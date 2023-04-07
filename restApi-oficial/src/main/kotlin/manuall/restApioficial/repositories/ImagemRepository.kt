package manuall.restApioficial.repositories

import manuall.restApioficial.models.Imagem
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ImagemRepository: JpaRepository<Imagem, Int> {
}