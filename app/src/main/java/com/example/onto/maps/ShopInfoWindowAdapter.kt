package com.example.onto.maps

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import com.example.onto.R
import com.example.onto.vo.OntoShop
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.Marker
import kotlinx.android.synthetic.main.shop_info_window.view.*

class ShopInfoWindowAdapter(context: Context) : GoogleMap.InfoWindowAdapter {
    lateinit var shop: OntoShop
    private val mWindow = LayoutInflater.from(context).inflate(R.layout.shop_info_window, null)

    private fun redrawWindowText(view: View) {
        view.shop_title.text = shop.name
        view.shop_address.text = shop.address
    }

    override fun getInfoContents(marker: Marker): View {
        redrawWindowText(mWindow)
        return mWindow
    }

    override fun getInfoWindow(marker: Marker): View? {
        redrawWindowText(mWindow)
        return mWindow
    }
}