package com.example.onto.materials

import com.example.onto.base.BaseViewModel

class MaterialViewModel: BaseViewModel<MaterialViewState,MaterialEffect,MaterialIntent,MaterialAction>() {

    private val materialRepo = MaterialRepository()

    override fun initialState(): MaterialViewState = MaterialViewState()

    override fun intentInterpreter(intent: MaterialIntent): MaterialAction {
        return when(intent){
            is MaterialIntent.InitialIntent -> MaterialAction.RefreshValues
            is MaterialIntent.RefreshIntent -> MaterialAction.InitialValues
        }
    }

    override fun performAction(action: MaterialAction): MaterialEffect {
       return when(action){
          is MaterialAction.RefreshValues -> {
                val material = materialRepo.updateMaterials()
                MaterialEffect.UpdateMaterials(material)
          }

          is MaterialAction.InitialValues -> {
              val material = materialRepo.updateMaterials()
              MaterialEffect.UpdateMaterials(material)
          }
        }
    }

    override fun stateReducer(oldState: MaterialViewState, effect: MaterialEffect): MaterialViewState {
        return when (effect) {
            is MaterialEffect.UpdateMaterials -> {
                MaterialViewState(effect.materials)
            }
        }
    }

}