package com.example.onto

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.MutableLiveData
import com.example.onto.base.BaseActivity
import kotlinx.android.synthetic.main.activity_example.*

class ExampleActivity : BaseActivity<ExampleViewState, ExampleIntent>() {
    private val firstCounterIntents = MutableLiveData<ExampleIntent>()
    private val secondCounterIntents = MutableLiveData<ExampleIntent>()
    private val otherIntents = MutableLiveData<ExampleIntent>().also {
        it.value = ExampleIntent.InitialIntent
    }

    override val layoutResourceId: Int = R.layout.activity_example

    override val viewModel: ExampleViewModel by viewModels()

    override fun initViews() {
        add_one_first_button.setOnClickListener {
            firstCounterIntents.value = ExampleIntent.AddOneFirstIntent
        }
        add_five_first_button.setOnClickListener {
            firstCounterIntents.value = ExampleIntent.AddFiveFirstIntent
        }
        add_one_second_button.setOnClickListener {
            secondCounterIntents.value = ExampleIntent.AddOneSecondIntent
        }
        add_five_second_button.setOnClickListener {
            secondCounterIntents.value = ExampleIntent.AddFiveSecondIntent
        }
        clear_counters.setOnClickListener {
            otherIntents.value = ExampleIntent.ClearCountersIntent
        }

        _intentLiveData.addSource(firstCounterIntents) {
            _intentLiveData.value = it
        }
        _intentLiveData.addSource(secondCounterIntents) {
            _intentLiveData.value = it
        }
        _intentLiveData.addSource(otherIntents) {
            _intentLiveData.value = it
        }
    }

    override fun render(viewState: ExampleViewState) {
        first_counter.text = viewState.firstCounter.toString()
        second_counter.text = viewState.secondCounter.toString()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        examplePrefs = getSharedPreferences("example", Context.MODE_PRIVATE)
    }

    override fun onDestroy() {
        super.onDestroy()
        examplePrefs = null
    }

    companion object {
        var examplePrefs: SharedPreferences? = null
    }

}