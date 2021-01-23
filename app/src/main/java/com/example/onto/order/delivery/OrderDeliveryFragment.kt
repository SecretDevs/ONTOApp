package com.example.onto.order.delivery

import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.example.onto.R
import com.example.onto.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_order_delivery.*

@AndroidEntryPoint
class OrderDeliveryFragment : BaseFragment<OrderDeliveryViewState, OrderDeliveryIntent>() {
    override val layoutResourceId: Int
        get() = R.layout.fragment_order_delivery
    override val viewModel: OrderDeliveryViewModel by viewModels()

    override fun backStackIntent(): OrderDeliveryIntent =
        OrderDeliveryIntent.SaveOrderDeliveryIntent(
            deliveryType = when {
                order_delivery_address_radio.isChecked -> 1
                order_delivery_post_radio.isChecked -> 2
                order_delivery_pickup_radio.isChecked -> 3
                else -> -1
            },
            city = when {
                order_delivery_address_radio.isChecked -> address_city_edit.text.toString()
                order_delivery_post_radio.isChecked -> post_city_edit.text.toString()
                order_delivery_pickup_radio.isChecked -> ""
                else -> ""
            },
            street = when {
                order_delivery_address_radio.isChecked -> address_street_edit.text.toString()
                order_delivery_post_radio.isChecked -> post_street_edit.text.toString()
                order_delivery_pickup_radio.isChecked -> ""
                else -> ""
            },
            house = when {
                order_delivery_address_radio.isChecked -> address_house_edit.text.toString()
                order_delivery_post_radio.isChecked -> post_house_edit.text.toString()
                order_delivery_pickup_radio.isChecked -> ""
                else -> ""
            },
            building = when {
                order_delivery_address_radio.isChecked -> address_building_edit.text.toString()
                order_delivery_post_radio.isChecked -> post_building_edit.text.toString()
                order_delivery_pickup_radio.isChecked -> ""
                else -> ""
            },
            apartment = when {
                order_delivery_address_radio.isChecked -> address_apartment_edit.text.toString()
                order_delivery_post_radio.isChecked -> post_apartment_edit.text.toString()
                order_delivery_pickup_radio.isChecked -> ""
                else -> ""
            },
            pickup = when {
                order_delivery_address_radio.isChecked -> ""
                order_delivery_post_radio.isChecked -> ""
                order_delivery_pickup_radio.isChecked -> pickup_points.selectedItem.toString()
                else -> ""
            },
            commentary = when {
                order_delivery_address_radio.isChecked -> address_commentary_edit.text.toString()
                order_delivery_post_radio.isChecked -> post_commentary_edit.text.toString()
                order_delivery_pickup_radio.isChecked -> pickup_commentary_edit.text.toString()
                else -> ""
            }
        )

    override fun initialIntent(): OrderDeliveryIntent? = OrderDeliveryIntent.InitialIntent

    override fun initViews() {
        order_delivery_address_header.setOnClickListener {
            if (order_delivery_address_radio.isChecked) {
                collapseAddress()
            } else {
                expandAddress()
            }
            collapsePost()
            collapsePickup()
        }
        order_delivery_post_header.setOnClickListener {
            if (order_delivery_post_radio.isChecked) {
                collapsePost()
            } else {
                expandPost()
            }
            collapseAddress()
            collapsePickup()
        }
        order_delivery_pickup_header.setOnClickListener {
            if (order_delivery_pickup_radio.isChecked) {
                collapsePickup()
            } else {
                expandPickup()
            }
            collapseAddress()
            collapsePost()
        }
    }

    override fun render(viewState: OrderDeliveryViewState) {
        address_city_edit.setText(viewState.userCity)
        address_street_edit.setText(viewState.userStreet)
        address_house_edit.setText(viewState.userHouse)
        address_building_edit.setText(viewState.userBuilding)
        address_apartment_edit.setText(viewState.userApartment)

        post_city_edit.setText(viewState.userCity)
        post_street_edit.setText(viewState.userStreet)
        post_house_edit.setText(viewState.userHouse)
        post_building_edit.setText(viewState.userBuilding)
        post_apartment_edit.setText(viewState.userApartment)
    }

    private fun expandAddress() {
        order_delivery_address_radio.isChecked = true
        Glide.with(order_delivery_address_toggle)
            .load(R.drawable.ic_baseline_expand_less)
            .into(order_delivery_address_toggle)
        order_delivery_address_form.expand()
    }

    private fun expandPost() {
        order_delivery_post_radio.isChecked = true
        Glide.with(order_delivery_post_toggle)
            .load(R.drawable.ic_baseline_expand_less)
            .into(order_delivery_post_toggle)
        order_delivery_post_form.expand()
    }

    private fun expandPickup() {
        order_delivery_pickup_radio.isChecked = true
        Glide.with(order_delivery_pickup_toggle)
            .load(R.drawable.ic_baseline_expand_less)
            .into(order_delivery_pickup_toggle)
        order_delivery_pickup_form.expand()
        pickup_points.isVisible = true
    }

    private fun collapseAddress() {
        order_delivery_address_radio.isChecked = false
        Glide.with(order_delivery_address_toggle)
            .load(R.drawable.ic_baseline_expand_more)
            .into(order_delivery_address_toggle)
        order_delivery_address_form.collapse()
    }

    private fun collapsePost() {
        order_delivery_post_radio.isChecked = false
        Glide.with(order_delivery_post_toggle)
            .load(R.drawable.ic_baseline_expand_more)
            .into(order_delivery_post_toggle)
        order_delivery_post_form.collapse()
    }

    private fun collapsePickup() {
        order_delivery_pickup_radio.isChecked = false
        Glide.with(order_delivery_pickup_toggle)
            .load(R.drawable.ic_baseline_expand_more)
            .into(order_delivery_pickup_toggle)
        order_delivery_pickup_form.collapse()
        pickup_points.isVisible = false
    }

}