package com.assessment.mobileengineerassesment.model


import com.squareup.moshi.Json

data class Social(
    @field:Json(name = "instagram_username")
    val instagramUsername: String,
    @field:Json(name = "paypal_email")
    val paypalEmail: Any?,
    @field:Json(name = "portfolio_url")
    val portfolioUrl: String,
    @field:Json(name = "twitter_username")
    val twitterUsername: String
)
