package com.example.onto

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.onto.discount.DiscountFragment
import com.example.onto.maps.MapsFragment
import com.example.onto.materials.MaterialsFragment
import com.example.onto.navigation.Coordinator
import com.example.onto.products.ProductsFragment
import com.example.onto.profile.ProfileFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var coordinator: Coordinator

    private val bottomNavigationViewVisibilityCallback =
        object : FragmentManager.FragmentLifecycleCallbacks() {
            override fun onFragmentViewCreated(
                fm: FragmentManager,
                f: Fragment,
                v: View,
                savedInstanceState: Bundle?
            ) {
                bottom_navigation.isVisible = bottomNavigationViewFragments.contains(f::class)
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            coordinator.start()
            bottom_navigation.selectedItemId = LAST_ITEM
            supportFragmentManager.registerFragmentLifecycleCallbacks(
                bottomNavigationViewVisibilityCallback,
                false
            )
        }
        bottom_navigation.setOnNavigationItemSelectedListener {
            bottom_navigation.isVisible = true
            when (it.itemId) {
                R.id.page_map -> {
                    if (LAST_ITEM != R.id.page_map) {
                        coordinator.navigateToMap()
                    }
                }
                R.id.page_discount -> {
                    if (LAST_ITEM != R.id.page_discount) {
                        coordinator.navigateToSales()
                    }
                }
                R.id.page_shop -> {
                    if (LAST_ITEM != R.id.page_shop) {
                        coordinator.navigateToShop()
                    }
                }
                R.id.page_articles -> {
                    if (LAST_ITEM != R.id.page_articles) {
                        coordinator.navigateToArticles()
                    }
                }
                R.id.page_user -> {
                    if (LAST_ITEM != R.id.page_user) {
                        coordinator.navigateToProfile()
                    }
                }
                else -> {
                    Timber.e("Unknown item id ${it.itemId}")
                    return@setOnNavigationItemSelectedListener false
                }
            }
            if (LAST_ITEM == it.itemId) {
                false
            } else {
                LAST_ITEM = it.itemId
                true
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        supportFragmentManager.unregisterFragmentLifecycleCallbacks(
            bottomNavigationViewVisibilityCallback
        )
    }

    companion object {
        private var LAST_ITEM: Int = R.id.page_shop
        private val bottomNavigationViewFragments = setOf(
            MapsFragment::class,
            DiscountFragment::class,
            ProductsFragment::class,
            MaterialsFragment::class,
            ProfileFragment::class
        )
    }

}