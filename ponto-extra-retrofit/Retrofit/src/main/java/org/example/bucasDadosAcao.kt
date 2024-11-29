package org.example

import org.jsoup.Jsoup
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// Função para buscar dados de ação (cotação e dividendos) usando Retrofit + Jsoup
fun buscarDadosAcaoComJsoup(codigo: String) {
    val call = RetrofitClient.instance.getAcaoPage(codigo)

    call.enqueue(object : Callback<ResponseBody> {
        override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
            if (response.isSuccessful) {
                val html = response.body()?.string() // Obtemos o HTML como String
                if (html != null) {
                    // Usa o Jsoup para fazer parsing do HTML
                    val doc = Jsoup.parse(html)

                    // Extrai o valor da cotação usando o seletor correto
                    val cotacaoElement = doc.select("span.value").first()?.text()
                    println("Cotação Atual: $cotacaoElement")

                    // Busca o histórico de dividendos
                    buscarDividendos(doc)
                }
            } else {
                println("Erro ao recuperar os dados da página. Código de status: ${response.code()}")
            }
        }

        override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
            println("Erro: ${t.message}")
        }
    })
}

// Função para buscar os dividendos usando Jsoup
fun buscarDividendos(doc: org.jsoup.nodes.Document) {
    try {
        // Seleciona a tabela de dividendos
        val dividendosTable = doc.select("table.table-dividends-history tbody tr")

        // Itera sobre as linhas da tabela para extrair as informações
        for (row in dividendosTable) {
            val tipo = row.select("td").get(0).text()  // Tipo de dividendo (Dividendos, JSCP, etc.)
            val dataCom = row.select("td").get(1).text()  // Data COM
            val pagamento = row.select("td").get(2).text()  // Data de pagamento
            val valor = row.select("td").get(3).text()  // Valor do dividendo

            println("Tipo: $tipo, Data COM: $dataCom, Pagamento: $pagamento, Valor: $valor")
        }
    } catch (e: Exception) {
        println("Erro ao buscar dividendos: ${e.message}")
    }
}