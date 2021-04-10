package com.kharismarizqii.currencyconverter

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.jakewharton.rxbinding2.widget.RxTextView
import com.kharismarizqii.currencyconverter.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

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

        beforeStream.subscribe{
            binding.etAfter.setText(it)
        }
    }
}