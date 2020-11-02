package com.example.onto.product

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.example.onto.R
import com.example.onto.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_product_details.*
import kotlinx.android.synthetic.main.fragment_product_details_content.*
import kotlinx.android.synthetic.main.item_error.*
import java.text.DecimalFormat

@AndroidEntryPoint
class ProductDetailsFragment : BaseFragment<ProductDetailsViewState, ProductDetailsIntent>() {
    override val layoutResourceId: Int
        get() = R.layout.fragment_product_details
    override val viewModel: ProductDetailsViewModel by viewModels()

    override fun initViews() {
        arrow_back_btn.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }
        retry_button.setOnClickListener {
            _intentLiveData.value = ProductDetailsIntent.ReloadIntent(getProductId())
        }
        add_to_cart_btn.setOnClickListener {

        }
    }

    override fun render(viewState: ProductDetailsViewState) {
        loading_view.isVisible = viewState.isInitialLoading
        error_view.isVisible = viewState.initialLoadingError != null
        product_details_content.isVisible = viewState.product != null
        add_to_cart_layout.isVisible = viewState.product != null

        if (viewState.product != null) {
            Glide.with(product_details_image)
                .load(viewState.product.image)
                .into(product_details_image)
            appbar_title.text = viewState.product.name
            product_details_info.text = viewState.product.info
            product_details_price.text =
                resources.getString(
                    R.string.price_placeholder,
                    priceFormat.format(viewState.product.price)
                )
            product_details_description.text = viewState.product.description
            add_to_cart_btn.isEnabled = viewState.product.isInStock
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (savedInstanceState == null) {
            _intentLiveData.value =
                ProductDetailsIntent.InitialIntent(getProductId())
        }
    }

    private fun getProductId() = arguments?.getLong(PRODUCT_ID_KEY) ?: 0L

    companion object {
        private val priceFormat = DecimalFormat("#.##")
        private const val PRODUCT_ID_KEY = "product_id"

        fun newInstance(productId: Long): ProductDetailsFragment =
            ProductDetailsFragment().apply {
                arguments = Bundle().apply { putLong(PRODUCT_ID_KEY, productId) }
            }

    }

}