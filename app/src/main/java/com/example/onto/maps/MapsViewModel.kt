package com.example.onto.maps

import androidx.hilt.lifecycle.ViewModelInject
import com.example.onto.base.BaseViewModel

class MapsViewModel @ViewModelInject constructor(private val repository: MapsRepository) :
    BaseViewModel<MapsViewState, MapsEffect, MapsIntent, MapsAction>() {

    override fun initialState(): MapsViewState = MapsViewState()

    override fun intentInterpreter(intent: MapsIntent): MapsAction =
        when (intent) {
            is MapsIntent.InitialIntent -> MapsAction.RetrieveShops
        }

    override fun performAction(action: MapsAction): MapsEffect =
        when (action) {
            is MapsAction.RetrieveShops -> {
                val ontoShops = repository.getShops().value?.data
                MapsEffect.RetrieveShops(ontoShops = ontoShops!!)
            }
            is MapsAction.ChooseNextShop -> {
                val ontoShops = repository.getShops().value?.data
                MapsEffect.RetrieveShops(ontoShops = ontoShops!!)
            }
            is MapsAction.MyLocation -> {
                val ontoShops = repository.getShops().value?.data
                MapsEffect.RetrieveShops(ontoShops = ontoShops!!)
            }

        }


    override fun stateReducer(oldState: MapsViewState, effect: MapsEffect): MapsViewState {
        TODO("Not yet implemented")
    }
}