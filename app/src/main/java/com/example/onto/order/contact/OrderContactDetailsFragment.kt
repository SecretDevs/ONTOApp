package com.example.onto.order.contact

import androidx.fragment.app.viewModels
import com.example.onto.R
import com.example.onto.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_order_contact_details.*

@AndroidEntryPoint
class OrderContactDetailsFragment :
    BaseFragment<OrderContactDetailsViewState, OrderContactDetailsIntent>() {
    override val layoutResourceId: Int = R.layout.fragment_order_contact_details
    override val viewModel: OrderContactDetailsViewModel by viewModels()

    override fun backStackIntent(): OrderContactDetailsIntent =
        OrderContactDetailsIntent.SaveOrderContactDetailsIntent(
            firstName = first_name_edit.text?.toString() ?: "",
            lastName = last_name_edit.text?.toString() ?: "",
            email = email_name_edit.text?.toString() ?: "",
            phone = phone_name_edit.text?.toString() ?: ""
        )

    override fun initialIntent(): OrderContactDetailsIntent? =
        OrderContactDetailsIntent.InitialIntent

    override fun initViews() {

    }

    override fun render(viewState: OrderContactDetailsViewState) {
        if (first_name_edit.text.isNullOrEmpty()) {
            first_name_edit.setText(viewState.firstName)
        }
        if (last_name_edit.text.isNullOrEmpty()) {
            last_name_edit.setText(viewState.lastName)
        }
        if (email_name_edit.text.isNullOrEmpty()) {
            email_name_edit.setText(viewState.email)
        }
        if (phone_name_edit.text.isNullOrEmpty()) {
            phone_name_edit.setText(viewState.phone)
        }
    }
}