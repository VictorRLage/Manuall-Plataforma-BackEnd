package manuall.restApioficial.services

import manuall.restApioficial.models.Usuario
import manuall.restApioficial.repositories.UsuarioRepository
import org.springframework.stereotype.Service
import java.util.Optional

@Service
class UsuarioService (
    private val usuarioRepository: UsuarioRepository
) {

    // Essas são as funções padrões que já vem em qualquer Repository, mais abaixo temos algumas feitas do 0

    fun getAllUsuarios():List<Usuario> {
        return usuarioRepository.findAll()
    }

    fun getUsuarioById(id: Int): Optional<Usuario> {
        return usuarioRepository.findById(id)
    }

    fun createUsuario(usuario: Usuario): Usuario {
        return usuarioRepository.save(usuario)
    }

    fun updateUsuario(id: Int, usuario: Usuario): Optional<Usuario> {
        val usuarioExistente = usuarioRepository.findById(id)
        if (usuarioExistente.isPresent) {
            usuario.idUsuario = id
            return Optional.of(usuarioRepository.save(usuario))
        }
        return Optional.empty()
    }

    fun deleteUsuario(id:Int) {
        usuarioRepository.deleteById(id)
    }



    // Essas funções foram criadas do zero no UsuarioRepository

    fun getUsuariosAprovados(): List<Usuario> {
        return usuarioRepository.findByAprovadoTrue()
    }

    fun getUsuariosAprovadosOrdemAlfabetica(): List<Usuario> {
        return usuarioRepository.findByAprovadoTrueOrderByNomeDesc()
    }
}