package com.assessment.mobileengineerassesment.network.model

data class GenericError (
    val status: Throwable?,
    val errors: List<String>
)
