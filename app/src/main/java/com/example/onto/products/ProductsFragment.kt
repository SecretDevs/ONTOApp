package com.example.onto.products

import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.onto.R
import com.example.onto.base.BaseFragment
import com.example.onto.base.recycler.RecyclerState
import com.example.onto.products.recycler.ProductAdapter
import com.example.onto.products.recycler.ProductItemDecoration
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_products.*

@AndroidEntryPoint
class ProductsFragment : BaseFragment<ProductsViewState, ProductsIntent>() {
    override val layoutResourceId: Int
        get() = R.layout.fragment_products
    override val viewModel: ProductsViewModel by viewModels()

    private lateinit var productAdapter: ProductAdapter
    private val intentLiveData = MutableLiveData<ProductsIntent>().also { intents ->
        intents.value = ProductsIntent.InitialIntent
        _intentLiveData.addSource(intents) {
            _intentLiveData.value = it
        }
    }

    override fun initViews() {
        refresher.setColorSchemeResources(R.color.colorPrimary)
        refresher.setOnRefreshListener {
            intentLiveData.value = ProductsIntent.RefreshIntent
        }

        cart_view.setOnClickListener {
            //navigate to cart
        }

        productAdapter = ProductAdapter(
            onRetry = { intentLiveData.value = ProductsIntent.ReloadIntent },
            onCLick = { }, //navigate to product details
            onAddToCartClick = { intentLiveData.value = ProductsIntent.AddToCartIntent(it) }
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