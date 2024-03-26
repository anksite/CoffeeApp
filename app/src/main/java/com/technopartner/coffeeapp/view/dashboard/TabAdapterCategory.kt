package com.technopartner.coffeeapp.view.dashboard

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class TabAdapterCategory(fm: FragmentManager, lifecycle: Lifecycle, val listDate: List<String>): FragmentStateAdapter(fm, lifecycle) {
    val TAG = "PagerAdapterPlan"

    override fun getItemCount(): Int = listDate.size
    override fun createFragment(position: Int): Fragment {
        return Fragment()
    }
}