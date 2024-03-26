package com.technopartner.coffeeapp.api

sealed class ResultWrapper<out T> {
    data class Success<out T>(val value: T) : ResultWrapper<T>()
    data class Error(val e: Exception? = null) : ResultWrapper<Nothing>()
}