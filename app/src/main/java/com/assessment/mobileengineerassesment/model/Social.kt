package com.assessment.mobileengineerassesment.model


import com.squareup.moshi.Json

data class Social(
    @Json(name = "instagram_username")
    val instagramUsername: String,
    @Json(name = "paypal_email")
    val paypalEmail: Any?,
    @Json(name = "portfolio_url")
    val portfolioUrl: String,
    @Json(name = "twitter_username")
    val twitterUsername: String
)
