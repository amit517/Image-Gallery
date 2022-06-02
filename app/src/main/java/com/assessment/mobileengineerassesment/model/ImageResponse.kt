package com.assessment.mobileengineerassesment.model

import androidx.recyclerview.widget.DiffUtil
import com.squareup.moshi.Json

data class ImageResponse(
    @field:Json(name = "alt_description")
    val altDescription: Any?,
    @field:Json(name = "blur_hash")
    val blurHash: String,
    @field:Json(name = "categories")
    val categories: List<Any>,
    @field:Json(name = "color")
    val color: String,
    @field:Json(name = "created_at")
    val createdAt: String,
    @field:Json(name = "current_user_collections")
    val currentUserCollections: List<Any>,
    @field:Json(name = "description")
    val description: Any?,
    @field:Json(name = "height")
    val height: Int,
    @field:Json(name = "id")
    val id: String,
    @field:Json(name = "liked_by_user")
    val likedByUser: Boolean,
    @field:Json(name = "likes")
    val likes: Int,
    @field:Json(name = "promoted_at")
    val promotedAt: Any?,
    @field:Json(name = "updated_at")
    val updatedAt: String,
    @field:Json(name = "urls")
    val imageUrls: ImageUrls,
    @field:Json(name = "user")
    val user: User,
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

