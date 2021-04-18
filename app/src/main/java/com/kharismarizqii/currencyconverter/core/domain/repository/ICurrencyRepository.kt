package com.kharismarizqii.currencyconverter.core.domain.repository

import com.kharismarizqii.currencyconverter.core.data.Resource
import com.kharismarizqii.currencyconverter.core.domain.model.CountryCode
import com.kharismarizqii.currencyconverter.core.domain.model.Exchange
import com.kharismarizqii.currencyconverter.core.domain.model.History
import io.reactivex.Completable
import io.reactivex.Flowable
import retrofit2.Call

interface ICurrencyRepository {
    fun getListCode(): Flowable<Resource<List<CountryCode>>>
    fun getExchange(from: String, to: String): Flowable<Resource<Exchange>>
    fun getExchangeCall(from: String, to: String): Call<String>
    fun getHistories(): Flowable<List<History>>
    fun insertHistory(history: History)
}