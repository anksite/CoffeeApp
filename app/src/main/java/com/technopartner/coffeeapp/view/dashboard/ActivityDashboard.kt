package com.technopartner.coffeeapp.view.dashboard

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.technopartner.coffeeapp.R
import com.technopartner.coffeeapp.databinding.ActivityDashboardBinding
import com.technopartner.coffeeapp.unil.Cons

class ActivityDashboard : AppCompatActivity() {
    val TAG = "ActivityDashboard"
    val b by lazy { ActivityDashboardBinding.inflate(layoutInflater) }
    val accessToken by lazy { intent.getStringExtra(Cons.ACCESS_TOKEN) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(b.root)
        supportActionBar?.hide()

        val navController = findNavController(R.id.navHostFragment)
        NavigationUI.setupWithNavController(b.bottomNavView, navController)
    }
}