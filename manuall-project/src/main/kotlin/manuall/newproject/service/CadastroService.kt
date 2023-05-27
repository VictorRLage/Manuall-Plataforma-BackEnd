package manuall.newproject.service

import manuall.newproject.domain.DadosEndereco
import manuall.newproject.domain.Usuario
import manuall.newproject.domain.UsuarioServico
import manuall.newproject.dto.*
import manuall.newproject.repository.*
import manuall.newproject.security.JwtTokenManager
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
}