package com.toeii.extensionreadjetpack.base

import android.util.Log
import com.toeii.extensionreadjetpack.config.ERAppConfig
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import okhttp3.Interceptor
import okhttp3.Response

class BaseUrlInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {

        val request = chain.request()
        val oldHttpUrl = request.url
        val builder = request.newBuilder()
        val headerValues = request.header("urls")

        if (headerValues != null && headerValues.isNotEmpty()) {

            builder.removeHeader("urls")
            val headerValue = headerValues[0]
            val newBaseUrl = if(headerValue.toString().isNotEmpty()){
                ERAppConfig.BASE_URL_KY.toHttpUrlOrNull()!!
            }else{
                ERAppConfig.BASE_URL.toHttpUrlOrNull()!!
            }

            val newFullUrl = oldHttpUrl
                    .newBuilder()
                .scheme("http")
                .host(newBaseUrl.host)
                .build()
            return chain.proceed(builder.url(newFullUrl).build())
        }
        return chain.proceed(request)

    }

}
