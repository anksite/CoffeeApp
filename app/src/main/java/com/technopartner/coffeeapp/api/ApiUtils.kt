package com.technopartner.coffeeapp.api

import com.technopartner.coffeeapp.unil.Cons
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiUtils {
    fun getApiService(): InterfaceApi {
        val httpClient = OkHttpClient.Builder()

        //if (BuildConfig.DEBUG) {
            httpClient.addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        //}

        val retrofit = Retrofit.Builder()
            .baseUrl(Cons.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient.build())
            .build()
        return retrofit.create(InterfaceApi::class.java)
    }
}