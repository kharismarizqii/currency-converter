package com.kharismarizqii.currencyconverter

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.jakewharton.rxbinding2.widget.RxTextView
import com.kharismarizqii.currencyconverter.core.data.Resource
import com.kharismarizqii.currencyconverter.core.domain.model.Exchange
import com.kharismarizqii.currencyconverter.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: CurrencyViewModel by viewModels()

    private lateinit var binding: ActivityMainBinding
    private lateinit var arrayAdapter: ArrayAdapter<String>
    private var currentSpBefore = ""
    private var currentSpAfter = ""

    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        lifecycle.addObserver(viewModel)

        val beforeStream = RxTextView.textChanges(binding.etBefore)
            .skipInitialValue()
            .map { query ->
                query.toString()
            }
            .debounce(400, TimeUnit.MILLISECONDS)
            .observeOn(AndroidSchedulers.mainThread())

        beforeStream.subscribe({
            getExchangeCall(it)
        },{
            Log.e("BeforeStream", "subscribe: ${it.message}")
        })

        eventSpinnerHandling()

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

        binding.spAfter.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                getExchangeCall(binding.etBefore.text.toString())
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

        }
    }

    private fun getExchangeCall(it: String?){

        viewModel.getExchangeCall(binding.spBefore.selectedItem.toString(), binding.spAfter.selectedItem.toString()).enqueue(object : Callback<String>{
            override fun onResponse(call: Call<String>, response: Response<String>) {
                Log.e("Response", "${response.body()}")
                if (it=="0"){
                    binding.etAfter.setText("0")
                } else {
                    val converted = it?.toDouble()?.times(response.body()?.toDouble()!!)
                    binding.etAfter.setText(converted.toString())
                }

                if (currentSpAfter!=binding.spAfter.selectedItem.toString()
                    || currentSpBefore!=binding.spBefore.selectedItem.toString()){
                    currentSpBefore = binding.spBefore.selectedItem.toString()
                    currentSpAfter = binding.spAfter.selectedItem.toString()
                    binding.tvBasicCurrency.text = "1 $currentSpBefore - ${response.body()} $currentSpAfter"
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                Log.e("Failure", "${t.message}")
            }

        })
    }
}