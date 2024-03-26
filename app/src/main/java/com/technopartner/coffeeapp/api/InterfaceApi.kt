package com.technopartner.coffeeapp.api

import com.technopartner.coffeeapp.api.model.ResponseHome
import com.technopartner.coffeeapp.api.model.ResponseLogin
import com.technopartner.coffeeapp.api.model.ResponseMenu
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST


interface InterfaceApi {

    @FormUrlEncoded
    @POST("/oauth/token")
    suspend fun oauthToken(
        @Field("grant_type") grantType: String,
        @Field("client_secret") clientSecret: String,
        @Field("client_id") clientId: String,
        @Field("username") username: String,
        @Field("password") password: String,
    ): ResponseLogin

    @GET("/api/home")
    suspend fun apiHome(
        @Header("Authorization") token: String
    ): ResponseHome

    @FormUrlEncoded
    @POST("/api/menu")
    suspend fun apiMenu(
        @Header("Authorization") token: String,
        @Field("show_all") showAll: String,
    ): ResponseMenu

}