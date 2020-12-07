package com.example.onto.onboarding

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.example.onto.data.usecase.SharedPreferenceUseCase
import com.example.onto.navigation.Coordinator

class OnboardingViewModel @ViewModelInject constructor(
    private val sharedPreferenceUseCase: SharedPreferenceUseCase,
    private val coordinator: Coordinator
) : ViewModel() {

    fun onSeenOnboarding() {
        sharedPreferenceUseCase.setOnboardingScreenSeen()
        coordinator.navigateToShop()
    }

}