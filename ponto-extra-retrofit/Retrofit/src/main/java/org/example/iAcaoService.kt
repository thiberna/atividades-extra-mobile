package org.example

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface AcaoService {

    @GET("https://investidor10.com.br/acoes/{codigo}/")
    fun getAcaoPage(@Path("codigo") codigo: String): Call<ResponseBody>
}