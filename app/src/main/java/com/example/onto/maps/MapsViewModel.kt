package com.example.onto.maps

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import com.example.onto.base.BaseViewModel
import com.example.onto.vo.OntoArticle
import com.example.onto.vo.OntoShop

class MapsViewModel @ViewModelInject constructor(private val repository: MapsRepository) :
    BaseViewModel<MapsViewState, MapsEffect, MapsIntent, MapsAction>() {

    override fun initialState(): MapsViewState = MapsViewState()

    override fun intentInterpreter(intent: MapsIntent): MapsAction =
        when (intent) {
            is MapsIntent.InitialIntent -> MapsAction.RetrieveShops
            is MapsIntent.ChooseShopIntent -> MapsAction.RetrieveShops
            is MapsIntent.UserLocationIntent -> MapsAction.FindUserLocation
        }

    override suspend fun performAction(action: MapsAction): MapsEffect =
        when (action) {
            is MapsAction.RetrieveShops -> {
                val ontoShops = repository.getShops().data.shops
                Log.d("coroutines example",  "get list of posts = ${ontoShops}")
                MapsEffect.RetrieveShops(ontoShops = ArrayList<OntoShop>())
            }
            is MapsAction.ChooseNextShop -> {
                val ontoShops = repository.getShops().data.shops
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
}