package com.example.onto.profile

import com.example.onto.base.MviAction

sealed class ProfileAction : MviAction {

    object LoadProfileAction : ProfileAction()

}