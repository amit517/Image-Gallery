package com.assessment.mobileengineerassesment.model

import com.squareup.moshi.Json

data class UserLinks(
    @Json(name = "followers")
    val followers: String,
    @Json(name = "following")
    val following: String,
    @Json(name = "html")
    val html: String,
    @Json(name = "likes")
    val likes: String,
    @Json(name = "photos")
    val photos: String,
    @Json(name = "portfolio")
    val portfolio: String,
    @Json(name = "self")
    val self: String
)
