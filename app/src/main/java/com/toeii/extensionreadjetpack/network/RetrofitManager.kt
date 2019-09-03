package com.toeii.extensionreadjetpack.network

import com.toeii.extensionreadjetpack.ERApplication
import com.toeii.extensionreadjetpack.config.ERAppConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.jetbrains.anko.info
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


object RetrofitManager {

    val apiService: ApiService by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
        Retrofit.Builder()
            .baseUrl(ERAppConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(genericOkClient())
            .build().create(ApiService::class.java)

    }

    private fun genericOkClient(): OkHttpClient {
        val httpLoggingInterceptor = HttpLoggingInterceptor(
            object : HttpLoggingInterceptor.Logger {
                override fun log(message: String) {
                    ERApplication.logger.info(message)
                }
            })

        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        val urlInterceptor = RequestUrlInterceptor()

        return OkHttpClient.Builder()
            .connectTimeout(5000L, TimeUnit.MILLISECONDS)
            .readTimeout(10_000, TimeUnit.MILLISECONDS)
            .writeTimeout(30_000, TimeUnit.MILLISECONDS)
            .addInterceptor(urlInterceptor)
            .addInterceptor(httpLoggingInterceptor)
            .build()
    }
}