package org.example

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.google.gson.GsonBuilder

object RetrofitClient {
    private const val BASE_URL = "https://investidor10.com.br/"

    val instance: AcaoService by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .build()
        retrofit.create(AcaoService::class.java)
    }
}