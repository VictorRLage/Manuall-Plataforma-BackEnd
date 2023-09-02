package manuall.api.security

import manuall.api.domain.Usuario
import manuall.api.enums.TipoUsuario
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class UserDetailsDto (
    private val usuario: Usuario
): UserDetails {

    override fun getAuthorities(): Collection<GrantedAuthority>? = null

    override fun getPassword(): String? = usuario.senha

    override fun getUsername(): String {
        return "${TipoUsuario.fromObjectToString(usuario)}${usuario.email}"
    }

    override fun isAccountNonExpired(): Boolean  = true

    override fun isAccountNonLocked(): Boolean = true

    override fun isCredentialsNonExpired(): Boolean = true

    override fun isEnabled(): Boolean = true
}