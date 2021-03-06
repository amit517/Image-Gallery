package com.assessment.mobileengineerassesment.model

import androidx.recyclerview.widget.DiffUtil
import com.squareup.moshi.Json

data class ImageResponse(
    @field:Json(name = "color")
    val color: String,
    @field:Json(name = "height")
    val height: Int,
    @field:Json(name = "id")
    val id: String,
    @field:Json(name = "urls")
    val imageUrls: ImageUrls,
    @field:Json(name = "width")
    val width: Int,
) {
    companion object {
        var DIFF_CALLBACK: DiffUtil.ItemCallback<ImageResponse> =
            object : DiffUtil.ItemCallback<ImageResponse>() {
                override fun areItemsTheSame(
                    oldItem: ImageResponse,
                    newItem: ImageResponse,
                ): Boolean {
                    return oldItem.id === newItem.id
                }

                override fun areContentsTheSame(
                    oldItem: ImageResponse,
                    newItem: ImageResponse,
                ): Boolean {
                    return oldItem == newItem
                }
            }
    }
}

