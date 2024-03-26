package com.technopartner.coffeeapp.api

import com.technopartner.coffeeapp.api.model.ResponseHome
import com.technopartner.coffeeapp.api.model.ResponseLogin
import com.technopartner.coffeeapp.api.model.ResponseMenu

class RepositoryCoffeeApp {

    suspend fun oauthToken(
        interfaceApi: InterfaceApi,
        grantType: String,
        clientSecret: String,
        clientId: String,
        username: String,
        password: String,
    ): ResultWrapper<ResponseLogin> = NetworkCall.safeApiCall {
        interfaceApi.oauthToken(grantType, clientSecret, clientId, username, password)
    }

    suspend fun apiHome(
        interfaceApi: InterfaceApi,
        token: String
    ): ResultWrapper<ResponseHome> = NetworkCall.safeApiCall {
        interfaceApi.apiHome(token)
    }

    suspend fun apiMenu(
        interfaceApi: InterfaceApi,
        token: String,
        showAll: String
    ): ResultWrapper<ResponseMenu> = NetworkCall.safeApiCall {
        interfaceApi.apiMenu(token, showAll)
    }

}
