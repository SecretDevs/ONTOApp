package com.example.onto.maps

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import com.example.onto.R
import com.example.onto.vo.remote.OntoShop
import com.google.android.libraries.maps.GoogleMap
import com.google.android.libraries.maps.model.Marker
import kotlinx.android.synthetic.main.shop_info_window.view.*

class ShopInfoWindowAdapter(context: Context) : GoogleMap.InfoWindowAdapter {
    private val shops = mutableListOf<OntoShop>()
    private val mWindow = LayoutInflater.from(context).inflate(R.layout.shop_info_window, null)

    fun updateShops(newShops: List<OntoShop>) {
        shops.clear()
        shops.addAll(newShops)
    }

    private fun redrawWindowText(marker: Marker) {
        val shop = shops.firstOrNull {
            it.location.latitude == marker.position.latitude &&
                    it.location.longitude == marker.position.longitude
        }
        mWindow.shop_title.text = shop?.name
        mWindow.shop_address.text = shop?.address
    }

    override fun getInfoContents(marker: Marker): View {
        redrawWindowText(marker)
        return mWindow
    }

    override fun getInfoWindow(marker: Marker): View? {
        redrawWindowText(marker)
        return mWindow
    }
}