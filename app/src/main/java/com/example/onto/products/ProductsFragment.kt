package com.example.onto.products

import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.onto.R
import com.example.onto.base.BaseFragment
import com.example.onto.base.recycler.RecyclerState
import com.example.onto.products.recycler.ProductAdapter
import com.example.onto.products.recycler.ProductItemDecoration
import com.example.onto.utils.formatPrice
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_products.*
import java.text.DecimalFormat

@AndroidEntryPoint
class ProductsFragment : BaseFragment<ProductsViewState, ProductsIntent>() {
    override val layoutResourceId: Int
        get() = R.layout.fragment_products
    override val viewModel: ProductsViewModel by viewModels()

    private lateinit var productAdapter: ProductAdapter

    override fun initialIntent(): ProductsIntent? = ProductsIntent.InitialIntent

    override fun backStackIntent(): ProductsIntent = ProductsIntent.UpdateCartIntent

    override fun initViews() {
        refresher.setColorSchemeResources(R.color.colorPrimary)
        refresher.setOnRefreshListener {
            _intentLiveData.value = ProductsIntent.RefreshIntent
        }

        cart_btn.setOnClickListener {
            _intentLiveData.value = ProductsIntent.OpenCartIntent
        }

        productAdapter = ProductAdapter(
            onRetry = { _intentLiveData.value = ProductsIntent.ReloadIntent },
            onCLick = { _intentLiveData.value = ProductsIntent.OpenProductDetailsIntent(it) },
            onAddToCartClick = { _intentLiveData.value = ProductsIntent.AddToCartIntent(it) }
        )
        productAdapter.setHasStableIds(true)

        products_recycler.adapter = productAdapter
        products_recycler.addItemDecoration(
            ProductItemDecoration(
                resources.getDimensionPixelSize(R.dimen.margin_default)
            )
        )
        products_recycler.layoutManager = StaggeredGridLayoutManager(
            2, StaggeredGridLayoutManager.VERTICAL
        )
    }

    override fun render(viewState: ProductsViewState) {
        val state = when {
            viewState.isInitialLoading -> RecyclerState.LOADING
            viewState.initialError != null -> RecyclerState.ERROR
            viewState.products.isEmpty() -> RecyclerState.EMPTY
            else -> RecyclerState.ITEM
        }
        val isRefreshable = !(viewState.isInitialLoading || viewState.initialError != null)
        cart_badge.isVisible = viewState.cartInfo != null && viewState.cartInfo.count != 0
        cart_price.isVisible = viewState.cartInfo != null && viewState.cartInfo.count != 0
        if (viewState.cartInfo != null) {
            cart_price.text = resources.getString(
                R.string.price_placeholder,
                formatPrice(viewState.cartInfo.totalPrice)
            )
            cart_badge.text = viewState.cartInfo.count.toString()
        }

        refresher.isEnabled = isRefreshable
        refresher.isRefreshing = viewState.isRefreshLoading
        productAdapter.updateData(viewState.products, state)

        if (viewState.refreshError != null) {
            Snackbar.make(
                fragment_products_layout,
                viewState.refreshError.localizedMessage ?: "",
                Snackbar.LENGTH_SHORT
            ).show()
        }
    }

}