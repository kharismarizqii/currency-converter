package com.kharismarizqii.currencyconverter.core.domain.usecase

import com.kharismarizqii.currencyconverter.core.data.Resource
import com.kharismarizqii.currencyconverter.core.domain.model.CountryCode
import com.kharismarizqii.currencyconverter.core.domain.repository.ICurrencyRepository
import io.reactivex.Flowable
import javax.inject.Inject

class CurrencyInteractor @Inject constructor(private val currencyRepository: ICurrencyRepository): CurrencyUseCase {
    override fun getListCode(): Flowable<Resource<List<CountryCode>>> = currencyRepository.getListCode()
}