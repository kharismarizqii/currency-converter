package com.kharismarizqii.currencyconverter.core.data.source.remote.network

import io.reactivex.Flowable
import retrofit2.http.GET
import retrofit2.http.Headers

interface ApiService {
    @GET("listquotes")
    fun getListCode(): Flowable<List<String>>
}