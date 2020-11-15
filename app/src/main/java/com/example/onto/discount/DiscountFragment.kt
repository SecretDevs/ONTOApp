package com.example.onto.discount

import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.onto.R
import com.example.onto.base.BaseFragment
import com.example.onto.base.recycler.RecyclerState
import com.example.onto.discount.recycler.DiscountAdapter
import com.example.onto.product.ProductDetailsFragment
import com.example.onto.products.recycler.ProductItemDecoration
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_discounts.*
import kotlinx.android.synthetic.main.fragment_materials.refresher

@AndroidEntryPoint
class DiscountFragment : BaseFragment<DiscountlViewState, DiscountIntent>() {
    override val layoutResourceId: Int
        get() = R.layout.fragment_discounts

    override val viewModel: DiscountViewModel by viewModels()

    private lateinit var discountAdapter: DiscountAdapter

    private val intentLiveData = MutableLiveData<DiscountIntent>().also { intents ->
        _intentLiveData.addSource(intents) {
            _intentLiveData.value = it
        }
    }

    override fun initialIntent(): DiscountIntent? = DiscountIntent.InitialIntent

    override fun initViews() {
        refresher.setColorSchemeResources(R.color.colorPrimary)
        refresher.setOnRefreshListener {
            intentLiveData.value = DiscountIntent.RefreshIntent
        }

        discountAdapter = DiscountAdapter(
            onRetry = { intentLiveData.value = DiscountIntent.ReloadIntent },
            onCLick = {
                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, ProductDetailsFragment.newInstance(it))
                    .addToBackStack(ProductDetailsFragment::class.java.name)
                    .commitAllowingStateLoss()
            },
        )

        discountAdapter.setHasStableIds(true)

        discounts_recycler.adapter = discountAdapter
        discounts_recycler.addItemDecoration(
            ProductItemDecoration(
                resources.getDimensionPixelSize(R.dimen.gutter_default)
            )
        )
        discounts_recycler.layoutManager = LinearLayoutManager(context)
    }

    override fun render(viewState: DiscountlViewState) {
        val state = when {
            viewState.isInitialLoading -> RecyclerState.LOADING
            viewState.initialError != null -> RecyclerState.ERROR
            viewState.offers.isEmpty() -> RecyclerState.EMPTY
            else -> RecyclerState.ITEM
        }
        val isRefreshable = !(viewState.isInitialLoading || viewState.initialError != null)

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