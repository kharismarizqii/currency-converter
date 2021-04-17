package com.kharismarizqii.currencyconverter.core.data.source.local

import com.kharismarizqii.currencyconverter.core.data.source.local.entity.CountryCodeEntity
import com.kharismarizqii.currencyconverter.core.data.source.local.entity.ExchangeEntity
import com.kharismarizqii.currencyconverter.core.data.source.local.entity.HistoryEntity
import com.kharismarizqii.currencyconverter.core.data.source.local.room.CurrencyDao
import io.reactivex.Completable
import io.reactivex.Flowable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalDataSource @Inject constructor(private val currencyDao: CurrencyDao) {
    fun getListCode(): Flowable<List<CountryCodeEntity>> = currencyDao.getListCode()
    fun insertListCode(code: List<CountryCodeEntity>) = currencyDao.insertListCode(code)

    fun getExchange(id: String): Flowable<ExchangeEntity> = currencyDao.getExchange(id)
    fun insertExchange(exchange: ExchangeEntity) = currencyDao.insertExchange(exchange)

    fun getHistories(): Flowable<List<HistoryEntity>> = currencyDao.getHistories()
    fun insertHistory(historyEntity: HistoryEntity): Completable {
        return currencyDao.insertHistory(historyEntity)
    }
}