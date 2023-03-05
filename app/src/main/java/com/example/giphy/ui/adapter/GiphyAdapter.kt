package com.example.giphy.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.giphy.R
import com.example.giphy.data.model.GiphyList
import com.example.giphy.databinding.ItemGiphyBinding

class GiphyAdapter(private val listener: OnItemClickListener) :
    PagingDataAdapter<GiphyList.Data, GiphyAdapter.GiphyViewHolder>(
        GIPHY_COMPARATOR
    ) {

    inner class GiphyViewHolder(private val binding: ItemGiphyBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val item = getItem(position)
                    if (item != null) {
                        listener.onItemClick(item.id)
                    }
                }
            }
        }

        fun bind(item: GiphyList.Data) {
            binding.apply {
                Glide.with(itemView).asGif()
                    .load(item.images.fixed_height.url)
                    .into(giphyItemGIF)
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(id: String)
    }

    companion object {
        private val GIPHY_COMPARATOR = object : DiffUtil.ItemCallback<GiphyList.Data>() {
            override fun areItemsTheSame(
                oldItem: GiphyList.Data,
                newItem: GiphyList.Data
            ): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(
                oldItem: GiphyList.Data,
                newItem: GiphyList.Data
            ) = oldItem == newItem

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GiphyViewHolder {
        val binding = ItemGiphyBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GiphyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GiphyViewHolder, position: Int) {
        val currentItem = getItem(position)
        if (currentItem != null) {
            holder.bind(currentItem)
        }
    }
}