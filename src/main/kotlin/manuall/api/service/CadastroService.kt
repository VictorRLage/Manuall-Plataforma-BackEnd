package manuall.api.service

import manuall.api.domain.*
import manuall.api.dto.cadastro.*
import manuall.api.dto.usuario.LoginResponse
import manuall.api.enums.TipoUsuario
import manuall.api.repository.*
import manuall.api.security.JwtTokenManager
import org.springframework.http.ResponseEntity
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class CadastroService(
    val prospectRepository: ProspectRepository,
    val usuarioRepository: UsuarioRepository,
    val passwordEncoder: PasswordEncoder,
    val dadosEnderecoRepository: DadosEnderecoRepository,
    val servicoRepository: ServicoRepository,
    val usuarioServicoRepository: UsuarioServicoRepository,
    val areaRepository: AreaRepository,
    val jwtTokenManager: JwtTokenManager
) {

    fun checarProspect(prospectDTO: ProspectDto): ResponseEntity<PipefyReturnDto> {
        val usuario = prospectRepository.findByEmailAndTipoUsuario(prospectDTO.email, prospectDTO.tipoUsuario)
        return if (usuario.isPresent) {
            val pipefyReturnDTO = PipefyReturnDto()
            val usuarioAtual = usuario.get()

            pipefyReturnDTO.nome = usuarioAtual.nome
            pipefyReturnDTO.telefone = usuarioAtual.fone
            pipefyReturnDTO.optCidade = when (usuarioAtual.optCidade) {
                1 -> "São Paulo"
                2 -> "São Bernardo do Campo"
                3 -> "São Caetano do Sul"
                4 -> "Santo André"
                5 -> "Osasco"
                6 -> "Bauru"
                else -> null
            }
            pipefyReturnDTO.optArea = usuarioAtual.area?.id
            pipefyReturnDTO.blnInteresseEnsinar = usuarioAtual.blnInteresseEnsinar

            ResponseEntity.status(200).body(pipefyReturnDTO)
        } else {
            ResponseEntity.status(204).build()
        }

    }

    fun cadastrar1(cadastrar1DTO: Cadastrar1Dto): ResponseEntity<Cad1Response> {

        if (cadastrar1DTO.isReturning) {
            if (cadastrar1DTO.id == null) return ResponseEntity.status(404).build()
            val possivelUsuario = usuarioRepository.findById(cadastrar1DTO.id)
            if (possivelUsuario.isEmpty) {
                return ResponseEntity.status(404).build()
            }
            val usuario = possivelUsuario.get()
            usuario.nome = cadastrar1DTO.nome
            usuario.email = cadastrar1DTO.email
            usuario.cpf = cadastrar1DTO.cpf
            usuario.telefone = cadastrar1DTO.telefone
            usuarioRepository.save(usuario)
            return ResponseEntity.status(201).body(
                Cad1Response(null, usuario.id)
            )
        }

        val emailExistente = usuarioRepository.findByEmailAndTipoUsuario(
            cadastrar1DTO.email,
            TipoUsuario.fromIntToClass(cadastrar1DTO.tipoUsuario)
        )
        if (emailExistente.isPresent) {
            val usuario = emailExistente.get()

            return if (usuario.status == null) {
                if (dadosEnderecoRepository.findByUsuarioId(usuario.id).isEmpty)
                    ResponseEntity.status(206).body(
                        Cad1Response(2, usuario.id)
                    )
                else if (usuario is Prestador)
                    ResponseEntity.status(206).body(
                        Cad1Response(3, usuario.id)
                    )
                else
                    ResponseEntity.status(409).build()
            } else {
                if (usuario.canal !== null)
                    ResponseEntity.status(409).build()
                else
                    ResponseEntity.status(206).body(
                        Cad1Response(4, usuario.id)
                    )
            }
        } else {
            val usuarioVisitante = prospectRepository.findByEmail(cadastrar1DTO.email)
            val canal: Int? =
                if (usuarioVisitante != null)
                    usuarioVisitante.optCanal
                else
                    0

            val usuario = if (cadastrar1DTO.tipoUsuario == 1) {
                Contratante()
            } else {
                val temporaryPrestador = Prestador()
                temporaryPrestador.acessos = 0
                temporaryPrestador
            }
            usuario.nome = cadastrar1DTO.nome
            usuario.email = cadastrar1DTO.email
            usuario.cpf = cadastrar1DTO.cpf
            usuario.telefone = cadastrar1DTO.telefone
            usuario.senha = passwordEncoder.encode(cadastrar1DTO.senha)
            usuario.canal = canal

            return ResponseEntity.status(201).body(
                Cad1Response(null, usuarioRepository.save(usuario).id)
            )
        }
    }

    fun cadastrar2(id: Int, cadastrar2DTO: Cadastrar2Dto): ResponseEntity<String> {
        val usuario = usuarioRepository.findById(id)
        if (usuario.isEmpty) {
            return ResponseEntity.status(404).body("Usuário não encontrado!")
        }
        val enderecoCadastrado = dadosEnderecoRepository.findByUsuarioId(id)
        val dadosEndereco =
            if (enderecoCadastrado.isPresent) {
                enderecoCadastrado.get()
            } else {
                if (usuario.get() is Contratante) {
                    val novoUsuario: Usuario = usuario.get()
                    novoUsuario.status = 2
                    usuarioRepository.save(novoUsuario)
                }

                val newDadosEndereco = DadosEndereco()
                newDadosEndereco.usuario = usuario.get()
                newDadosEndereco
            }
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

    fun cadastrar3(id: Int, cadastrar3Dto: Cadastrar3Dto): ResponseEntity<Unit> {
        val usuario = usuarioRepository.findById(id)
        if (usuario.isEmpty) {
            return ResponseEntity.status(404).build()
        }
        val prestador = usuario.get() as? Prestador
            ?: return ResponseEntity.status(403).build()

        if (prestador.area != null) {
            return ResponseEntity.status(409).build()
        }

        val servicos = servicoRepository.findAll()

        val servicosMap = mutableMapOf<Int, Servico>()
        servicos.forEach {
            servicosMap[it.id] = it
        }

        cadastrar3Dto.servico.forEach loopServicos@{

            if (servicosMap[it] == null) return@loopServicos

            val usuarioServico = UsuarioServico()
            usuarioServico.prestador = prestador
            usuarioServico.servico = servicosMap[it]!!
            usuarioServicoRepository.save(usuarioServico)
        }

        prestador.area = areaRepository.findById(cadastrar3Dto.area).get()
        prestador.prestaAula = cadastrar3Dto.prestaAula
        prestador.orcamentoMin = cadastrar3Dto.orcamentoMin
        prestador.orcamentoMax = cadastrar3Dto.orcamentoMax
        prestador.status = 1
        prestador.statusProcessoAprovacao = 1

        usuarioRepository.save(prestador)
        return ResponseEntity.status(201).build()
    }

    fun cadastrar4(token: String?, idPlano: Int): ResponseEntity<Unit> {

        val usuario = jwtTokenManager.validateToken(token)
            ?: return ResponseEntity.status(480).build()

        if (usuario !is Prestador)
            return ResponseEntity.status(403).build()

        usuario.plano = idPlano
        usuarioRepository.save(usuario)
        return ResponseEntity.status(201).build()
    }

    fun getCad1Info(userId: Int): ResponseEntity<Cad1InfoResponse> {
        val user = usuarioRepository.findById(userId)

        if (user.isEmpty) {
            return ResponseEntity.status(404).build()
        }

        val usuario = user.get()

        return ResponseEntity.status(200).body(
            Cad1InfoResponse(
                usuario.nome,
                usuario.email,
                usuario.cpf,
                usuario.telefone,
            )
        )
    }

    fun getCad2Info(userId: Int): ResponseEntity<Cad2InfoResponse> {
        val endereco = dadosEnderecoRepository.findByUsuarioId(userId)

        if (endereco.isEmpty) {
            return ResponseEntity.status(404).build()
        }

        val dadosEndereco = endereco.get()

        return ResponseEntity.status(200).body(
            Cad2InfoResponse(
                dadosEndereco.cep,
                dadosEndereco.estado,
                dadosEndereco.cidade,
                dadosEndereco.bairro,
                dadosEndereco.rua,
                dadosEndereco.numero,
                dadosEndereco.complemento
            )
        )
    }
}
