package com.kharismarizqii.currencyconverter.core.domain.usecase

import com.kharismarizqii.currencyconverter.core.data.Resource
import com.kharismarizqii.currencyconverter.core.domain.model.CountryCode
import com.kharismarizqii.currencyconverter.core.domain.model.Exchange
import io.reactivex.Flowable
import retrofit2.Call

interface CurrencyUseCase {
    fun getListCode(): Flowable<Resource<List<CountryCode>>>
    fun getExchange(from: String, to: String): Flowable<Resource<Exchange>>
    fun getExchangeCall(from: String, to: String): Call<String>
}