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
import java.util.*

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
    val usuarioImgRepository: UsuarioImgRepository,
    val usuarioServicoRepository: UsuarioServicoRepository,
    val avaliacaoRepository: AvaliacaoRepository
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
                    null -> {

                        if (dadosEnderecoRepository.findByUsuarioId(usuario.id).isEmpty) {
                            // Parou o cadastro na fase 2
                            ResponseEntity.status(403).body(
                                LoginResponse(
                                    usuario.id,
                                    2
                                )
                            )
                        } else {
                            // Parou o cadastro na fase 3
                            ResponseEntity.status(403).body(
                                LoginResponse(
                                    usuario.id,
                                    3
                                )
                            )
                        }

                    }
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
                // Token + 200 = Cadastro 100% concluído
                // Token + 403 = Parou na escolha de plano
                ResponseEntity.status(if (usuario.plano == null) 206 else 200).body(jwtTokenManager.generateToken(authentication))

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

    fun checarProspect(prospectDTO: ProspectDTO): ResponseEntity<PipefyReturnDTO> { // dto do retorno do pipefy
        val usuario = prospectRepository.findByEmailAndTipoUsuario(prospectDTO.email, prospectDTO.tipoUsuario)
        if (usuario.isPresent) {
            val pipefyReturnDTO = PipefyReturnDTO()
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

    fun cadastrar2(id: Int, cadastrar2DTO: Cadastrar2DTO): ResponseEntity<String> {
        val usuario = usuarioRepository.findById(id)
        if (usuario.isEmpty) {
            return ResponseEntity.status(404).body("Usuário não encontrado!")
        }
        val enderecoCadastrado = dadosEnderecoRepository.findByUsuarioId(id)
        if (enderecoCadastrado.isPresent) {
            return ResponseEntity.status(409).body("Endereço já cadastrado!")
        } else {
            val dadosEndereco = DadosEndereco()
            dadosEndereco.usuario = usuario.get()
            dadosEndereco.cep = cadastrar2DTO.cep
            dadosEndereco.cidade = cadastrar2DTO.cidade
            dadosEndereco.estado = cadastrar2DTO.estado
            dadosEndereco.bairro = cadastrar2DTO.bairro
            dadosEndereco.rua = cadastrar2DTO.rua
            dadosEndereco.numero = cadastrar2DTO.numero
            dadosEndereco.complemento = cadastrar2DTO.complemento

            if (usuario.get().tipoUsuario == 1) {
                val novoUsuario: Usuario = usuario.get()
                novoUsuario.status = 2
                usuarioRepository.save(novoUsuario)
            }

            dadosEnderecoRepository.save(dadosEndereco)
            return ResponseEntity.status(201).body("Endereço cadastrado com sucesso!")
        }
    }

    fun cadastrar3Prest(id: Int, cadastrar3PrestDTO: Cadastrar3PrestDTO): ResponseEntity<String> {
        val usuario = usuarioRepository.findById(id)
        if (usuario.isEmpty) {
            return ResponseEntity.status(404).body("Usuário não encontrado!")
        }

        val novoUsuario = usuario.get()

        if (novoUsuario.tipoUsuario != 2) {
            return ResponseEntity.status(403).body("Usuário é um contratante")
        } else if (novoUsuario.area != null) {
            return ResponseEntity.status(409).body("Campos já cadastrados!")
        }

        cadastrar3PrestDTO.servico.forEach {
            val usuarioServico = UsuarioServico()
            usuarioServico.usuario = usuario.get()
            usuarioServico.servico = servicoRepository.findById(it).get()
            usuarioServicoRepository.save(usuarioServico)
        }

        novoUsuario.area = areaRepository.findById(cadastrar3PrestDTO.area).get()
        novoUsuario.prestaAula = cadastrar3PrestDTO.prestaAula
        novoUsuario.orcamentoMin = cadastrar3PrestDTO.orcamentoMin
        novoUsuario.orcamentoMax = cadastrar3PrestDTO.orcamentoMax
        novoUsuario.status = if (novoUsuario.tipoUsuario == 2) 1 else 2

        usuarioRepository.save(novoUsuario)
        return ResponseEntity.status(201).body("Serviços cadastrados com sucesso!")
    }

    fun buscarArea(): List<Area> {
        return areaRepository.findAll()
    }

    fun buscarTiposServico(@PathVariable id: Int): List<Servico> {
        return servicoRepository.findAllByAreaId(id)
    }

    fun cadastrar4Prest(token: String, idPlano: Int): ResponseEntity<String> {
        val usuarioEncontrado = if (jwtTokenManager.validarToken(token)) {
            jwtTokenManager.getUserFromToken(token) ?: return ResponseEntity.status(480).build()
        } else {
            return ResponseEntity.status(480).build()
        }

        usuarioEncontrado.plano = idPlano
        usuarioRepository.save(usuarioEncontrado)
        return ResponseEntity.status(201).body("Plano cadastrado com sucesso!")
    }

    fun getPrestadoresFiltrado(idArea: String, filtro: String, crescente: Boolean): ResponseEntity<List<UsuariosFilteredList>> {

        val a = when (filtro) {
            "Nota" ->
                if (crescente)
                    if (idArea == "null")
                        usuarioRepository.findAllOrderByNotaAsc()
                    else
                        usuarioRepository.findByAreaIdOrderByNotaAsc(idArea.toInt())
                else
                    if (idArea == "null")
                        usuarioRepository.findAllOrderByNotaDesc()
                    else
                        usuarioRepository.findByAreaIdOrderByNotaDesc(idArea.toInt())
            "PrecoMax" ->
                if (crescente)
                    if (idArea == "null")
                        usuarioRepository.findAllOrderByPrecoMaxAsc()
                    else
                        usuarioRepository.findByAreaIdOrderByPrecoMaxAsc(idArea.toInt())
                else
                    if (idArea == "null")
                        usuarioRepository.findAllOrderByPrecoMaxDesc()
                    else
                        usuarioRepository.findByAreaIdOrderByPrecoMaxDesc(idArea.toInt())
            "PrecoMin" ->
                if (crescente)
                    if (idArea == "null")
                        usuarioRepository.findAllOrderByPrecoMinAsc()
                    else
                        usuarioRepository.findByAreaIdOrderByPrecoMinAsc(idArea.toInt())
                else
                    if (idArea == "null")
                        usuarioRepository.findAllOrderByPrecoMinDesc()
                    else
                        usuarioRepository.findByAreaIdOrderByPrecoMinDesc(idArea.toInt())
            "Alfabetica" ->
                if (crescente)
                    if (idArea == "null")
                        usuarioRepository.findAllOrderByAlfabeticaAsc()
                    else
                        usuarioRepository.findByAreaIdOrderByAlfabeticaAsc(idArea.toInt())
                else
                    if (idArea == "null")
                        usuarioRepository.findAllOrderByAlfabeticaDesc()
                    else
                        usuarioRepository.findByAreaIdOrderByAlfabeticaDesc(idArea.toInt())
            "Servico" ->
                if (crescente)
                    if (idArea == "null")
                        usuarioRepository.findAllOrderByServicoAsc()
                    else
                        usuarioRepository.findByAreaIdOrderByServicoAsc(idArea.toInt())
                else
                    if (idArea == "null")
                        usuarioRepository.findAllOrderByServicoDesc()
                    else
                        usuarioRepository.findByAreaIdOrderByServicoDesc(idArea.toInt())
            "ServicoAula" ->
                if (crescente)
                    if (idArea == "null")
                        usuarioRepository.findAllOrderByServicoAulaAsc()
                    else
                        usuarioRepository.findByAreaIdOrderByServicoAulaAsc(idArea.toInt())
                else
                    if (idArea == "null")
                        usuarioRepository.findAllOrderByServicoAulaDesc()
                    else
                        usuarioRepository.findByAreaIdOrderByServicoAulaDesc(idArea.toInt())
            else -> return ResponseEntity.status(404).build()
        }

        return ResponseEntity.status(200).body(a)

    }

    fun checarPrestador(token: String): ResponseEntity<PerfilDTO> {
        val usuarioEncontrado = if (jwtTokenManager.validarToken(token)) {
            jwtTokenManager.getUserFromToken(token) ?: return ResponseEntity.status(480).build()
        } else {
            return ResponseEntity.status(480).build()
        }

        val dadosEndereco = dadosEnderecoRepository.findByUsuarioId(usuarioEncontrado.id).get()
        val dadosAvaliacao = avaliacaoRepository.findByPrestadorUsuarioId(usuarioEncontrado.id)
        val nomeArea = areaRepository.findAreaNomeByUsuarioId(usuarioEncontrado.id)
        val urls = usuarioImgRepository.findUrlsByUsuarioId(usuarioEncontrado.id)
        val servicos = usuarioServicoRepository.findServicosNomeByUsuarioId(usuarioEncontrado.id)

        val perfilDTO = PerfilDTO(
            nomeArea!!,
            usuarioEncontrado.descricao!!,
            usuarioEncontrado.anexoPfp!!,
            usuarioEncontrado.nome!!,
            usuarioEncontrado.orcamentoMin!!,
            usuarioEncontrado.orcamentoMax!!,
            usuarioEncontrado.prestaAula!!,
            dadosEndereco.estado!!,
            dadosEndereco.cidade!!,
            urls,
            servicos,
            dadosAvaliacao
        )
        return ResponseEntity.status(200).body(perfilDTO)
    }

    fun acessarPerfilPrestador(idPrestador: Int): ResponseEntity<Void> {
        val prestador = usuarioRepository.findById(idPrestador)
        if (prestador.isEmpty) {
            return ResponseEntity.status(404).build()
        }
        val prestadorEncontrado = prestador.get()

        prestadorEncontrado.acessos = prestadorEncontrado.acessos!! + 1

        usuarioRepository.save(prestadorEncontrado)

        return ResponseEntity.status(200).build()
    }

}