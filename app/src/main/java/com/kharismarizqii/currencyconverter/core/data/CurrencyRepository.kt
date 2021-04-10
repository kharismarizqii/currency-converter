package com.kharismarizqii.currencyconverter.core.data

import com.kharismarizqii.currencyconverter.core.data.source.local.LocalDataSource
import com.kharismarizqii.currencyconverter.core.data.source.remote.RemoteDataSource
import com.kharismarizqii.currencyconverter.core.data.source.remote.network.ApiResponse
import com.kharismarizqii.currencyconverter.core.domain.model.CountryCode
import com.kharismarizqii.currencyconverter.core.domain.repository.ICurrencyRepository
import com.kharismarizqii.currencyconverter.core.utils.AppExecutors
import com.kharismarizqii.currencyconverter.core.utils.DataMapper
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CurrencyRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
): ICurrencyRepository {
    override fun getListCode(): Flowable<Resource<List<CountryCode>>> =
        object : NetworkBoundResource<List<CountryCode>, List<String>>(appExecutors){
            override fun loadFromDB(): Flowable<List<CountryCode>> {
                return localDataSource.getListCode().map {
                    DataMapper.mapEntitiesToDomain(it)
                }
            }

            override fun shouldFetch(data: List<CountryCode>?): Boolean =
                data == null || data.isEmpty()

            override fun createCall(): Flowable<ApiResponse<List<String>>> {
                return remoteDataSource.getListCode()
            }

            override fun saveCallResult(data: List<String>) {
                val codeList = DataMapper.mapResponsesToEntities(data)
                localDataSource.insertListCode(codeList)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe()
            }

        }.asFlowable()

}