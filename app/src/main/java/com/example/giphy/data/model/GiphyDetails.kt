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
        val original: Original,
        val fixed_height: GiphyList.FixedHeight
    )


    data class Original(
        val frames: String,
        val hash: String,
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