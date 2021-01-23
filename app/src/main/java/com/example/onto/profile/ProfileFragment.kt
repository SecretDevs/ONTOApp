package com.example.onto.profile

import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.example.onto.R
import com.example.onto.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.fragment_profile_content.*
import kotlinx.android.synthetic.main.item_error.*
import java.text.DecimalFormat

@AndroidEntryPoint
class ProfileFragment : BaseFragment<ProfileViewState, ProfileIntent>() {
    override val layoutResourceId: Int
        get() = R.layout.fragment_profile
    override val viewModel: ProfileViewModel by viewModels()

    override fun backStackIntent(): ProfileIntent = ProfileIntent.SaveProfileIntent(
        firstName = first_name_edit.text?.toString() ?: "",
        lastName = last_name_edit.text?.toString() ?: "",
        email = email_name_edit.text?.toString() ?: "",
        phone = phone_name_edit.text?.toString() ?: "",
        useForDelivery = ship_address.isChecked,
        city = city_edit.text?.toString() ?: "",
        street = street_edit.text?.toString() ?: "",
        house = house_edit.text?.toString() ?: "",
        building = building_edit.text?.toString() ?: "",
        apartment = apartment_edit.text?.toString() ?: ""
    )

    override fun initialIntent(): ProfileIntent? = ProfileIntent.InitialIntent

    override fun initViews() {
        retry_button.setOnClickListener {
            _intentLiveData.value = ProfileIntent.ReloadIntent
        }
        cart_btn.setOnClickListener {
            _intentLiveData.value = ProfileIntent.GoToCartIntent
        }
        question_btn.setOnClickListener {
            _intentLiveData.value = ProfileIntent.GoToQuestionIntent
        }
    }

    override fun render(viewState: ProfileViewState) {
        loading_view.isVisible = viewState.isInitialLoading
        error_view.isVisible = viewState.initialLoadingError != null
        content_view.isVisible = viewState.user != null
        ship_address.isChecked = viewState.forDelivery

        cart_badge.isVisible =
            viewState.cartInformation != null && viewState.cartInformation.count != 0
        cart_price.isVisible =
            viewState.cartInformation != null && viewState.cartInformation.count != 0
        if (viewState.cartInformation != null) {
            cart_price.text = resources.getString(
                R.string.price_placeholder,
                priceFormat.format(viewState.cartInformation.totalPrice)
            )
            cart_badge.text = viewState.cartInformation.count.toString()
        }

        if (viewState.user != null) {
            pet_type.text = viewState.user.pet.type
            pet_type.isSelected = true
            first_name_edit.setText(viewState.user.firstName)
            last_name_edit.setText(viewState.user.lastName)
            email_name_edit.setText(viewState.user.email)
            phone_name_edit.setText(viewState.user.phone)
            city_edit.setText(viewState.user.address.city)
            house_edit.setText(viewState.user.address.house.toString())
            street_edit.setText(viewState.user.address.street)
            apartment_edit.setText(viewState.user.address.apartment.toString())
        }
    }

    companion object {
        private val priceFormat = DecimalFormat("#.##")
    }

}