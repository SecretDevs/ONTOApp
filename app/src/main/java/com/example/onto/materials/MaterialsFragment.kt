package com.example.onto.materials

import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.onto.R
import com.example.onto.base.BaseFragment
import com.example.onto.base.recycler.RecyclerState
import com.example.onto.materials.recycler.MaterialAdapter
import com.example.onto.materials.recycler.MaterialsItemDecoration
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_materials.*
import java.text.DecimalFormat

@AndroidEntryPoint
class MaterialsFragment : BaseFragment<MaterialViewState, MaterialIntent>() {
    override val layoutResourceId: Int
        get() = R.layout.fragment_materials

    override val viewModel: MaterialViewModel by viewModels()

    private lateinit var materialAdapter: MaterialAdapter

    override fun backStackIntent(): MaterialIntent = MaterialIntent.UpdateCartIntent

    override fun initialIntent(): MaterialIntent? = MaterialIntent.InitialIntent

    override fun initViews() {
        refresher.setColorSchemeResources(R.color.colorPrimary)
        refresher.setOnRefreshListener {
            _intentLiveData.value = MaterialIntent.RefreshIntent
        }

        cart_btn.setOnClickListener { _intentLiveData.value = MaterialIntent.NavigateToCartIntent }

        materialAdapter = MaterialAdapter(
            onRetry = { _intentLiveData.value = MaterialIntent.ReloadIntent },
            onCLick = {
                _intentLiveData.value = MaterialIntent.NavigateToMaterialDetailsIntent(it)
            },
        )

        materialAdapter.setHasStableIds(true)

        materials_recycler.adapter = materialAdapter
        materials_recycler.addItemDecoration(
            MaterialsItemDecoration(
                resources.getDimensionPixelSize(R.dimen.margin_default)
            )
        )
        materials_recycler.layoutManager =
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
    }

    override fun render(viewState: MaterialViewState) {
        val state = when {
            viewState.isInitialLoading -> RecyclerState.LOADING
            viewState.initialError != null -> RecyclerState.ERROR
            viewState.products.isEmpty() -> RecyclerState.EMPTY
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
                priceFormat.format(viewState.cartInformation.totalPrice)
            )
            cart_badge.text = viewState.cartInformation.count.toString()
        }

        refresher.isEnabled = isRefreshable
        refresher.isRefreshing = viewState.isRefreshLoading
        materialAdapter.updateData(viewState.products, state)

        if (viewState.refreshError != null) {
            Snackbar.make(
                materials_recycler,
                viewState.refreshError.localizedMessage ?: "",
                Snackbar.LENGTH_SHORT
            ).show()
        }
    }

    companion object {
        private val priceFormat = DecimalFormat("#.##")
    }

}