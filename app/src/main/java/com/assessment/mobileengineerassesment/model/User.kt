package com.assessment.mobileengineerassesment.model

import com.squareup.moshi.Json

data class User(
    @field:Json(name = "accepted_tos")
    val acceptedTos: Boolean,
    @field:Json(name = "bio")
    val bio: String,
    @field:Json(name = "first_name")
    val firstName: String,
    @field:Json(name = "for_hire")
    val forHire: Boolean,
    @field:Json(name = "id")
    val id: String,
    @field:Json(name = "instagram_username")
    val instagramUsername: String,
    @field:Json(name = "last_name")
    val lastName: Any?,
    @field:Json(name = "links")
    val userLinks: UserLinks,
    @field:Json(name = "location")
    val location: Any?,
    @field:Json(name = "name")
    val name: String,
    @field:Json(name = "portfolio_url")
    val portfolioUrl: String,
    @field:Json(name = "profile_image")
    val userProfileImage: UserProfileImage,
    @field:Json(name = "social")
    val social: Social,
    @field:Json(name = "total_collections")
    val totalCollections: Int,
    @field:Json(name = "total_likes")
    val totalLikes: Int,
    @field:Json(name = "total_photos")
    val totalPhotos: Int,
    @field:Json(name = "twitter_username")
    val twitterUsername: String,
    @field:Json(name = "updated_at")
    val updatedAt: String,
    @field:Json(name = "username")
    val username: String
)
