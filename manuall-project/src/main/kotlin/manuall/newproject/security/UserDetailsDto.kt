package manuall.newproject.security

import manuall.newproject.domain.Usuario
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class UserDetailsDto (
    private val usuario: Usuario
): UserDetails {

    override fun getAuthorities(): Collection<GrantedAuthority>? = null

    override fun getPassword(): String? = usuario.senha

    override fun getUsername(): String = usuario.tipoUsuario.toString()+usuario.email

    override fun isAccountNonExpired(): Boolean  = true

    override fun isAccountNonLocked(): Boolean = true

    override fun isCredentialsNonExpired(): Boolean = true

    override fun isEnabled(): Boolean = true
}