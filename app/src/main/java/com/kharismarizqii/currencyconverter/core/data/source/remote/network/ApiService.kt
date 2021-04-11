package com.kharismarizqii.currencyconverter.core.data.source.remote.network

import io.reactivex.Flowable
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface ApiService {
    @GET("listquotes")
    fun getListCode(): Flowable<List<String>>

    @GET("exchange")
    fun getExchange(
        @Query("from") from: String,
        @Query("to") to: String
    ): Flowable<Double>
}