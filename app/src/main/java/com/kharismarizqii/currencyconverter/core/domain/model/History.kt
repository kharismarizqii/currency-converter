package com.kharismarizqii.currencyconverter.core.domain.model

data class History(
    val id: Int,
    val fromCode: String,
    val toCode: String,
    val fromValue: Double,
    val toValue: Double
)
