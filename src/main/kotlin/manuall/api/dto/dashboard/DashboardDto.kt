package manuall.api.dto.dashboard

data class DashboardDto(

    // KPIs
    val servicosConcluidos: Long,
    val tempoMedioResposta: Long?, // em minutos, if > 60 então front-end trata
    val valorArrecadado: Double,

    // Dashboards
    val servicos: List<DashboardServicosDto>, // grafico servicosMaisContratados e mediaAvaliacoesServico
    val avaliacoes: List<String>, // apenas os textos, para fazer a wordcloud por js
    val solicitacoesMensais: List<SolicitacoesMensaisDto>,
)