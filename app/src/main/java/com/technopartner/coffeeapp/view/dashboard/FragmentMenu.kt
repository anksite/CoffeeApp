package com.technopartner.coffeeapp.view.dashboard

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.technopartner.coffeeapp.api.model.ResponseMenu
import com.technopartner.coffeeapp.databinding.FragmentMenuBinding
import com.technopartner.coffeeapp.unil.DialogLoading


class FragmentMenu : Fragment() {
    val TAG = "FragmentMenu"

    lateinit var b: FragmentMenuBinding
    val vmDashboard: VMDashboard by activityViewModels()
    val mDialogLoading by lazy { DialogLoading(requireContext()) }
    lateinit var mLatestRequest: () -> Unit
    val mListCategory = mutableListOf<DataCategoryIndex>()
    val mListDataViewMenu = mutableListOf<DataViewMenu>()
    lateinit var mAdapter: RecyclerMenu

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        b = FragmentMenuBinding.inflate(inflater, container, false)
        return b.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        vmDashboard.dataMenu.observe(viewLifecycleOwner, ::handleResponse)

        mAdapter = RecyclerMenu(mListDataViewMenu, ::onClickMenu)
        b.rvMenu.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext())
            adapter = mAdapter
        }

        b.tlCategory.addOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab) {
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {

            }

            override fun onTabSelected(tab: TabLayout.Tab) {
                Log.d(TAG, "mListCategory ${mListCategory.size}, ${mListDataViewMenu.size}")
                if(mListCategory.size>0){
                    Log.d(TAG, "scroll to ${mListCategory[tab.position].listIndex}")
                    b.rvMenu.smoothSnapToPosition(mListCategory[tab.position].listIndex)
                }

            }

        })
    }

    fun onClickMenu(position: Int) {
        Toast.makeText(context, "You clicked "+mListDataViewMenu[position].dataMenu?.name, Toast.LENGTH_SHORT).show()
    }

    override fun onResume() {
        super.onResume()
        if (vmDashboard.dataMenu.value == null) {
            val token = (activity as ActivityDashboard).accessToken!!
            doRequest { vmDashboard.menu(token) }
        }
    }

    fun doRequest(function: () -> Unit) {
        mDialogLoading.show()
        mLatestRequest = function
        mLatestRequest.invoke()
    }

    fun handleResponse(response: ResponseMenu) {
        mDialogLoading.cancel()
        Log.d(TAG, response.toString())

        for(category in response.result.categories){
            b.tlCategory.addTab(b.tlCategory.newTab().setText(category.categoryName));
            mListDataViewMenu.add(DataViewMenu(category = category.categoryName))
            mListCategory.add(DataCategoryIndex(category.categoryName, mListDataViewMenu.lastIndex))
            for(menu in category.menu){
                mListDataViewMenu.add(DataViewMenu(dataMenu = menu))
            }
        }

        mAdapter.notifyDataSetChanged()
    }

    fun RecyclerView.smoothSnapToPosition(position: Int, snapMode: Int = LinearSmoothScroller.SNAP_TO_START) {
        val smoothScroller = object : LinearSmoothScroller(this.context) {
            override fun getVerticalSnapPreference(): Int = snapMode
            override fun getHorizontalSnapPreference(): Int = snapMode
        }
        smoothScroller.targetPosition = position
        layoutManager?.startSmoothScroll(smoothScroller)
    }
}
