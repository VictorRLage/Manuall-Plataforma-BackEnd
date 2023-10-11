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
class CadastroService (
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

        val emailExistente = usuarioRepository.findByEmailAndTipoUsuario(
            cadastrar1DTO.email,
            TipoUsuario.fromIntToClass(cadastrar1DTO.tipoUsuario)
        )
        if (emailExistente.isPresent) {
            val usuario = emailExistente.get()

            println(usuario.status)
            println(usuario::class.java)
            println(usuario is Prestador)
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
                    if (cadastrar1DTO.isReturning) {
                        usuario.nome = cadastrar1DTO.nome
                        usuario.email = cadastrar1DTO.email
                        usuario.cpf = cadastrar1DTO.cpf
                        usuario.telefone = cadastrar1DTO.telefone
                        usuario.senha = passwordEncoder.encode(cadastrar1DTO.senha)
                        usuarioRepository.save(usuario)
                        ResponseEntity.status(201).build()
                    } else
                        ResponseEntity.status(409).build()
            } else {
                if (usuario.canal !== null)
                    if (cadastrar1DTO.isReturning) {
                        usuario.nome = cadastrar1DTO.nome
                        usuario.email = cadastrar1DTO.email
                        usuario.cpf = cadastrar1DTO.cpf
                        usuario.telefone = cadastrar1DTO.telefone
                        usuario.senha = passwordEncoder.encode(cadastrar1DTO.senha)
                        usuarioRepository.save(usuario)
                        ResponseEntity.status(201).build()
                    } else
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

            if (usuario.get() is Contratante) {
                val novoUsuario: Usuario = usuario.get()
                novoUsuario.status = 2
                usuarioRepository.save(novoUsuario)
            }

            dadosEnderecoRepository.save(dadosEndereco)
            return ResponseEntity.status(201).body("Endereço cadastrado com sucesso!")
        }
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

        cadastrar3Dto.servico.forEach {
            val usuarioServico = UsuarioServico()
            usuarioServico.prestador = prestador
            usuarioServico.servico = servicoRepository.findById(it).get()
            usuarioServicoRepository.save(usuarioServico)
        }

        prestador.area = areaRepository.findById(cadastrar3Dto.area).get()
        prestador.prestaAula = cadastrar3Dto.prestaAula
        prestador.orcamentoMin = cadastrar3Dto.orcamentoMin
        prestador.orcamentoMax = cadastrar3Dto.orcamentoMax
        prestador.status = 1

        usuarioRepository.save(prestador)
        return ResponseEntity.status(201).build()
    }

    fun cadastrar4(token: String?, idPlano: Int): ResponseEntity<String> {

        val usuario = jwtTokenManager.validateToken(token)
            ?: return ResponseEntity.status(480).build()

        if (usuario !is Prestador)
            return ResponseEntity.status(403).body("Usuário não é um prestador")

        usuario.plano = idPlano
        usuarioRepository.save(usuario)
        return ResponseEntity.status(201).body("Plano cadastrado com sucesso!")
    }
}