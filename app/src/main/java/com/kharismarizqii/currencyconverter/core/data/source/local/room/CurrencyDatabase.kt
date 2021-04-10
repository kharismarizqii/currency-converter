package com.kharismarizqii.currencyconverter.core.data.source.remote.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.kharismarizqii.currencyconverter.core.data.source.remote.local.entity.CountryCodeEntity

@Database(entities = [CountryCodeEntity::class], version = 1, exportSchema = false)
abstract class CurrencyDatabase: RoomDatabase() {
    abstract fun currencyDao() : CurrencyDao
}