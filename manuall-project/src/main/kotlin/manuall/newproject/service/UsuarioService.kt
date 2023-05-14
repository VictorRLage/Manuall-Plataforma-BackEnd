package manuall.newproject.service

import manuall.newproject.domain.DadosEndereco
import manuall.newproject.domain.Usuario
import manuall.newproject.dto.*
import manuall.newproject.repository.*
import manuall.newproject.security.JwtTokenManager
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.util.*

@Service
class UsuarioService (
    val passwordEncoder: PasswordEncoder,
    val jwtTokenManager: JwtTokenManager,
    val authenticationManager: AuthenticationManager,
    val usuarioRepository: UsuarioRepository,
    val dadosEnderecoRepository: DadosEnderecoRepository,
    val areaUsuarioRepository: AreaUsuarioRepository,
    val descServicosRepository: DescServicosRepository,
    val prospectRepository: ProspectRepository
) {

    fun login(usuarioLoginRequest: UsuarioLoginRequest): ResponseEntity<Any> {

        // Pegando os usuários com o email requisitado em uma lista, já que podem
        // existir 2 usuários com o mesmo email e tipo_usuario diferentes
        val possiveisUsuarios = usuarioRepository.findByEmail(usuarioLoginRequest.email)

        return if (possiveisUsuarios.isEmpty()) {
            ResponseEntity.status(204).build()
        } else if (possiveisUsuarios.size > 1) {
            ResponseEntity.status(409).body("Dois valores encontrados")
        } else {

            // Tendo em vista que existe apenas 1 usuário nesta lista, vamos simplificar e chamá-lo de "usuario"
            val usuario = possiveisUsuarios[0].get()

            // Autenticando sua senha, essa função decripta a senha do banco e a compara com a recebida
            if (passwordEncoder.matches(usuarioLoginRequest.senha, usuario.senha)) {

                if (usuario.status!! == 4) {
                    ResponseEntity.status(403).body("Aprovação negada")
                } else if (usuario.status == 1) {
                    ResponseEntity.status(403).body("Aprovação pendente")
                }

                // Gerando token de sessão com base no tipo_usuario e email, para identificar unicamente cada login
                val authentication = authenticationManager.authenticate(
                    UsernamePasswordAuthenticationToken(
                        usuario.tipoUsuario.toString()+usuario.email,
                        usuarioLoginRequest.senha
                    )
                )

                SecurityContextHolder.getContext().authentication = authentication
                ResponseEntity.status(200).body(UsuarioLoginResponse(
                    usuario.tipoUsuario!!,
                    jwtTokenManager.generateToken(authentication)
                ))

            } else {
                ResponseEntity.status(401).body("Senha incorreta")
            }

        }

    }

    fun logoff(token: String): ResponseEntity<Void> {
        jwtTokenManager.expirarToken(token)
        return ResponseEntity.status(200).build()
    }

    fun criar(cadastroRequest: CadastroRequest): ResponseEntity<Void> {

        val usuarioCheck: Optional<Usuario> = usuarioRepository.findByEmailAndTipoUsuario(cadastroRequest.usuario.email, cadastroRequest.usuario.tipoUsuario)

        return if (usuarioCheck.isEmpty) {

            // Codificando a senha do usuário para enviar a senha encriptada ao banco
            cadastroRequest.usuario.senha = passwordEncoder.encode(cadastroRequest.usuario.senha.toString())

            // Inserindo usuário e pegando seu id para usar nos próximos inserts
            val usuarioAtual: Int = usuarioRepository.save(cadastroRequest.usuario).id

            // Inserindo vários dados na tabela area_usuario
            cadastroRequest.areaUsuario.forEach {
                it.usuario.id = usuarioAtual
                areaUsuarioRepository.save(it)
            }

            // Inserindo dados na tabela dados_endereco
            cadastroRequest.dadosEndereco.usuario.id = usuarioAtual
            dadosEnderecoRepository.save(cadastroRequest.dadosEndereco)

            // Inserindo vários dados na tabela desc_servicos
            cadastroRequest.descServicos.forEach {
                it.usuario.id = usuarioAtual
                descServicosRepository.save(it)
            }

            ResponseEntity.status(201).build()
        } else {
            ResponseEntity.status(409).build()
        }

    }

    fun atualizarSenha(token: String, alterSenhaRequest: AlterSenhaRequest): ResponseEntity<Usuario> {

        // Checando se token foi expirado e então encontrando usuário por token
        val usuarioEncontrado = if (jwtTokenManager.validarToken(token)) {
            jwtTokenManager.getUserFromToken(token)
        } else {
            return ResponseEntity.status(401).build()
        }

        return if (alterSenhaRequest.senhaAntiga == usuarioEncontrado.senha) {

            usuarioEncontrado.senha = alterSenhaRequest.senhaNova
            ResponseEntity.status(200).body(usuarioRepository.save(usuarioEncontrado))

        } else {
            ResponseEntity.status(401).build()
        }

    }

    fun atualizarDesc(token: String, alterDescRequest: AlterDescRequest): ResponseEntity<Usuario> {

        // Checando se token foi expirado e então encontrando usuário por token
        val usuarioEncontrado = if (jwtTokenManager.validarToken(token)) {
            jwtTokenManager.getUserFromToken(token)
        } else {
            return ResponseEntity.status(401).build()
        }

        usuarioEncontrado.descricao = alterDescRequest.descricao
        return ResponseEntity.status(200).body(usuarioRepository.save(usuarioEncontrado))

    }

    fun deletar(token: String): ResponseEntity<String> {

        // Checando se token foi expirado e então encontrando usuário por token
        val usuarioEncontrado = if (jwtTokenManager.validarToken(token)) {
            jwtTokenManager.getUserFromToken(token)
        } else {
            return ResponseEntity.status(401).body("Token expirado")
        }

        descServicosRepository.deleteByUsuarioId(usuarioEncontrado.id)
        areaUsuarioRepository.deleteByUsuarioId(usuarioEncontrado.id)
        dadosEnderecoRepository.deleteByUsuarioId(usuarioEncontrado.id)
        usuarioRepository.deleteById(usuarioEncontrado.id)
        return ResponseEntity.status(200).body(null)

    }

    fun cadastrar1(cadastrar1DTO: Cadastrar1DTO): ResponseEntity<Int> {
        val emailExistente = usuarioRepository.findByEmailAndTipoUsuario(cadastrar1DTO.email, cadastrar1DTO.tipoUsuario)
        if (emailExistente.isPresent) {
            return ResponseEntity.status(409).body(null)
        } else {
            val usuarioVisitante = prospectRepository.findByEmail(cadastrar1DTO.email)
            var canal: Int? = 0
            if (usuarioVisitante != null) {
                canal = usuarioVisitante.optCanal
            }
            val usuario = Usuario()
            usuario.nome = cadastrar1DTO.nome
            usuario.email = cadastrar1DTO.email
            usuario.cpf = cadastrar1DTO.cpf
            usuario.senha = passwordEncoder.encode(cadastrar1DTO.senha)
            usuario.tipoUsuario = cadastrar1DTO.tipoUsuario
            usuario.canal = canal

            val usuarioAtual = usuarioRepository.save(usuario).id

            return ResponseEntity.status(201).body(usuarioAtual)
        }
    }

    fun cadastrar2Cont(id:Int, cadastrar2ContDTO: Cadastrar2ContDTO): ResponseEntity<String> {
        val usuario = usuarioRepository.findById(id)
        if (usuario.isEmpty) {
            return ResponseEntity.status(404).body("Usuário não encontrado!")
        }
        val enderecoCadastrado = dadosEnderecoRepository.findByUsuarioId(id)
        if (!enderecoCadastrado.isEmpty()) {
            return ResponseEntity.status(409).body("Endereço já cadastrado!")
        } else {
            val dadosEndereco = DadosEndereco()
            dadosEndereco.usuario = usuario.get() // definição de fk da dadosEndereco
            dadosEndereco.cep = cadastrar2ContDTO.cep
            dadosEndereco.cidade = cadastrar2ContDTO.cidade
            dadosEndereco.estado = cadastrar2ContDTO.estado
            dadosEndereco.bairro = cadastrar2ContDTO.bairro
            dadosEndereco.rua = cadastrar2ContDTO.rua
            dadosEndereco.numero = cadastrar2ContDTO.numero
            dadosEndereco.complemento = cadastrar2ContDTO.complemento

            dadosEnderecoRepository.save(dadosEndereco)
            return ResponseEntity.status(201).body("Endereço cadastrado com sucesso!")
        }
    }
}