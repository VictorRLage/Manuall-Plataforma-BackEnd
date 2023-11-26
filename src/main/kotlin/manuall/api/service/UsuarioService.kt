package manuall.api.service

import manuall.api.domain.*
import manuall.api.dto.perfil.NotificacaoDto
import manuall.api.dto.perfil.NotificacaoSolicitacaoDto
import manuall.api.dto.usuario.*
import manuall.api.enums.StatusProcesso
import manuall.api.enums.TipoUsuario
import manuall.api.repository.*
import manuall.api.security.JwtTokenManager
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.io.FileWriter
import java.io.InputStream
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import java.util.concurrent.ArrayBlockingQueue

@Service
class UsuarioService(
    val passwordEncoder: PasswordEncoder,
    val jwtTokenManager: JwtTokenManager,
    val authenticationManager: AuthenticationManager,
    val usuarioRepository: UsuarioRepository,
    val dadosEnderecoRepository: DadosEnderecoRepository,
    val areaRepository: AreaRepository,
    val servicoRepository: ServicoRepository,
    val usuarioServicoRepository: UsuarioServicoRepository,
    val solicitacaoRepository: SolicitacaoRepository
) {

    fun getIdByToken(token: String?): ResponseEntity<Int> {

        val usuario = jwtTokenManager.validateToken(token)
            ?: return ResponseEntity.status(480).build()

        return ResponseEntity.status(200).body(usuario.id)
    }

    fun buscarArea(): List<Area> {
        return areaRepository.findAll()
    }

    fun buscarNomeUsuario(token: String?): ResponseEntity<String?> {

        val usuario = jwtTokenManager.validateToken(token)
            ?: return ResponseEntity.status(480).build()

        return ResponseEntity.status(200).body(usuarioRepository.findById(usuario.id).get().nome)
    }

    fun buscarPlanoUsuario(token: String?): ResponseEntity<Int?> {

        val usuario = jwtTokenManager.validateToken(token)
            ?: return ResponseEntity.status(480).build()

        return ResponseEntity.status(200).body((usuario as Prestador).plano)
    }

    fun buscarTiposServico(idServico: Int): List<Servico> {
        return servicoRepository.findAllByAreaId(idServico)
    }

    fun loginChecar(email: String): ResponseEntity<List<Int>> {
        val possiveisUsuarios = usuarioRepository.findByEmail(email)

        return if (possiveisUsuarios.size > 1) {
            ResponseEntity.status(207).body(
                possiveisUsuarios.map { TipoUsuario.fromObjectToInt(it) }
            )
        } else {
            ResponseEntity.status(200).build()
        }
    }

    fun loginEfetuar(usuarioLoginRequest: UsuarioLoginRequest): ResponseEntity<LoginResponse> {

        val possiveisUsuarios = if (usuarioLoginRequest.tipoUsuario !== null) {
            usuarioRepository.findByEmailAndTipoUsuarioList(
                usuarioLoginRequest.email,
                TipoUsuario.fromIntToClass(usuarioLoginRequest.tipoUsuario!!)
            )
        } else {
            usuarioRepository.findByEmail(usuarioLoginRequest.email)
        }

        return if (possiveisUsuarios.isEmpty()) {
            ResponseEntity.status(401).build()
        } else {
            val usuario = possiveisUsuarios[0]

            if (passwordEncoder.matches(usuarioLoginRequest.senha, usuario.senha)) {
                when (usuario.status) {
                    null -> return ResponseEntity.status(206).body(
                        LoginResponse(
                            null,
                            null,
                            TipoUsuario.fromObjectToInt(usuario),
                            if (dadosEnderecoRepository.findByUsuarioId(usuario.id).isEmpty)
                                2
                            else
                                3,
                            null,
                            if (usuario is Prestador) usuario.plano else null
                        )
                    )

                    4 -> return ResponseEntity.status(403).body(
                        LoginResponse(
                            null,
                            "Aprovação negada",
                            null,
                            null,
                            null,
                            if (usuario is Prestador) usuario.plano else null
                        )
                    )

                    1 -> return ResponseEntity.status(403).body(
                        LoginResponse(
                            null,
                            "Aprovação pendente",
                            null,
                            null,
                            null,
                            if (usuario is Prestador) usuario.plano else null
                        )
                    )
                }

                val authentication = authenticationManager.authenticate(
                    UsernamePasswordAuthenticationToken(
                        TipoUsuario.fromObjectToString(usuario) + usuario.email,
                        usuarioLoginRequest.senha
                    )
                )

                SecurityContextHolder.getContext().authentication = authentication
                if (usuario is Prestador && usuario.plano == null)
                    ResponseEntity.status(206).body(
                        LoginResponse(
                            jwtTokenManager.generateToken(authentication),
                            null,
                            2,
                            4,
                            usuario.id,
                            usuario.plano
                        )
                    )
                else
                    ResponseEntity.status(200).body(
                        LoginResponse(
                            jwtTokenManager.generateToken(authentication),
                            null,
                            TipoUsuario.fromObjectToInt(usuario),
                            null,
                            null,
                            if (usuario is Prestador) usuario.plano else null
                        )
                    )
            } else {
                ResponseEntity.status(401).build()
            }
        }
    }

    fun checarValidadeLoginAdm(token: String?): ResponseEntity<Unit> {

        val usuario = jwtTokenManager.validateToken(token)
            ?: return ResponseEntity.status(480).build()

        if (usuario !is Administrador) return ResponseEntity.status(480).build()

        return ResponseEntity.status(200).build()
    }

    fun logoff(token: String?): ResponseEntity<Unit> {

        if (jwtTokenManager.validateToken(token) == null)
            return ResponseEntity.status(480).build()

        jwtTokenManager.expirarToken(token!!)
        return ResponseEntity.status(200).build()
    }

    fun getNotificacoes(token: String?): ResponseEntity<List<NotificacaoDto>> {

        val usuario = jwtTokenManager.validateToken(token)
            ?: return ResponseEntity.status(480).build()

        val usuarioSolicitacoes = when (usuario) {
            is Contratante -> solicitacaoRepository.findByContratanteId(usuario.id)
            is Prestador -> solicitacaoRepository.findByPrestadorId(usuario.id)
            else -> return ResponseEntity.status(404).build()
        }

        val notificacoes = mutableListOf<NotificacaoDto>()

        for (i in usuarioSolicitacoes.indices) {
            val solicitacao = usuarioSolicitacoes[i]
            when (solicitacao.status) {
                1 -> {
                    notificacoes.add(
                        NotificacaoDto(
                            solicitacao.id,
                            if (usuario is Prestador)
                                solicitacao.contratante.nome!!
                            else
                                solicitacao.prestador.nome!!,
                            1,
                            solicitacao.dataFim,
                            NotificacaoSolicitacaoDto(
                                solicitacao.incluiAula,
                                solicitacao.servico.nome,
                                solicitacao.tamanho,
                                solicitacao.medida,
                                solicitacao.descricao,
                                solicitacao.solicitacaoImg.map { it.anexo }
                            )
                        )
                    )
                }

                2 -> {
                    if (solicitacao.dataFim != null) continue

                    val cal = Calendar.getInstance()
                    cal.add(Calendar.DATE, -4)
                    if (solicitacao.dataInicio != null && solicitacao.dataInicio!!.before(cal.time)) {
                        if (solicitacao.formOrcamento == null) {
                            notificacoes.add(
                                NotificacaoDto(
                                    solicitacao.id,
                                    if (usuario is Prestador)
                                        solicitacao.contratante.nome!!
                                    else
                                        solicitacao.prestador.nome!!,
                                    4,
                                    solicitacao.dataFim,
                                    null
                                )
                            )
                        }
                    } else {
                        notificacoes.add(
                            NotificacaoDto(
                                solicitacao.id,
                                if (usuario is Prestador)
                                    solicitacao.contratante.nome!!
                                else
                                    solicitacao.prestador.nome!!,
                                2,
                                null,
                                null,
                            )
                        )
                    }
                }

                4 -> {
                    notificacoes.add(
                        NotificacaoDto(
                            solicitacao.id,
                            if (usuario is Prestador)
                                solicitacao.contratante.nome!!
                            else
                                solicitacao.prestador.nome!!,
                            3,
                            solicitacao.dataFim,
                            null,
                        )
                    )
                }
            }
        }

        return ResponseEntity.status(200).body(notificacoes)
    }

    fun aprovacoesPendentes(token: String?): ResponseEntity<List<AprovacaoDto>> {
        val usuario = jwtTokenManager.validateToken(token)
            ?: return ResponseEntity.status(480).build()

        if (usuario !is Administrador)
            return ResponseEntity.status(403).build()

        val usuarios = usuarioRepository.aprovacoesPendentes()

        if (usuarios.isEmpty()) return ResponseEntity.status(200).body(listOf())

        val filaPrestadores = ArrayBlockingQueue<AprovacaoDto>(usuarios.size)

        usuarios.forEach {
            filaPrestadores.put(
                AprovacaoDto(
                    it.id,
                    it.nome,
                    it.email,
                    it.telefone,
                    it.cpf,
                    it.cidade,
                    it.estado,
                    it.area,
                    usuarioServicoRepository.findServicosNomeByUsuarioId(it.id),
                    it.orcamentoMin,
                    it.orcamentoMax,
                    it.ensino,
                    it.statusProcesso,
                    it.status
                )
            )
        }

        val prestadores = mutableListOf<AprovacaoDto>()
        while (!filaPrestadores.isEmpty()) {
            prestadores.add(filaPrestadores.take())
        }

        return ResponseEntity.status(200).body(prestadores)
    }

    fun gravarCsvAprovacoes(lista: List<AprovacaoDto>, nomeArquivo: String) {
        FileWriter(nomeArquivo).use { arquivo ->
            Formatter(arquivo).use { saida ->
                for (aprovacao in lista) {
                    val servicos = usuarioServicoRepository.findServicosNomeByUsuarioId(aprovacao.id)
                    val servicosString = servicos.joinToString("| ")

                    saida.format("%d;%s;%s;%s;%s;%s;%s;%s;%s;%.2f;%.2f;%s;%s;%d\n",
                        aprovacao.id,
                        aprovacao.nome,
                        aprovacao.email,
                        aprovacao.telefone,
                        aprovacao.cpf,
                        aprovacao.cidade,
                        aprovacao.estado,
                        aprovacao.area,
                        servicosString,
                        aprovacao.orcamentoMin,
                        aprovacao.orcamentoMax,
                        aprovacao.ensino,
                        aprovacao.statusProcesso?.let { StatusProcesso.fromIdToTexto(it) },
                        aprovacao.status
                    )
                }
            }
        }
    }

    fun gravarTxtAprovacoes(lista: List<AprovacaoDto>, nomeArquivo: String){
        val arquivo =  FileWriter(nomeArquivo)
        val saida = Formatter(arquivo)

        var header = "00APROVACAO20232"
        header += LocalDateTime.now()
            .format(
                DateTimeFormatter
                .ofPattern("dd-MM-yyyy HH:mm:ss"))
        header += "01"

        saida.format(header + "\n")

        for (aprovacao in lista) {
            val servicos = usuarioServicoRepository.findServicosNomeByUsuarioId(aprovacao.id)
            val servicosString = servicos.joinToString(" | ")

            val linha = StringBuilder()
            linha.append("02")
            linha.append(String.format("%-30.30s", aprovacao.area)) //confere
            linha.append(String.format("%-4.4s", aprovacao.id)) //confere
            linha.append(String.format("%-60.60s", aprovacao.nome)) //confere
            linha.append(String.format("%-256.256s", aprovacao.email)) //confere
            linha.append(String.format("%-11.11s", aprovacao.telefone)) //confere
            linha.append(String.format("%-11.11s", aprovacao.cpf)) //confere
            linha.append(String.format("%-35.35s", aprovacao.cidade)) //confere
            linha.append(String.format("%-25.25s", aprovacao.estado)) //confere
            linha.append(String.format("%-272.272s", servicosString)) //confere
            linha.append(String.format("%10.2f", aprovacao.orcamentoMin)) //confere
            linha.append(String.format("%10.2f", aprovacao.orcamentoMax)) //confere
            linha.append(String.format("%-5.5s", aprovacao.ensino)) //confere
            linha.append(String.format("%01d", aprovacao.statusProcesso)) //confere
            linha.append(String.format("%01d", aprovacao.status)) //confere

            saida.format(linha.toString() + "\n")
        }

        var trailer = "01"
        trailer += String.format("%010d", lista.size)

        saida.format(trailer + "\n")

        saida.close()
        arquivo.close()
    }

    fun atualizarAprovacoesViaCsv(inputStream: InputStream) {
        Scanner(inputStream).use { leitor ->
            leitor.useDelimiter(";|\\n")
            while (leitor.hasNext()) {
                val id = leitor.nextInt()
                val nome = leitor.next()
                val email = leitor.next()
                val telefone = leitor.next()
                val cpf = leitor.next()
                val cidade = leitor.next()
                val estado = leitor.next()
                val area = leitor.next()
                val servicos = leitor.next()
                val orcamentoMin = leitor.nextDouble()
                val orcamentoMax = leitor.nextDouble()
                val ensino = leitor.nextBoolean()
                val statusProcesso = leitor.next()
                val status = leitor.nextInt()

                val usuario = usuarioRepository.findById(id).get()
                usuario as Prestador

                usuario.status = status
                usuario.statusProcessoAprovacao = StatusProcesso.fromTextoToId(statusProcesso)
                usuarioRepository.save(usuario)
            }
        }
    }

    fun aprovar(token: String?, idPrestador: Int, aprovar: Int): ResponseEntity<Unit> {

        if (jwtTokenManager.validateToken(token) == null)
            return ResponseEntity.status(480).build()

        val usuario = usuarioRepository.findById(idPrestador).get()

        usuario as Prestador

        usuario.status = aprovar
        usuario.plano = 3

        usuarioRepository.save(usuario)

        return ResponseEntity.status(200).build()
    }

    fun alterarStatusProcesso(token: String?, idPrestador: Int, statusProcesso: Int): ResponseEntity<Unit> {

        if (jwtTokenManager.validateToken(token) == null)
            return ResponseEntity.status(480).build()

        val usuario = usuarioRepository.findById(idPrestador).get()

        usuario as Prestador

        usuario.statusProcessoAprovacao = statusProcesso

        usuarioRepository.save(usuario)

        return ResponseEntity.status(200).build()
    }

}