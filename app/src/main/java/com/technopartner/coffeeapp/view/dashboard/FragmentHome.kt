package com.technopartner.coffeeapp.view.dashboard

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.technopartner.coffeeapp.R
import com.technopartner.coffeeapp.api.model.ResponseHome
import com.technopartner.coffeeapp.databinding.FragmentHomeBinding
import com.technopartner.coffeeapp.databinding.SheetQrBinding
import com.technopartner.coffeeapp.unil.DialogCustom
import com.technopartner.coffeeapp.unil.DialogLoading
import com.technopartner.coffeeapp.unil.ToolBatch
import org.imaginativeworld.whynotimagecarousel.model.CarouselItem


class FragmentHome : Fragment() {
    val TAG = "FragmentHome"

    lateinit var b: FragmentHomeBinding
    val vmDashboard: VMDashboard by activityViewModels()
    val mDialogLoading by lazy { DialogLoading(requireContext()) }
    lateinit var mLatestRequest: () -> Unit
    var mQrCodeUrl = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        b = FragmentHomeBinding.inflate(inflater, container, false)
        return b.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        vmDashboard.dataHome.observe(viewLifecycleOwner, ::handleResponse)

        b.refreshLayout.setOnRefreshListener {
            val token = (activity as ActivityDashboard).accessToken!!
            doRequest { vmDashboard.home(token) }
        }

        b.carousel.registerLifecycle(viewLifecycleOwner)

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            DialogCustom(requireContext())
                .setIcon(R.drawable.dialog_ask)
                .setTitle("Exit")
                .setMessage("Are you sure want to exit?")
                .setTextNegative("Cancel")
                .setTextPositive("Exit")
                .setOnPositiveListener {
                    activity?.finish()
                }.show()
        }

        b.cvQr.setOnClickListener {
            showSheetQr()
        }
    }

    fun showSheetQr() {
        val dialog = BottomSheetDialog(requireContext())
        val binding = SheetQrBinding.inflate(LayoutInflater.from(requireContext()))

        Glide.with(requireContext()).load(mQrCodeUrl).into(binding.ivQr)

        binding.ivClose.setOnClickListener {
            dialog.cancel()
        }

        dialog.setContentView(binding.root)
        dialog.show()
    }

    override fun onResume() {
        super.onResume()
        if (vmDashboard.dataHome.value == null) {
            val token = (activity as ActivityDashboard).accessToken!!
            doRequest { vmDashboard.home(token) }
        }
    }

    fun doRequest(function: () -> Unit) {
        if (!b.refreshLayout.isRefreshing) {
            mDialogLoading.show()
        }
        mLatestRequest = function
        mLatestRequest.invoke()
    }

    fun handleResponse(response: ResponseHome) {
        mDialogLoading.cancel()
        b.refreshLayout.isRefreshing = false
        Log.d(TAG, response.toString())

        mQrCodeUrl = response.result.qrcode
        b.tvName.text = response.result.name
        b.tvSaldo.text = ToolBatch.formatHarga(response.result.saldo.toLong())
        b.tvPoin.text = ToolBatch.formatThousand(response.result.point.toLong())

        val listCarousel = mutableListOf<CarouselItem>()
        repeat(response.result.banner.size){
            listCarousel.add(CarouselItem(imageUrl = response.result.banner[it]))
        }
        b.carousel.setData(listCarousel)

        b.tvViewAll.setOnClickListener {

        }
    }
}
