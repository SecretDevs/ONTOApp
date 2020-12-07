package com.example.onto.onboarding

import android.view.View
import com.bumptech.glide.Glide
import com.example.onto.base.recycler.DataViewHolder
import kotlinx.android.synthetic.main.item_onboarding.view.*

class OnboardingViewHolder(
    view: View
) : DataViewHolder<OnboardingItem>(view) {
    private val onboardingText = view.onboarding_text
    private val onboardingImage = view.onboarding_image

    override fun bindData(data: OnboardingItem) {
        onboardingText.text = onboardingText.resources.getString(data.textResource)
        Glide.with(onboardingImage)
            .load(data.imageResource)
            .into(onboardingImage)
    }

    fun clear() {
        Glide.with(onboardingImage).clear(onboardingImage)
    }

}