package com.assessment.mobileengineerassesment.model

import com.squareup.moshi.Json

data class UserLinks(
    @field:Json(name = "followers")
    val followers: String,
    @field:Json(name = "following")
    val following: String,
    @field:Json(name = "html")
    val html: String,
    @field:Json(name = "likes")
    val likes: String,
    @field:Json(name = "photos")
    val photos: String,
    @field:Json(name = "portfolio")
    val portfolio: String,
    @field:Json(name = "self")
    val self: String
)
