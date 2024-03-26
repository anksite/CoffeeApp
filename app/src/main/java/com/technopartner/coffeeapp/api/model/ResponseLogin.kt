package com.technopartner.coffeeapp.api.model

import com.google.gson.annotations.SerializedName

data class ResponseLogin(
    @SerializedName("token_type") var tokenType: String,
    @SerializedName("expires_in") val expiresIn: Int,
    @SerializedName("access_token") val accessToken: String,
    @SerializedName("refresh_token") val refreshToken: String,
)