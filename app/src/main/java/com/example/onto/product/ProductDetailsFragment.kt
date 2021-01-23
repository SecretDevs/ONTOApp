package com.example.onto.product

import android.os.Bundle
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.onto.R
import com.example.onto.base.BaseFragment
import com.example.onto.product.recycler.SimilarProductItemDecoration
import com.example.onto.product.recycler.SimilarProductsAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_product_details.*
import kotlinx.android.synthetic.main.fragment_product_details_content.*
import kotlinx.android.synthetic.main.item_error.*
import net.cachapa.expandablelayout.ExpandableLayout
import java.text.DecimalFormat

@AndroidEntryPoint
class ProductDetailsFragment : BaseFragment<ProductDetailsViewState, ProductDetailsIntent>() {
    override val layoutResourceId: Int
        get() = R.layout.fragment_product_details
    override val viewModel: ProductDetailsViewModel by viewModels()
    private lateinit var productAdapter: SimilarProductsAdapter

    override fun backStackIntent(): ProductDetailsIntent =
        ProductDetailsIntent.UpdateCartIntent

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

        productAdapter = SimilarProductsAdapter(
            {
                arguments?.putLong(PRODUCT_ID_KEY, it)
                _intentLiveData.value = ProductDetailsIntent.InitialIntent(getProductId())
            },
            { _intentLiveData.value = ProductDetailsIntent.AddSimilarToCartIntent(it) }
        )

        productAdapter.setHasStableIds(true)

        similar_recycler.adapter = productAdapter
        similar_recycler.addItemDecoration(
            SimilarProductItemDecoration(
                resources.getDimensionPixelSize(R.dimen.margin_default)
            )
        )
        similar_recycler.layoutManager = LinearLayoutManager(
            requireContext(), LinearLayoutManager.HORIZONTAL, false
        )

        product_details_description_title.setOnClickListener {
            product_details_description.toggle()
        }
        product_details_description_toggle.setOnClickListener {
            product_details_description.toggle()
        }
        product_details_description.setOnExpansionUpdateListener { expansionFraction, state ->
            if (state == ExpandableLayout.State.EXPANDING || state == ExpandableLayout.State.EXPANDED) {
                Glide.with(product_details_description_toggle)
                    .load(R.drawable.ic_baseline_expand_less)
                    .into(product_details_description_toggle)
            } else {
                Glide.with(product_details_description_toggle)
                    .load(R.drawable.ic_baseline_expand_more)
                    .into(product_details_description_toggle)
            }
        }

        product_details_advantages_title.setOnClickListener {
            product_details_advantages.toggle()
        }
        product_details_advantages_toggle.setOnClickListener {
            product_details_advantages.toggle()
        }
        product_details_advantages.setOnExpansionUpdateListener { expansionFraction, state ->
            if (state == ExpandableLayout.State.EXPANDING || state == ExpandableLayout.State.EXPANDED) {
                Glide.with(product_details_advantages_toggle)
                    .load(R.drawable.ic_baseline_expand_less)
                    .into(product_details_advantages_toggle)
            } else {
                Glide.with(product_details_advantages_toggle)
                    .load(R.drawable.ic_baseline_expand_more)
                    .into(product_details_advantages_toggle)
            }
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
        cart_price.isVisible =
            viewState.cartInformation != null && viewState.cartInformation.count != 0
        if (viewState.cartInformation != null) {
            cart_price.text = resources.getString(
                R.string.price_placeholder,
                priceFormat.format(viewState.cartInformation.totalPrice)
            )
            cart_badge.text = viewState.cartInformation.count.toString()
        }

        if (viewState.product != null) {
            Glide.with(product_details_image)
                .load(viewState.product.product.image)
                .into(product_details_image)
            product_details_title.text = viewState.product.product.name
            product_details_price_value.text =
                resources.getString(
                    R.string.price_placeholder,
                    priceFormat.format(viewState.product.product.price)
                )
            product_details_description_body.text =
                viewState.product.product.info
            product_details_advantages_body.text =
                viewState.product.product.description

            product_details_protein_value.text = viewState.product.product.proteins
            product_details_fat_value.text = viewState.product.product.fat
            product_details_carbon_value.text = viewState.product.product.carbon
            product_details_kcal_value.text = viewState.product.product.kcal

            productAdapter.updateData(viewState.product.similarProducts)

            product_details_protein_value.isVisible =
                viewState.product.product.proteins.isNotEmpty()
            product_details_fat_value.isVisible = viewState.product.product.fat.isNotEmpty()
            product_details_carbon_value.isVisible = viewState.product.product.carbon.isNotEmpty()
            product_details_kcal_value.isVisible = viewState.product.product.kcal.isNotEmpty()

            product_details_protein_title.isVisible =
                viewState.product.product.proteins.isNotEmpty()
            product_details_fat_title.isVisible = viewState.product.product.fat.isNotEmpty()
            product_details_carbon_title.isVisible = viewState.product.product.carbon.isNotEmpty()
            product_details_kcal_title.isVisible = viewState.product.product.kcal.isNotEmpty()

            add_to_cart_btn.isEnabled = viewState.product.product.inStock != 0
            product_details_minus_btn.isEnabled = viewState.product.product.inStock != 0
            product_details_plus_btn.isEnabled = viewState.product.product.inStock != 0

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