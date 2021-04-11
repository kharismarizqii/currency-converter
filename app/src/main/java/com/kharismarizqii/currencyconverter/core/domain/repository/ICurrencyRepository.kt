package com.kharismarizqii.currencyconverter.core.domain.repository

import com.kharismarizqii.currencyconverter.core.data.Resource
import com.kharismarizqii.currencyconverter.core.domain.model.CountryCode
import com.kharismarizqii.currencyconverter.core.domain.model.Exchange
import io.reactivex.Flowable

interface ICurrencyRepository {
    fun getListCode(): Flowable<Resource<List<CountryCode>>>
    fun getExchange(from: String, to: String): Flowable<Resource<Exchange>>
}