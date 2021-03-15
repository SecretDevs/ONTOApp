package com.example.onto.data.usecase

import android.content.Context
import androidx.core.content.edit
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

interface OnboardingUseCase {
    fun isOnboardingScreenSeen(): Boolean
    fun setOnboardingScreenSeen()
}

class OnboardingUseCaseImpl @Inject constructor(
    @ApplicationContext context: Context
) : OnboardingUseCase {
    private val pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    override fun isOnboardingScreenSeen(): Boolean = pref.getBoolean(ONBOARDING_FIELD, false)

    override fun setOnboardingScreenSeen() {
        pref.edit {
            putBoolean(ONBOARDING_FIELD, true)
        }
    }

    companion object {
        private const val PREF_NAME = "ONTO_ONBOARDING_PREF"
        private const val ONBOARDING_FIELD = "ONTO_ONBOARDING"
    }

}