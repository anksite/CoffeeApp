package com.technopartner.coffeeapp.view.dashboard

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.technopartner.coffeeapp.databinding.ListCategoryBinding
import com.technopartner.coffeeapp.databinding.ListMenuBinding
import com.technopartner.coffeeapp.unil.ToolBatch

class RecyclerMenu(
    private val listDataViewMenu: List<DataViewMenu>,
    val onClickItem: (Int) -> Unit
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    lateinit var mContext: Context
    val TYPE_CATEGORY = 0
    val TYPE_MENU = 1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        mContext = parent.context

        val bindingCategory =
            ListCategoryBinding.inflate(LayoutInflater.from(mContext), parent, false)
        val bindingMenu =
            ListMenuBinding.inflate(LayoutInflater.from(mContext), parent, false)

        return if (viewType == TYPE_CATEGORY) {
            ViewHolderCategory(bindingCategory)
        } else {
            ViewHolderMenu(bindingMenu, mContext)
        }
    }

    override fun getItemCount(): Int = listDataViewMenu.size

    override fun getItemViewType(position: Int): Int {
        return if (listDataViewMenu[position].category.isNullOrEmpty()) {
            TYPE_MENU
        } else TYPE_CATEGORY
    }

    override fun onBindViewHolder(vh: RecyclerView.ViewHolder, position: Int) {
        if (vh.itemViewType == TYPE_CATEGORY) {
            val vhCategory = vh as ViewHolderCategory
            vhCategory.bind(listDataViewMenu[position])
        } else if (vh.itemViewType == TYPE_MENU) {
            val vhMenu = vh as ViewHolderMenu
            vhMenu.bind(listDataViewMenu[position])
            vhMenu.b.root.setOnClickListener { onClickItem(position) }
        }
    }

    class ViewHolderCategory(val b: ListCategoryBinding) : RecyclerView.ViewHolder(b.root) {
        fun bind(dataViewMenu: DataViewMenu) {
            b.tvCategory.text = dataViewMenu.category
        }
    }

    class ViewHolderMenu(val b: ListMenuBinding, val context: Context) : RecyclerView.ViewHolder(b.root) {
        fun bind(dataViewMenu: DataViewMenu) {
            val dataMenu = dataViewMenu.dataMenu!!
            Glide.with(context).load(dataMenu.photo).into(b.ivMenu)
            b.tvMenu.text = dataMenu.name
            b.tvDesc.text = dataMenu.description
            b.tvPrice.text = ToolBatch.formatHarga(dataMenu.price.toLong())
        }
    }
}