package com.assessment.mobileengineerassesment.model

import com.squareup.moshi.Json

data class UserProfileImage(
    @Json(name = "large")
    val large: String,
    @Json(name = "medium")
    val medium: String,
    @Json(name = "small")
    val small: String
)
