package com.technopartner.coffeeapp.api.model

import com.google.gson.annotations.SerializedName

data class ResponseMenu(
    @SerializedName("status") var status: String,
    @SerializedName("result") val result: DataCategories,
)

data class DataCategories(
    @SerializedName("categories") var categories: List<DataCategory>,
)

data class DataCategory(
    @SerializedName("category_name") var categoryName: String,
    @SerializedName("menu") var menu: List<DataMenu>,
)

data class DataMenu(
    @SerializedName("name") var name: String,
    @SerializedName("description") val description: String,
    @SerializedName("photo") val photo: String,
    @SerializedName("price") val price: Int,
)