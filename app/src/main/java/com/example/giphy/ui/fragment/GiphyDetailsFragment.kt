package com.example.giphy.ui.fragment

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.giphy.R
import com.example.giphy.databinding.FragmentGiphyDetailesBinding
import com.example.giphy.ui.viewmodel.GiphyDetailsViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class GiphyDetailsFragment : Fragment() {

    private lateinit var binding: FragmentGiphyDetailesBinding

    private val args by navArgs<GiphyDetailsFragmentArgs>()

    private val viewModel: GiphyDetailsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentGiphyDetailesBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setData()
        retryBtnClick()



    }

    private fun setData() {
        if (hasConnection(requireContext())) {
            viewModel.getGiphyDetail(args.movieId).observe(viewLifecycleOwner) {
                binding.apply {
                   setRetryVisible(false)
                    Glide.with(requireActivity()).asGif()
                        .load(it.images.fixed_height.url)
                        .into(imgDetail)
                    giphyDetailsTitle.text = it.title
                    giphyDetailsDateImport.text =
                        getString(R.string.import_datetime) + it.import_datetime
                }
            }
        } else {
            setRetryVisible(true)
        }
    }

    private fun setRetryVisible(bool : Boolean){
        binding.apply {
            giphyDetailsSearchRetryBTN.isVisible = bool
            giphyDetailsSearchErrorTV.isVisible = bool
        }
    }


    private fun retryBtnClick() {
        binding.giphyDetailsSearchRetryBTN.setOnClickListener {
            setData()
        }
    }

    fun hasConnection(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        var wifiInfo = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
        if (wifiInfo != null && wifiInfo.isConnected) {
            return true
        }
        wifiInfo = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
        if (wifiInfo != null && wifiInfo.isConnected) {
            return true
        }
        wifiInfo = cm.activeNetworkInfo
        return wifiInfo != null && wifiInfo.isConnected
    }
}