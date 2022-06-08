package com.assessment.mobileengineerassesment.model

import android.os.Parcelable
import androidx.recyclerview.widget.DiffUtil
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

@Parcelize
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
    @field:Json(name = "blur_hash")
    val blurHash: String,
) : Parcelable {
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

