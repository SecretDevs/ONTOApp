package com.example.onto.product

import android.os.Bundle
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

    override fun backStackIntent(): ProductDetailsIntent =
        ProductDetailsIntent.ProductDetailsNothingIntent

    override fun initialIntent(): ProductDetailsIntent? =
        ProductDetailsIntent.InitialIntent(getProductId())

    override fun initViews() {
        arrow_back_btn.setOnClickListener {
            _intentLiveData.value = ProductDetailsIntent.NavigateBackIntent
        }
        cart_btn.setOnClickListener {
            _intentLiveData.value = ProductDetailsIntent.NavigateToCartIntent
        }
        retry_button.setOnClickListener {
            _intentLiveData.value = ProductDetailsIntent.ReloadIntent(getProductId())
        }
        add_to_cart_btn.setOnClickListener {
            _intentLiveData.value = ProductDetailsIntent.AddToCartIntent(
                getProductId()
            )
        }
        product_details_plus_btn.setOnClickListener {
            _intentLiveData.value = ProductDetailsIntent.AddOneIntent
        }
        product_details_minus_btn.setOnClickListener {
            _intentLiveData.value = ProductDetailsIntent.RemoveOneIntent
        }
    }

    override fun render(viewState: ProductDetailsViewState) {
        loading_view.isVisible = viewState.isInitialLoading
        error_view.isVisible = viewState.initialLoadingError != null
        product_details_content.isVisible = viewState.product != null
        add_to_cart_layout.isVisible = viewState.product != null
        product_details_quantity.text = viewState.quantity.toString()
        product_details_minus_btn.isEnabled = viewState.quantity > 1
        cart_badge.isVisible =
            viewState.cartInformation != null && viewState.cartInformation.count != 0
        cart_badge.text = viewState.cartInformation?.count?.toString()

        if (viewState.product != null) {
            Glide.with(product_details_image)
                .load(viewState.product.image)
                .into(product_details_image)
            product_details_title.text = viewState.product.name
            product_details_price_value.text =
                resources.getString(
                    R.string.price_placeholder,
                    priceFormat.format(viewState.product.price)
                )
            product_details_description.text = viewState.product.description
            add_to_cart_btn.isEnabled = viewState.product.isInStock
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