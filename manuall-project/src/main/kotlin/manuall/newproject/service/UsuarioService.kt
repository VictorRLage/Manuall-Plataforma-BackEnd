package manuall.newproject.service

import manuall.newproject.domain.*
import manuall.newproject.dto.*
import manuall.newproject.repository.*
import manuall.newproject.security.JwtTokenManager
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody

@Service
class UsuarioService (
    val passwordEncoder: PasswordEncoder,
    val jwtTokenManager: JwtTokenManager,
    val authenticationManager: AuthenticationManager,
    val usuarioRepository: UsuarioRepository,
    val dadosEnderecoRepository: DadosEnderecoRepository,
    val descServicosRepository: DescServicosRepository,
    val prospectRepository: ProspectRepository,
    val areaRepository: AreaRepository,
    val servicoRepository: ServicoRepository
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
            usuario.acessos = 0
            usuario.status = if (cadastrar1DTO.tipoUsuario == 2) 1 else 2

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

    fun cadastrar2Prest(id:Int, cadastrar2PrestDTO:Cadastrar2PrestDTO):ResponseEntity<String> {
        val usuario = usuarioRepository.findById(id)
        if (usuario.isEmpty) {
            return ResponseEntity.status(404).body("Usuário não encontrado!")
        }

        val novoUsuario = usuario.get()
        novoUsuario.orcamentoMin = cadastrar2PrestDTO.orcamentoMin
        novoUsuario.orcamentoMax = cadastrar2PrestDTO.orcamentoMax
        novoUsuario.area = areaRepository.findById(cadastrar2PrestDTO.area).get()

        usuarioRepository.save(novoUsuario)
        return ResponseEntity.status(201).body("Dados profissionais cadastrados com sucesso!")
    }

    fun buscarArea():List<Area> {
        var areas = areaRepository.findAll()
        return areas
    }

    fun buscarTiposServico(@PathVariable id:Int): List<Servico> {
        var servico = servicoRepository.findAllByAreaId(id)
        return servico
    }

    fun cadastrar3Prest(@PathVariable id:Int, @RequestBody cadastrar3PrestDTO: Cadastrar3PrestDTO):ResponseEntity<String> {
        val usuario = usuarioRepository.findById(id)
        if (usuario.isEmpty) {
            return ResponseEntity.status(404).body("Usuário não encontrado!")
        }

    }


}