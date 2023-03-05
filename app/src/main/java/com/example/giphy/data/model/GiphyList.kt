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
    )

    data class FixedHeight(
        val url: String,
    )
}