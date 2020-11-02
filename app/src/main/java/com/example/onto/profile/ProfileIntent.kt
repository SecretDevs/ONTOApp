package com.example.onto.profile

import com.example.onto.base.MviIntent

sealed class ProfileIntent : MviIntent {

    object InitialIntent : ProfileIntent()

    object ReloadIntent : ProfileIntent()

}