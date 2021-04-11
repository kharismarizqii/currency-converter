package com.kharismarizqii.currencyconverter.core.data.source.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.kharismarizqii.currencyconverter.core.data.source.local.entity.CountryCodeEntity
import com.kharismarizqii.currencyconverter.core.data.source.local.entity.ExchangeEntity
import io.reactivex.Completable
import io.reactivex.Flowable

@Dao
interface CurrencyDao {
    @Query("SELECT * FROM country_code")
    fun getListCode(): Flowable<List<CountryCodeEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertListCode(code: List<CountryCodeEntity>): Completable

    @Query("SELECT * FROM exchange WHERE id = :id")
    fun getExchange(id: String): Flowable<ExchangeEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertExchange(exchange: ExchangeEntity): Completable
}