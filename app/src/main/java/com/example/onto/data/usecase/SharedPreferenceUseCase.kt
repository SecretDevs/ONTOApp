package com.example.onto.data.usecase

import android.content.Context
import androidx.core.content.edit
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

interface SharedPreferenceUseCase {
    fun isOnboardingScreenSeen(): Boolean
    fun setOnboardingScreenSeen()
}

class SharedPreferenceUseCaseImpl @Inject constructor(
    @ApplicationContext context: Context
) : SharedPreferenceUseCase {
    private val pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    override fun isOnboardingScreenSeen(): Boolean = pref.getBoolean(ONBOARDING_FIELD, false)

    override fun setOnboardingScreenSeen() {
        pref.edit {
            putBoolean(ONBOARDING_FIELD, true)
        }
    }

    companion object {
        private const val PREF_NAME = "ONTO_PREF"
        private const val ONBOARDING_FIELD = "ONTO_ONBOARDING"
    }

}