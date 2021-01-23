package com.example.onto.order.confirmation

import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.onto.R
import com.example.onto.base.BaseFragment
import com.example.onto.order.confirmation.recycler.OrderProductsAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_order_confirmation.*

@AndroidEntryPoint
class OrderConfirmationFragment :
    BaseFragment<OrderConfirmationViewState, OrderConfirmationIntent>() {
    override val layoutResourceId: Int
        get() = R.layout.fragment_order_confirmation
    override val viewModel: OrderConfirmationViewModel by viewModels()

    private lateinit var adapter: OrderProductsAdapter

    override fun backStackIntent(): OrderConfirmationIntent =
        OrderConfirmationIntent.OrderConfirmationNothingIntent

    override fun initialIntent(): OrderConfirmationIntent? = OrderConfirmationIntent.InitialIntent

    override fun initViews() {
        adapter = OrderProductsAdapter()
        order_confirmation_items.adapter = adapter
        order_confirmation_items.layoutManager = object : LinearLayoutManager(requireContext()) {
            override fun canScrollVertically(): Boolean = false
        }
    }

    override fun render(viewState: OrderConfirmationViewState) {
        order_confirmation_number.text =
            resources.getString(R.string.order_number_text, viewState.orderNumber)
        adapter.updateData(viewState.items)
        order_contact_details_name.text =
            resources.getString(R.string.full_name_text, viewState.firstName, viewState.lastName)
        order_contact_details_email.text = viewState.email
        order_contact_details_phone.text = viewState.phone

        order_delivery_address_title.text = resources.getString(
            when (viewState.deliveryType) {
                3 -> R.string.order_delivery_pickup_title
                2 -> R.string.order_delivery_post_title
                else -> R.string.order_delivery_address_title
            }
        )
        order_delivery_address_text.text =
            when (viewState.deliveryType) {
                3 -> viewState.deliveryAddress[0]
                else -> resources.getString(
                    R.string.order_delivery_address_full,
                    viewState.deliveryAddress[0],
                    viewState.deliveryAddress[1],
                    viewState.deliveryAddress[2],
                    viewState.deliveryAddress[3],
                    viewState.deliveryAddress[4]
                )
            }

        order_commentary_title.isVisible = viewState.commentary.isNotEmpty()
        order_commentary_text.isVisible = viewState.commentary.isNotEmpty()
        order_commentary_text.text = viewState.commentary
    }
}