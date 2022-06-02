package com.assessment.mobileengineerassesment.model

import com.squareup.moshi.Json

data class User(
    @Json(name = "accepted_tos")
    val acceptedTos: Boolean,
    @Json(name = "bio")
    val bio: String,
    @Json(name = "first_name")
    val firstName: String,
    @Json(name = "for_hire")
    val forHire: Boolean,
    @Json(name = "id")
    val id: String,
    @Json(name = "instagram_username")
    val instagramUsername: String,
    @Json(name = "last_name")
    val lastName: Any?,
    @Json(name = "links")
    val userLinks: UserLinks,
    @Json(name = "location")
    val location: Any?,
    @Json(name = "name")
    val name: String,
    @Json(name = "portfolio_url")
    val portfolioUrl: String,
    @Json(name = "profile_image")
    val userProfileImage: UserProfileImage,
    @Json(name = "social")
    val social: Social,
    @Json(name = "total_collections")
    val totalCollections: Int,
    @Json(name = "total_likes")
    val totalLikes: Int,
    @Json(name = "total_photos")
    val totalPhotos: Int,
    @Json(name = "twitter_username")
    val twitterUsername: String,
    @Json(name = "updated_at")
    val updatedAt: String,
    @Json(name = "username")
    val username: String
)
