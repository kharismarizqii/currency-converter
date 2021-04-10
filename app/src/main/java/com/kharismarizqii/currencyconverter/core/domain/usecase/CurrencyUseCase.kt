package com.kharismarizqii.currencyconverter.core.domain.usecase

import com.kharismarizqii.currencyconverter.core.data.Resource
import com.kharismarizqii.currencyconverter.core.domain.model.CountryCode
import io.reactivex.Flowable

interface CurrencyUseCase {
    fun getListCode(): Flowable<Resource<List<CountryCode>>>
}