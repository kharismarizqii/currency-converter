package com.kharismarizqii.currencyconverter.core.data.source.remote.local

import com.kharismarizqii.currencyconverter.core.data.source.remote.local.entity.CountryCodeEntity
import com.kharismarizqii.currencyconverter.core.data.source.remote.local.room.CurrencyDao
import io.reactivex.Flowable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalDataSource @Inject constructor(private val currencyDao: CurrencyDao) {
    fun getListCode(): Flowable<List<CountryCodeEntity>> = currencyDao.getListCode()
}