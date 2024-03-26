package com.technopartner.coffeeapp.view.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import com.technopartner.coffeeapp.view.dashboard.ActivityDashboard
import com.technopartner.coffeeapp.unil.Cons
import com.technopartner.coffeeapp.unil.DialogLoading
import com.technopartner.coffeeapp.api.model.ResponseLogin
import com.technopartner.coffeeapp.databinding.ActivityLoginBinding

class ActivityLogin : AppCompatActivity() {
    val TAG = "ActivityLogin"
    val b by lazy { ActivityLoginBinding.inflate(layoutInflater) }
    val vmLogin: VMLogin by viewModels()
    val mDialogLoading by lazy { DialogLoading(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(b.root)

        vmLogin.responseLogin.observe(this, ::handleResponse)

        b.bLogin.setOnClickListener {
            if(b.etEmail.text.length ==0 || b.etPassword.text.length==0){
                Toast.makeText(this, "Please fill Username or Password!", Toast.LENGTH_SHORT).show()
            } else {
                val username = b.etEmail.text.toString()
                val password = b.etPassword.text.toString()
                vmLogin.login(username, password)
                mDialogLoading.show()
            }
        }
    }

    fun handleResponse(responseLogin: ResponseLogin) {
        Log.d(TAG, responseLogin.toString())
        mDialogLoading.cancel()
        Intent(this, ActivityDashboard::class.java).also {
            it.putExtra(Cons.ACCESS_TOKEN, responseLogin.accessToken)
            startActivity(it)
            finish()
        }
    }
}