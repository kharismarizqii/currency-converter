package com.kharismarizqii.currencyconverter.di

import com.kharismarizqii.currencyconverter.core.domain.usecase.CurrencyInteractor
import com.kharismarizqii.currencyconverter.core.domain.usecase.CurrencyUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent

@Module
@InstallIn(ApplicationComponent::class)
abstract class AppModule {
    @Binds
    abstract fun provideCurrencyUseCase(currencyInteractor: CurrencyInteractor): CurrencyUseCase
}