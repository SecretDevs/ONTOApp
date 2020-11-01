package com.example.onto

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.onto.discount.DiscountFragment
import com.example.onto.maps.MapsFragment
import com.example.onto.materials.MaterialsFragment
import com.example.onto.products.ProductsFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import timber.log.Timber

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bottom_navigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.page_map -> {
                    if (LAST_ITEM != R.id.page_map) {
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.fragment_container, MapsFragment())
                            .commitAllowingStateLoss()
                        LAST_ITEM = it.itemId
                        true
                    } else {
                        false
                    }
                }
                R.id.page_discount -> {
                    if (LAST_ITEM != R.id.page_discount) {
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.fragment_container, DiscountFragment())
                            .commitAllowingStateLoss()
                        LAST_ITEM = it.itemId
                        true
                    } else {
                        false
                    }
                }
                R.id.page_shop -> {
                    if (LAST_ITEM != R.id.page_shop) {
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.fragment_container, ProductsFragment())
                            .commitAllowingStateLoss()
                        LAST_ITEM = it.itemId
                        true
                    } else {
                        false
                    }
                }
                R.id.page_articles -> {
                    if (LAST_ITEM != R.id.page_articles) {
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.fragment_container, MaterialsFragment())
                            .commitAllowingStateLoss()
                        LAST_ITEM = it.itemId
                        true
                    } else {
                        false
                    }
                }
                R.id.page_user -> {
                    false
                }
                else -> {
                    Timber.e("Unknown item id ${it.itemId}")
                    false
                }
            }
        }
        if (savedInstanceState == null) {
            bottom_navigation.findViewById<View>(R.id.page_shop).performClick()
        }
    }

    companion object {
        private var LAST_ITEM: Int = 0
    }

}