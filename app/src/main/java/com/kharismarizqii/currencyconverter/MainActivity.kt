package com.kharismarizqii.currencyconverter

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.jakewharton.rxbinding2.widget.RxTextView
import com.kharismarizqii.currencyconverter.core.data.Resource
import com.kharismarizqii.currencyconverter.core.domain.model.Exchange
import com.kharismarizqii.currencyconverter.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: CurrencyViewModel by viewModels()

    private lateinit var binding: ActivityMainBinding
    private lateinit var arrayAdapter: ArrayAdapter<String>
    private var dataExchange: Exchange? = null

    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val beforeStream = RxTextView.textChanges(binding.etBefore)
            .skipInitialValue()
            .debounce(400, TimeUnit.MILLISECONDS)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map {
                it.toString()
            }

        beforeStream.subscribe {
            if (dataExchange == null) {
                Log.e("DataExchange null", "masuk")
                viewModel.setExchange(binding.spBefore.selectedItem.toString(),
                    binding.spAfter.selectedItem.toString())
                viewModel.exchange.observe(this@MainActivity, { exchange ->
                    Log.e("DataExchange null", "ExchangeData:")
                    if (exchange != null) {
                        when (exchange) {
                            is Resource.Success -> {
                                dataExchange = exchange.data
                                Log.e("DataExchange null", "DataExchange: $dataExchange")
                                val converted =
                                    dataExchange?.amount?.times(binding.etBefore.text.toString().toDouble())
                                binding.etAfter.setText(converted.toString())
                            }
                        }
                    }
                })
            }
            else {
                Log.e("DataExchange not null", "masuk")
                if (binding.spBefore.selectedItem.toString() == dataExchange?.from
                    && binding.spAfter.selectedItem.toString() == dataExchange?.to
                ) {
                    Log.e("DataExchange same", "DataExchange: $dataExchange")
                    val converted =
                        dataExchange?.amount?.times(binding.etBefore.text.toString().toDouble())
                    binding.etAfter.setText(converted.toString())
                } else {
                    viewModel.setExchange(binding.spBefore.selectedItem.toString(),
                        binding.spAfter.selectedItem.toString())
                    viewModel.exchange.observe(this@MainActivity, { exchange ->
                        Log.e("DataExchange not null", "ExchangeData")
                        if (exchange != null) {
                            when (exchange) {
                                is Resource.Success -> {
                                    dataExchange = exchange.data
                                    Log.e("DataExchange not same", "DataExchange: $dataExchange")
                                    val converted =
                                        dataExchange?.amount?.times(binding.etBefore.text.toString().toDouble())
                                    binding.etAfter.setText(converted.toString())
                                }
                            }
                        }
                    })
                }
            }
        }

        viewModel.currency.observe(this, { code ->
            if (code != null) {
                when (code) {
                    is Resource.Success -> {
                        val list = ArrayList<String>()
                        code.data?.map {
                            list.add(it.code)
                        }
                        Log.e("Observe", "List: $list")
                        arrayAdapter = ArrayAdapter<String>(
                            this,
                            android.R.layout.simple_spinner_dropdown_item,
                            list
                        )
                        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

                        binding.apply {
                            spAfter.adapter = arrayAdapter
                            spBefore.adapter = arrayAdapter
                        }
                    }
                    is Resource.Loading -> {
                        Log.e("Observe", "List: loading")

                    }
                    is Resource.Error -> {
                        Log.e("Observe", "List: failed")
                    }

                }
            }

        })
    }
}