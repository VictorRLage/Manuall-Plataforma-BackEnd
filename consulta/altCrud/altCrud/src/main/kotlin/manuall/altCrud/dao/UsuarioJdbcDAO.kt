package manuall.altCrud.dao

import manuall.altCrud.config.JdbcConfig
import manuall.altCrud.dto.Usuario
import org.springframework.jdbc.core.BeanPropertyRowMapper
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Repository

@Repository
class UsuarioJdbcDAO:DAO<Usuario> {

    val jdbcTemplate:JdbcTemplate = JdbcTemplate(JdbcConfig().dataSource())

    override fun create(entity: Usuario): Usuario? {
        return if (jdbcTemplate.update(
            "INSERT INTO usuario (nome, email, senha) VALUES (?, ?, ?)",
            entity.nome,
            entity.email,
            entity.senha
        ) == 1) entity
        else null
    }

    override fun read(): List<Usuario> {
        return jdbcTemplate.query(
            "SELECT * FROM usuario",
            BeanPropertyRowMapper(Usuario::class.java)
        )
    }

    override fun update(entity: Usuario, id:Int): Usuario? {
        return if (jdbcTemplate.update(
            "UPDATE usuario SET nome = ?, email = ?, senha = ? WHERE idUsuario = ?",
            entity.nome,
            entity.email,
            entity.senha,
            id
        ) == 1) entity
        else null
    }

    override fun delete(id:Int): Int? {
        return if (jdbcTemplate.update(
            "DELETE FROM usuario WHERE idUsuario = ?",
            id
        ) == 1) id
        else null
    }

    override fun findId(id:Int): Usuario? {
        return try {
            jdbcTemplate.queryForObject(
                "SELECT * from usuario where idUsuario = ?",
                BeanPropertyRowMapper(Usuario::class.java),
                id
            )
        } catch(e:Throwable) {
            null
        }
    }
}
