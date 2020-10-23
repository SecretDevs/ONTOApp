package com.example.onto.maps

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import com.example.onto.ExampleEffect
import com.example.onto.ExampleViewState
import com.example.onto.base.BaseViewModel
import com.example.onto.vo.OntoResponse
import com.example.onto.vo.OntoShop
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.Default
import kotlinx.coroutines.Dispatchers.IO
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MapsViewModel @ViewModelInject constructor(private val repository: MapsRepository) :
    BaseViewModel<MapsViewState, MapsEffect, MapsIntent, MapsAction>() {

    private var viewModelJob = Job()
    private val viewModelScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    override fun initialState(): MapsViewState = MapsViewState()

    override fun intentInterpreter(intent: MapsIntent): MapsAction =
        when (intent) {
            is MapsIntent.InitialIntent -> MapsAction.RetrieveShops
            is MapsIntent.ChooseShopIntent -> MapsAction.RetrieveShops
            is MapsIntent.UserLocationIntent -> MapsAction.FindUserLocation
        }

    override fun performAction(action: MapsAction): MapsEffect =
        when (action) {
            is MapsAction.RetrieveShops -> {
                var ontoShops = ArrayList<OntoShop>()
                viewModelScope.launch {
                    withContext(IO) {
                        ontoShops = repository.getShops().data.shops as ArrayList<OntoShop>
                        MapsEffect.RetrieveShops(ontoShops = ontoShops)
                        Log.d("coroutines example",  "get list of posts = ${ontoShops}")
                    }
                }
                MapsEffect.RetrieveShops(ontoShops = ontoShops)
            }
            is MapsAction.ChooseNextShop -> {
                var ontoShops = ArrayList<OntoShop>()
                viewModelScope.launch {
                    withContext(Dispatchers.IO) {

                        ontoShops = repository.getShops().data.shops as ArrayList<OntoShop>
                        MapsEffect.RetrieveShops(ontoShops = ontoShops)
                        Log.d("coroutines example",  "get list of posts = ${ontoShops}")
                    }
                }
                MapsEffect.RetrieveShops(ontoShops = ontoShops as List<OntoShop>)
            }
            is MapsAction.FindUserLocation -> {
                MapsEffect.FindUserLocation()
            }

        }


    override fun stateReducer(oldState: MapsViewState, effect: MapsEffect): MapsViewState =
        when (effect) {
            is MapsEffect.RetrieveShops -> MapsViewState(
                ontoShopsList = effect.ontoShops,
                currentShop = null
            )
            else -> {MapsViewState(
                ontoShopsList = null,
                currentShop = null
            )}
        }

    fun cancelJob() {
        viewModelJob.cancel()
    }
}