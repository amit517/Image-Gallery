package com.assessment.base.network.model

data class GenericError (
    val status: Throwable?,
    val errors: List<String>
)
