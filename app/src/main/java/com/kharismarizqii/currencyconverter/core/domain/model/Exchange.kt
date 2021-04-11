package com.kharismarizqii.currencyconverter.core.domain.model

data class Exchange(
    val id: String,
    val from: String,
    val to: String,
    val amount: Double
)
