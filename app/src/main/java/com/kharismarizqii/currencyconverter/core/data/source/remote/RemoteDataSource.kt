package com.kharismarizqii.currencyconverter.core.data.source.remote

import android.annotation.SuppressLint
import android.util.Log
import com.kharismarizqii.currencyconverter.core.data.source.remote.network.ApiResponse
import com.kharismarizqii.currencyconverter.core.data.source.remote.network.ApiService
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteDataSource @Inject constructor(private val apiService: ApiService) {
    @SuppressLint("CheckResult")
    fun getListCode(): Flowable<ApiResponse<List<String>>>{
        val resultData = PublishSubject.create<ApiResponse<List<String>>>()

        val client = apiService.getListCode()

        client.subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .take(1)
            .subscribe({
                resultData.onNext(if (it.isNotEmpty()) ApiResponse.Success(it) else ApiResponse.Empty)
            }, { error ->
                resultData.onNext(ApiResponse.Error(errorMessage = error.message.toString() ))
                Log.e("RemoteDataSource", error.toString())
            })

        return resultData.toFlowable(BackpressureStrategy.BUFFER)
    }
}