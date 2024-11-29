package org.example

data class Cotacao(
    val valorAtual: Double
)

data class Dividendo(
    val dataPagamento: String,
    val valor: Double
)

data class AcaoInfo(
    val cotacao: Cotacao,
    val dividendos: List<Dividendo>
)
