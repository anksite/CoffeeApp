package com.technopartner.coffeeapp.api.model

import com.google.gson.annotations.SerializedName

data class ResponseHome(
    @SerializedName("status") var status: String,
    @SerializedName("result") val result: DataHome,
)

data class DataHome(
    @SerializedName("greeting") var greeting: String,
    @SerializedName("name") val name: String,
    @SerializedName("saldo") val saldo: Int,
    @SerializedName("point") val point: Int,
    @SerializedName("qrcode") val qrcode: String,
    @SerializedName("banner") val banner: List<String>,
)