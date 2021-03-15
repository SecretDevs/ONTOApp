package com.example.onto.discount

import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.onto.R
import com.example.onto.base.BaseFragment
import com.example.onto.base.recycler.RecyclerState
import com.example.onto.discount.recycler.DiscountAdapter
import com.example.onto.discount.recycler.DiscountItemDecoration
import com.example.onto.utils.formatPrice
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_discounts.*
import kotlinx.android.synthetic.main.fragment_discounts.cart_badge
import kotlinx.android.synthetic.main.fragment_discounts.cart_btn
import kotlinx.android.synthetic.main.fragment_materials.refresher

@AndroidEntryPoint
class DiscountFragment : BaseFragment<DiscountViewState, DiscountIntent>() {
    override val layoutResourceId: Int
        get() = R.layout.fragment_discounts

    override val viewModel: DiscountViewModel by viewModels()

    private lateinit var discountAdapter: DiscountAdapter

    override fun backStackIntent(): DiscountIntent = DiscountIntent.UpdateCartIntent
    override fun initialIntent(): DiscountIntent? = DiscountIntent.InitialIntent

    override fun initViews() {
        refresher.setColorSchemeResources(R.color.colorPrimary)
        refresher.setOnRefreshListener {
            _intentLiveData.value = DiscountIntent.RefreshIntent
        }

        cart_btn.setOnClickListener { _intentLiveData.value = DiscountIntent.NavigateToCartIntent }

        discountAdapter = DiscountAdapter(
            onRetry = { _intentLiveData.value = DiscountIntent.ReloadIntent },
            onCLick = {
                _intentLiveData.value = DiscountIntent.NavigateToDiscountDetailsIntent(it)
            },
            onAddCLick = { _intentLiveData.value = DiscountIntent.AddProductToCartIntent(it) }
        )

        discountAdapter.setHasStableIds(true)

        discounts_recycler.adapter = discountAdapter
        discounts_recycler.addItemDecoration(
            DiscountItemDecoration(
                resources.getDimensionPixelSize(R.dimen.margin_default)
            )
        )
        discounts_recycler.layoutManager = StaggeredGridLayoutManager(
            2, StaggeredGridLayoutManager.VERTICAL
        )
    }

    override fun render(viewState: DiscountViewState) {
        val state = when {
            viewState.isInitialLoading -> RecyclerState.LOADING
            viewState.initialError != null -> RecyclerState.ERROR
            viewState.offers.isEmpty() -> RecyclerState.EMPTY
            else -> RecyclerState.ITEM
        }
        val isRefreshable = !(viewState.isInitialLoading || viewState.initialError != null)

        cart_badge.isVisible =
            viewState.cartInformation != null && viewState.cartInformation.count != 0
        cart_price.isVisible =
            viewState.cartInformation != null && viewState.cartInformation.count != 0
        if (viewState.cartInformation != null) {
            cart_price.text = resources.getString(
                R.string.price_placeholder,
                formatPrice(viewState.cartInformation.totalPrice)
            )
            cart_badge.text = viewState.cartInformation.count.toString()
        }

        refresher.isEnabled = isRefreshable
        refresher.isRefreshing = viewState.isRefreshLoading
        discountAdapter.updateData(viewState.offers, state)

        if (viewState.refreshError != null) {
            Snackbar.make(
                discounts_recycler,
                viewState.refreshError.localizedMessage ?: "",
                Snackbar.LENGTH_SHORT
            ).show()
        }
    }

}