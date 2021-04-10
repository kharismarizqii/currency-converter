package com.kharismarizqii.currencyconverter.core.di

import com.kharismarizqii.currencyconverter.core.data.CurrencyRepository
import com.kharismarizqii.currencyconverter.core.domain.repository.ICurrencyRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent

@Module(includes = [NetworkModule::class, DatabaseModule::class])
@InstallIn(ApplicationComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun provideRepository(currencyRepository: CurrencyRepository): ICurrencyRepository
}