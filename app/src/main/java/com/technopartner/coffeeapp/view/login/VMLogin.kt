package com.technopartner.coffeeapp.view.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.technopartner.coffeeapp.api.ApiUtils
import com.technopartner.coffeeapp.api.RepositoryCoffeeApp
import com.technopartner.coffeeapp.api.ResultWrapper
import com.technopartner.coffeeapp.api.model.ResponseLogin
import kotlinx.coroutines.launch

class VMLogin : ViewModel() {
    val TAG = "VMLogin"
    val mApi = ApiUtils().getApiService()

    private val mResponseLogin = MutableLiveData<ResponseLogin>()
    val responseLogin: LiveData<ResponseLogin> = mResponseLogin

    val grantType = "password"
    val clientSecret = "0a40f69db4e5fd2f4ac65a090f31b823"
    val clientId = "e78869f77986684a"

    fun login(
        username: String,
        password: String,
    ) {
        viewModelScope.launch {
            val response = RepositoryCoffeeApp().oauthToken(
                mApi, grantType, clientSecret, clientId, username, password
            )

            when (response) {
                is ResultWrapper.Success -> mResponseLogin.postValue(response.value!!)
                is ResultWrapper.Error -> {
                    Log.d(TAG, "Error ${response.e}")
                }
            }
        }
    }

}