package com.kharismarizqii.currencyconverter

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.jakewharton.rxbinding2.widget.RxTextView
import com.kharismarizqii.currencyconverter.core.data.Resource
import com.kharismarizqii.currencyconverter.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: CurrencyViewModel by viewModels()

    private lateinit var binding: ActivityMainBinding
    private lateinit var arrayAdapter: ArrayAdapter<String>

    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val beforeStream = RxTextView.textChanges(binding.etBefore)
            .skipInitialValue()
            .map {
                it.toString()
            }

        beforeStream.subscribe {
            binding.etAfter.setText(it)
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