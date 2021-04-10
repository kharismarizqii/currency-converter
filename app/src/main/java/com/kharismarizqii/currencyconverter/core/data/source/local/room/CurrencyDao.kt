package com.kharismarizqii.currencyconverter.core.data.source.remote.local.room

import androidx.room.Dao
import androidx.room.Query
import com.kharismarizqii.currencyconverter.core.data.source.remote.local.entity.CountryCodeEntity
import io.reactivex.Flowable

@Dao
interface CurrencyDao {
    @Query("SELECT * FROM country_code")
    fun getListCode(): Flowable<List<CountryCodeEntity>>
}