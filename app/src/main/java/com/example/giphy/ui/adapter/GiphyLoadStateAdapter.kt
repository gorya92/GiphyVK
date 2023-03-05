package com.example.giphy.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.giphy.databinding.FooterGiphyLoadStateBinding

class GiphyLoadStateAdapter(private val retry: () -> Unit) :
    LoadStateAdapter<GiphyLoadStateAdapter.LoadStateViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadStateViewHolder {
        val binding = FooterGiphyLoadStateBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return LoadStateViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    inner class LoadStateViewHolder(private val binding: FooterGiphyLoadStateBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.giphyLoadStateRetryBTN.setOnClickListener {
                retry.invoke()
            }
        }

        fun bind(loadState: LoadState) {
            binding.apply {
                giphyLoadStateProgressBar.isVisible = loadState is LoadState.Loading
                giphyLoadStateRetryBTN.isVisible = loadState is LoadState.Loading
                giphyLoadStateErrorTV.isVisible = loadState is LoadState.Loading

            }
        }
    }

}