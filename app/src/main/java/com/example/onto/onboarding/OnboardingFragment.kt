package com.example.onto.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.onto.R
import com.example.onto.utils.getSpannableString
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_onboarding.*


@AndroidEntryPoint
class OnboardingFragment : Fragment() {
    private val viewModel: OnboardingViewModel by viewModels()
    private lateinit var onboardingAdapter: OnboardingAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(
        R.layout.fragment_onboarding,
        container,
        false
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        onboarding_text.text = requireContext().getSpannableString(R.string.onboarding_welcome_text)

        onboardingAdapter = OnboardingAdapter()
        onboarding_info.adapter = onboardingAdapter
        TabLayoutMediator(tab_indicator, onboarding_info) { _, _ -> }.attach()

        val tabStrip = tab_indicator.getChildAt(0) as LinearLayout
        for (i in 0 until tabStrip.childCount) {
            tabStrip.getChildAt(i).setOnTouchListener { _, _ -> true }
        }

        next_screen_btn.setOnClickListener {
            if (onboarding_info.currentItem == 2) {
                viewModel.onSeenOnboarding()
            } else {
                onboarding_info.setCurrentItem(
                    onboarding_info.currentItem + 1,
                    true
                )
            }
        }

        skip_btn.setOnClickListener {
            viewModel.onSeenOnboarding()
        }
        onboarding_info.offscreenPageLimit = 3
    }

}