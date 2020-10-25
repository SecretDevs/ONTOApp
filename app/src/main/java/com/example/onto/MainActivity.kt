package com.example.onto

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.onto.maps.MapsFragment
import com.example.onto.materials.MaterialsFragment
import com.example.onto.products.ProductsFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if(savedInstanceState == null) { // initial transaction should be wrapped like this
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, MaterialsFragment())
                .commitAllowingStateLoss()
        }

    }
}