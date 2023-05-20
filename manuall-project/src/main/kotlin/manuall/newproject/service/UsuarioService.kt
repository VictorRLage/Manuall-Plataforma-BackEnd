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
    val servicoRepository: ServicoRepository,
    val usuarioServicoRepository: UsuarioServicoRepository
) {

    fun loginChecar(email: String): ResponseEntity<Int> {
        val possiveisUsuarios = usuarioRepository.findByEmail(email)

        return if (possiveisUsuarios.isEmpty()) {
            ResponseEntity.status(204).build()
        } else if (possiveisUsuarios.size > 1) {
            ResponseEntity.status(409).build()
        } else {
            ResponseEntity.status(200).body(possiveisUsuarios[0].get().tipoUsuario)
        }
    }

    fun loginEfetuar(usuarioLoginRequest: UsuarioLoginRequest): ResponseEntity<String> {

        // Pegando os usuários com o email requisitado em uma lista, já que podem
        // existir 2 usuários com o mesmo email e tipo_usuario diferentes
        val possivelUsuario = usuarioRepository.findByEmailAndTipoUsuario(usuarioLoginRequest.email, usuarioLoginRequest.tipoUsuario)

        return if (possivelUsuario.isEmpty) {
            ResponseEntity.status(401).body("Credenciais inválidas")
        } else {
            // Tendo em vista que existe apenas 1 usuário nesta lista, vamos simplificar e chamá-lo de "usuario"
            val usuario = possivelUsuario.get()

            // Autenticando sua senha, essa função decripta a senha do banco e a compara com a recebida
            if (passwordEncoder.matches(usuarioLoginRequest.senha, usuario.senha)) {

                when (usuario.status) {
                    null -> ResponseEntity.status(403).body("Usuário não finalizou o cadastro")
                    4 -> ResponseEntity.status(403).body("Aprovação negada")
                    1 -> ResponseEntity.status(403).body("Aprovação pendente")
                }

                // Gerando token de sessão com base no tipo_usuario e email, para identificar unicamente cada login
                val authentication = authenticationManager.authenticate(
                    UsernamePasswordAuthenticationToken(
                        usuario.tipoUsuario.toString()+usuario.email,
                        usuarioLoginRequest.senha
                    )
                )

                SecurityContextHolder.getContext().authentication = authentication
                ResponseEntity.status(200).body(jwtTokenManager.generateToken(authentication))

            } else {
                ResponseEntity.status(401).body("Credenciais inválidas")
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
            jwtTokenManager.getUserFromToken(token) ?: return ResponseEntity.status(480).build()
        } else {
            return ResponseEntity.status(480).build()
        }

        return if (passwordEncoder.matches(alterSenhaRequest.senhaAntiga, usuarioEncontrado.senha)) {

            usuarioEncontrado.senha = passwordEncoder.encode(alterSenhaRequest.senhaNova)
            ResponseEntity.status(200).body(usuarioRepository.save(usuarioEncontrado))

        } else {
            ResponseEntity.status(401).build()
        }

    }

    fun atualizarDesc(token: String, alterDescRequest: AlterDescRequest): ResponseEntity<Usuario> {

        // Checando se token foi expirado e então encontrando usuário por token
        val usuarioEncontrado = if (jwtTokenManager.validarToken(token)) {
            jwtTokenManager.getUserFromToken(token) ?: return ResponseEntity.status(480).build()
        } else {
            return ResponseEntity.status(480).build()
        }

        usuarioEncontrado.descricao = alterDescRequest.descricao
        return ResponseEntity.status(200).body(usuarioRepository.save(usuarioEncontrado))

    }

    fun deletar(token: String): ResponseEntity<String> {

        // Checando se token foi expirado e então encontrando usuário por token
        val usuarioEncontrado = if (jwtTokenManager.validarToken(token)) {
            jwtTokenManager.getUserFromToken(token) ?: return ResponseEntity.status(480).build()
        } else {
            return ResponseEntity.status(480).body("Token expirado")
        }

        descServicosRepository.deleteByUsuarioId(usuarioEncontrado.id)
        dadosEnderecoRepository.deleteByUsuarioId(usuarioEncontrado.id)
        usuarioRepository.deleteById(usuarioEncontrado.id)
        return ResponseEntity.status(200).body(null)

    }

    fun checarProspect(prospectDTO:ProspectDTO): ResponseEntity<PipefyReturnDTO> { // dto do retorno do pipefy
        val usuario = prospectRepository.findByEmailAndTipoUsuario(prospectDTO.email, prospectDTO.tipoUsuario)
        if (usuario.isPresent) {
            var pipefyReturnDTO = PipefyReturnDTO()
            pipefyReturnDTO.optCidade = when (usuario.get().optCidade) {
                1 -> "São Paulo"
                2 -> "São Bernardo do Campo"
                3 -> "São Caetano do Sul"
                4 -> "Santo André"
                5 -> "Osasco"
                6 -> "Bauru"
                else -> null
            }
            pipefyReturnDTO.nome = usuario.get().nome
            pipefyReturnDTO.telefone = usuario.get().fone
            // pipefyReturnDTO.area =
            pipefyReturnDTO.blnInteresseEnsinar = usuario.get().blnInteresseEnsinar

            return ResponseEntity.status(200).body(pipefyReturnDTO)
            TODO("fazerAreaDepoisDeAttPipefy")
        } else {
            return ResponseEntity.status(204).build()
        }

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
            usuario.telefone = cadastrar1DTO.telefone
            usuario.senha = passwordEncoder.encode(cadastrar1DTO.senha)
            usuario.tipoUsuario = cadastrar1DTO.tipoUsuario
            usuario.canal = canal
            usuario.acessos = 0
            usuario.status = null

            val usuarioAtual = usuarioRepository.save(usuario).id

            return ResponseEntity.status(201).body(usuarioAtual)
        }
    }

    fun cadastrar2(id:Int, cadastrar2DTO: Cadastrar2DTO): ResponseEntity<String> {
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
            dadosEndereco.cep = cadastrar2DTO.cep
            dadosEndereco.cidade = cadastrar2DTO.cidade
            dadosEndereco.estado = cadastrar2DTO.estado
            dadosEndereco.bairro = cadastrar2DTO.bairro
            dadosEndereco.rua = cadastrar2DTO.rua
            dadosEndereco.numero = cadastrar2DTO.numero
            dadosEndereco.complemento = cadastrar2DTO.complemento

            dadosEnderecoRepository.save(dadosEndereco)
            return ResponseEntity.status(201).body("Endereço cadastrado com sucesso!")
        }
    }

    fun cadastrar3Prest(id:Int, cadastrar3PrestDTO:Cadastrar3PrestDTO):ResponseEntity<String> {
        val usuario = usuarioRepository.findById(id)
        if (usuario.isEmpty) {
            return ResponseEntity.status(404).body("Usuário não encontrado!")
        }

        cadastrar3PrestDTO.servico.forEach {
            val usuarioServico = UsuarioServico()
            usuarioServico.usuario = usuario.get()
            usuarioServico.servico = it
            usuarioServicoRepository.save(usuarioServico)
        }

        val novoUsuario = usuario.get()
        novoUsuario.area = areaRepository.findById(cadastrar3PrestDTO.area).get()
        novoUsuario.prestaAula = cadastrar3PrestDTO.prestaAula
        novoUsuario.orcamentoMin = cadastrar3PrestDTO.orcamentoMin
        novoUsuario.orcamentoMax = cadastrar3PrestDTO.orcamentoMax

        usuarioRepository.save(novoUsuario)
        return ResponseEntity.status(201).body("Serviços cadastrados com sucesso!")
    }

    fun buscarArea():List<Area> {
        val areas = areaRepository.findAll()
        return areas
    }

    fun buscarTiposServico(@PathVariable id:Int): List<Servico> {
        val servico = servicoRepository.findAllByAreaId(id)
        return servico
    }

    fun cadastrar4Prest(token: String, @PathVariable idPlano:Int): ResponseEntity<String> {
        val usuarioEncontrado = if (jwtTokenManager.validarToken(token)) {
            jwtTokenManager.getUserFromToken(token) ?: return ResponseEntity.status(480).build()
        } else {
            return ResponseEntity.status(480).build()
        }

        usuarioEncontrado.plano = idPlano
        usuarioRepository.save(usuarioEncontrado)
        return ResponseEntity.status(201).body("Plano cadastrado com sucesso!")
    }


}