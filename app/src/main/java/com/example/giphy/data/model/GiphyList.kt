package com.example.giphy.data.model

data class GiphyList(
    val `data`: List<Data>,
) {
    data class Data(
        val id: String,
        val images: Images,
    )

    data class Images(
        val fixed_height: FixedHeight,
        val fixed_height_small : FixedHeightSmall
    )
    data class FixedHeightSmall(
        val height: String,
        val mp4: String,
        val mp4_size: String,
        val size: String,
        val url: String,
        val webp: String,
        val webp_size: String,
        val width: String
    )
    data class FixedHeight(
        val height: String,
        val mp4: String,
        val mp4_size: String,
        val size: String,
        val url: String,
        val webp: String,
        val webp_size: String,
        val width: String
    )
}