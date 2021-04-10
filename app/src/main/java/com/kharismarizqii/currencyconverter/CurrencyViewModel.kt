package com.kharismarizqii.currencyconverter

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.ViewModel
import com.kharismarizqii.currencyconverter.core.domain.usecase.CurrencyUseCase

class CurrencyViewModel @ViewModelInject constructor(currencyUseCase: CurrencyUseCase): ViewModel() {
    val currency = LiveDataReactiveStreams.fromPublisher(currencyUseCase.getListCode())
}