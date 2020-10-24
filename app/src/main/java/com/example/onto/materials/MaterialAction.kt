package com.example.onto.materials
import com.example.onto.base.MviAction

sealed class MaterialAction : MviAction {

    object RefreshValues : MaterialAction()

    object InitialValues : MaterialAction()
}