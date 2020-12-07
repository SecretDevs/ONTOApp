package com.example.onto.profile

import com.example.onto.base.MviIntent
import com.example.onto.base.NothingIntent

sealed class ProfileIntent : MviIntent {

    object InitialIntent : ProfileIntent()

    object ReloadIntent : ProfileIntent()

    object GoToQuestionIntent : ProfileIntent()

    object GoToCartIntent : ProfileIntent()

    object ProfileNothingIntent : ProfileIntent(), NothingIntent

}