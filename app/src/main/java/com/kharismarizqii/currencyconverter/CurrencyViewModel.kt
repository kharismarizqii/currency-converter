package com.kharismarizqii.currencyconverter

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.ViewModel
import com.kharismarizqii.currencyconverter.core.domain.usecase.CurrencyUseCase

class CurrencyViewModel @ViewModelInject constructor(currencyUseCase: CurrencyUseCase): ViewModel() {
    private var from: String = ""
    private var to: String = ""
    private val usecase = currencyUseCase
    fun setExchange(from: String, to: String){
        this.from = from
        this.to = to
    }
    val currency = LiveDataReactiveStreams.fromPublisher(currencyUseCase.getListCode())
    val exchange = LiveDataReactiveStreams.fromPublisher(usecase.getExchange(from, to))
}