package manuall.newproject.repository

import manuall.newproject.domain.UsuarioImg
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UsuarioImgRepository: JpaRepository<UsuarioImg, Int> {
}