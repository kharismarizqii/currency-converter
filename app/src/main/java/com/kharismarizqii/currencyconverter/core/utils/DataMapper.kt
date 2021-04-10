package com.kharismarizqii.currencyconverter.core.utils

import com.kharismarizqii.currencyconverter.core.data.source.local.entity.CountryCodeEntity
import com.kharismarizqii.currencyconverter.core.domain.model.CountryCode

object DataMapper {
    fun mapResponsesToEntities(input: List<String>): List<CountryCodeEntity>{
        val list = ArrayList<CountryCodeEntity>()
        input.map{
            val code = CountryCodeEntity(
                code = it
            )
            list.add(code)
        }
        return list
    }

    fun mapEntitiesToDomain(input: List<CountryCodeEntity>): List<CountryCode>{
        val list = ArrayList<CountryCode>()
        input.map {
            val code = CountryCode(
                code = it.code
            )
            list.add(code)
        }
        return list
    }
}