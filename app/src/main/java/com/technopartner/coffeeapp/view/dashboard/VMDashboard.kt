package com.technopartner.coffeeapp.view.dashboard

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.technopartner.coffeeapp.api.ApiUtils
import com.technopartner.coffeeapp.api.RepositoryCoffeeApp
import com.technopartner.coffeeapp.api.ResultWrapper
import com.technopartner.coffeeapp.api.model.ResponseHome
import com.technopartner.coffeeapp.api.model.ResponseMenu
import kotlinx.coroutines.launch

class VMDashboard : ViewModel() {
    val TAG = "VMDashboard"

    val mApi = ApiUtils().getApiService()

    private val mDataHome = MutableLiveData<ResponseHome>()
    val dataHome: LiveData<ResponseHome> = mDataHome

    private val mDataMenu = MutableLiveData<ResponseMenu>()
    val dataMenu: LiveData<ResponseMenu> = mDataMenu

    fun home(token: String) {
        viewModelScope.launch {
            val response = RepositoryCoffeeApp().apiHome(mApi, "Bearer $token")

            when (response) {
                is ResultWrapper.Success -> mDataHome.postValue(response.value!!)
                is ResultWrapper.Error -> {
                    Log.d(TAG, response.e.toString())
                }
            }
        }
    }

    fun menu(token: String) {
        viewModelScope.launch {
            val response = RepositoryCoffeeApp().apiMenu(mApi, "Bearer $token", "1")

            when (response) {
                is ResultWrapper.Success -> mDataMenu.postValue(response.value!!)
                is ResultWrapper.Error -> {
                    Log.d(TAG, response.e.toString())
                }
            }
        }
    }

}