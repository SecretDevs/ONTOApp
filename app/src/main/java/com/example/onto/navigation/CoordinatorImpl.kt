package com.example.onto.navigation

import androidx.fragment.app.FragmentActivity
import com.example.onto.R
import com.example.onto.cart.CartFragment
import com.example.onto.data.usecase.OnboardingUseCaseImpl
import com.example.onto.discount.DiscountFragment
import com.example.onto.maps.MapsFragment
import com.example.onto.material.MaterialDetailsFragment
import com.example.onto.materials.MaterialsFragment
import com.example.onto.onboarding.OnboardingFragment
import com.example.onto.order.OrderFragment
import com.example.onto.product.ProductDetailsFragment
import com.example.onto.products.ProductsFragment
import com.example.onto.profile.ProfileFragment
import com.example.onto.question.QuestionFragment
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

interface Coordinator {
    fun navigateToOnboarding()
    fun navigateToMap()
    fun navigateToSales()
    fun navigateToShop()
    fun navigateToArticles()
    fun navigateToProfile()
    fun navigateToSale(saleId: Long)
    fun navigateToProduct(productId: Long)
    fun navigateToArticle(articleId: Long)
    fun navigateToCart()
    fun navigateToQuestion()
    fun navigateToOrderHistory()
    fun navigateToOrder()
    fun start()
    fun pop()
}

@ActivityScoped
class CoordinatorImpl @Inject constructor(
    @ActivityScoped activity: FragmentActivity
) : Coordinator {
    private val fragmentManager = activity.supportFragmentManager
    private val preferenceUseCase = OnboardingUseCaseImpl(activity)

    override fun navigateToOnboarding() {
        fragmentManager.beginTransaction()
            .replace(R.id.fragment_container, OnboardingFragment())
            .commitAllowingStateLoss()
    }

    override fun navigateToMap() {
        clearBackStack()
        fragmentManager.beginTransaction()
            .replace(R.id.fragment_container, MapsFragment())
            .commitAllowingStateLoss()
    }

    override fun navigateToSales() {
        clearBackStack()
        fragmentManager.beginTransaction()
            .replace(R.id.fragment_container, DiscountFragment())
            .commitAllowingStateLoss()
    }

    override fun navigateToShop() {
        clearBackStack()
        fragmentManager.beginTransaction()
            .replace(R.id.fragment_container, ProductsFragment())
            .commitAllowingStateLoss()
    }

    override fun navigateToArticles() {
        clearBackStack()
        fragmentManager.beginTransaction()
            .replace(R.id.fragment_container, MaterialsFragment())
            .commitAllowingStateLoss()
    }

    override fun navigateToProfile() {
        clearBackStack()
        fragmentManager.beginTransaction()
            .replace(R.id.fragment_container, ProfileFragment())
            .commitAllowingStateLoss()
    }

    override fun navigateToSale(saleId: Long) {
        fragmentManager.beginTransaction()
            .replace(R.id.fragment_container, ProductDetailsFragment.newInstance(saleId))
            .addToBackStack(ProductDetailsFragment::class.java.name)
            .commitAllowingStateLoss()
    }

    override fun navigateToProduct(productId: Long) {
        fragmentManager.beginTransaction()
            .replace(R.id.fragment_container, ProductDetailsFragment.newInstance(productId))
            .addToBackStack(ProductDetailsFragment::class.java.name)
            .commitAllowingStateLoss()
    }

    override fun navigateToArticle(articleId: Long) {
        fragmentManager.beginTransaction()
            .replace(R.id.fragment_container, MaterialDetailsFragment.newInstance(articleId))
            .addToBackStack(MaterialDetailsFragment::class.java.name)
            .commitAllowingStateLoss()
    }

    override fun navigateToCart() {
        fragmentManager.beginTransaction()
            .replace(R.id.fragment_container, CartFragment.newInstance())
            .addToBackStack(CartFragment::class.java.name)
            .commitAllowingStateLoss()
    }

    override fun navigateToQuestion() {
        fragmentManager.beginTransaction()
            .replace(R.id.fragment_container, QuestionFragment.newInstance())
            .addToBackStack(QuestionFragment::class.java.name)
            .commitAllowingStateLoss()
    }

    override fun navigateToOrderHistory() {

    }

    override fun navigateToOrder() {
        fragmentManager.beginTransaction()
            .replace(R.id.fragment_container, OrderFragment.newInstance())
            .addToBackStack(OrderFragment::class.java.name)
            .commitAllowingStateLoss()
    }

    override fun start() {
        if (preferenceUseCase.isOnboardingScreenSeen()) {
            navigateToShop()
        } else {
            navigateToOnboarding()
        }
    }

    override fun pop() {
        fragmentManager.popBackStack()
    }

    private fun clearBackStack() {
        repeat(fragmentManager.backStackEntryCount) {
            fragmentManager.popBackStack()
        }
    }

}