package com.example.onto.onboarding

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.example.onto.data.usecase.OnboardingUseCase
import com.example.onto.navigation.Coordinator

class OnboardingViewModel @ViewModelInject constructor(
    private val sharedPreferenceUseCase: OnboardingUseCase,
    private val coordinator: Coordinator
) : ViewModel() {

    fun onSeenOnboarding() {
        sharedPreferenceUseCase.setOnboardingScreenSeen()
        coordinator.navigateToShop()
    }

}