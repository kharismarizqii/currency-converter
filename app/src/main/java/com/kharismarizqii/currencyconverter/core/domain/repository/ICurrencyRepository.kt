package com.kharismarizqii.currencyconverter.core.domain.repository

import com.kharismarizqii.currencyconverter.core.data.Resource
import com.kharismarizqii.currencyconverter.core.domain.model.CountryCode
import io.reactivex.Flowable

interface ICurrencyRepository {
    fun getListCode(): Flowable<Resource<List<CountryCode>>>
}