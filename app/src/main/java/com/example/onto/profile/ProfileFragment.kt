package com.example.onto.profile

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.example.onto.R
import com.example.onto.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.fragment_profile_content.*
import kotlinx.android.synthetic.main.item_error.*

@AndroidEntryPoint
class ProfileFragment : BaseFragment<ProfileViewState, ProfileIntent>() {
    override val layoutResourceId: Int
        get() = R.layout.fragment_profile
    override val viewModel: ProfileViewModel by viewModels()

    override fun initViews() {
        retry_button.setOnClickListener {
            _intentLiveData.value = ProfileIntent.ReloadIntent
        }
    }

    override fun render(viewState: ProfileViewState) {
        loading_view.isVisible = viewState.isInitialLoading
        error_view.isVisible = viewState.initialLoadingError != null
        content_view.isVisible = viewState.user != null

        if (viewState.user != null) {
            pet_type.text = viewState.user.pet.type
            pet_type.isSelected = true
            pet_name_edit.setText(viewState.user.pet.name)
            first_name_edit.setText(viewState.user.firstName)
            last_name_edit.setText(viewState.user.lastName)
            email_name_edit.setText(viewState.user.email)
            phone_name_edit.setText(viewState.user.phone)
            address_name_edit.setText("${viewState.user.address.city}, ${viewState.user.address.street}, ${viewState.user.address.house}, ${viewState.user.address.apartment}")
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (savedInstanceState == null) {
            _intentLiveData.value = ProfileIntent.InitialIntent
        }
    }
}