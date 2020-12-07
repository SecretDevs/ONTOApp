package com.example.onto.onboarding

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.onto.R

class OnboardingAdapter : RecyclerView.Adapter<OnboardingViewHolder>() {
    private val data = listOf(
        OnboardingItem(textResource = R.string.text_intro_first, imageResource = R.drawable.ic_intro_first),
        OnboardingItem(textResource = R.string.text_intro_second, imageResource = R.drawable.ic_intro_second),
        OnboardingItem(textResource = R.string.text_intro_third, imageResource = R.drawable.ic_intro_third),
    )

    override fun getItemCount(): Int = data.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OnboardingViewHolder =
        OnboardingViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_onboarding,
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: OnboardingViewHolder, position: Int) {
        holder.bindData(data[position])
    }

    override fun onViewRecycled(holder: OnboardingViewHolder) {
        super.onViewRecycled(holder)
        holder.clear()
    }

}