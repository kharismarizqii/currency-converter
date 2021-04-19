package com.kharismarizqii.currencyconverter

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.jakewharton.rxbinding2.widget.RxTextView
import com.kharismarizqii.currencyconverter.core.data.Resource
import com.kharismarizqii.currencyconverter.core.domain.model.History
import com.kharismarizqii.currencyconverter.core.ui.HistoryAdapter
import com.kharismarizqii.currencyconverter.core.utils.Helper.makeStatusBarTransparent
import com.kharismarizqii.currencyconverter.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.*
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.math.RoundingMode
import java.text.DecimalFormat
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.collections.ArrayList

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: CurrencyViewModel by viewModels()

    private lateinit var binding: ActivityMainBinding
    private lateinit var arrayAdapter: ArrayAdapter<String>
    private lateinit var historyAdapter: HistoryAdapter
    private var currentSpBefore = ""
    private var currentSpAfter = ""

    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        actionBar?.hide()
        makeStatusBarTransparent()

        lifecycle.addObserver(viewModel)
        historyAdapter = HistoryAdapter()
        with(binding.rvHistory) {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = historyAdapter
        }

        val beforeStream = RxTextView.textChanges(binding.etBefore)
            .skipInitialValue()
            .map { query ->
                query.toString()
            }
            .debounce(400, TimeUnit.MILLISECONDS)
            .observeOn(AndroidSchedulers.mainThread())

        beforeStream.subscribe({
            getExchangeCall(it)
        }, {
            Log.e("BeforeStream", "subscribe: ${it.message}")
        })

        eventSpinnerHandling()
        observeSpinner()
        showHistory()
    }

    private fun showHistory() {
        viewModel.history.observe(this, {
            Log.e("MainActivity", "showHistory() $it")
            if (it != null) {
                historyAdapter.setData(it)
            }
        })
    }

    private fun observeSpinner() {
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

    private fun eventSpinnerHandling() {
        binding.spBefore.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                getExchangeCall(binding.etBefore.text.toString())
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

        }

        binding.spAfter.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                getExchangeCall(binding.etBefore.text.toString())
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

        }
    }

    private fun getExchangeCall(it: String?) {
        if (it?.isEmpty() == true || it == null) {
            binding.etAfter.setText("")
        } else {
            viewModel.getExchangeCall(
                binding.spBefore.selectedItem.toString(),
                binding.spAfter.selectedItem.toString()
            ).enqueue(object : Callback<String> {
                @SuppressLint("CheckResult")
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    Log.e("Response", "${response.body()}")
                    if (it == "0") {
                        binding.etAfter.setText("0")
                    } else {
                        var converted = it.toDouble().times(response.body()?.toDouble()!!)
                        val df = DecimalFormat("#.###")
                        df.roundingMode = RoundingMode.CEILING
                        converted = df.format(converted.toBigDecimal()).toDouble()
                        binding.etAfter.setText(converted.toString())
                        val history = History(
                            binding.spBefore.selectedItem.toString(),
                            binding.spAfter.selectedItem.toString(),
                            it.toDouble(),
                            converted
                        )
                        Completable.fromAction({
                            viewModel.insertHistory(history)
                        }).subscribeOn(Schedulers.io())
                            .subscribe()
                        showHistory()
                    }

                    if (currentSpAfter != binding.spAfter.selectedItem.toString()
                        || currentSpBefore != binding.spBefore.selectedItem.toString()
                    ) {
                        currentSpBefore = binding.spBefore.selectedItem.toString()
                        currentSpAfter = binding.spAfter.selectedItem.toString()
                        val df = DecimalFormat("#.####")
                        df.roundingMode = RoundingMode.CEILING
                        val basicCurrency =
                            "1.0 $currentSpBefore - ${df.format(response.body()?.toBigDecimal())} $currentSpAfter"
                        binding.tvBasicCurrency.text = basicCurrency
                    }
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    Log.e("Failure", "${t.message}")
                }

            })

        }

    }
}