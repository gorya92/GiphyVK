package com.example.giphy.data.model

data class GiphyDetails(
    val `data`: Data
) {
    data class Data(
        val images: Images,
        val import_datetime: String,
        val title: String,

        )

    data class Images(
        val fixed_height: GiphyList.FixedHeight
    )



}