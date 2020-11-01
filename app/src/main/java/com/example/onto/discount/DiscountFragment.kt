package com.example.onto.discount

import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.onto.R
import com.example.onto.base.BaseFragment
import com.example.onto.base.recycler.RecyclerState
import com.example.onto.products.recycler.ProductItemDecoration
import com.example.onto.discount.recycler.DiscountAdapter
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_materials.*

@AndroidEntryPoint
class DiscountFragment : BaseFragment<DiscountlViewState, DiscountIntent>() {
    override val layoutResourceId: Int
        get() = R.layout.fragment_materials

    override val viewModel: DiscountViewModel by viewModels()

    private lateinit var materialAdapter: DiscountAdapter

    private val intentLiveData = MutableLiveData<DiscountIntent>().also { intents ->
        intents.value = DiscountIntent.InitialIntent
        _intentLiveData.addSource(intents) {
            _intentLiveData.value = it
        }
    }

    override fun initViews() {

        //refresher.setColorSchemeResources(R.color.colorPrimary)
        material_refresher.setOnRefreshListener {
            intentLiveData.value = DiscountIntent.RefreshIntent
        }

        materialAdapter = DiscountAdapter(
            onRetry = { intentLiveData.value = DiscountIntent.ReloadIntent },
            onCLick = { },
        )

        materialAdapter.setHasStableIds(true)

        material_recycler.adapter = materialAdapter
        material_recycler.addItemDecoration(
            ProductItemDecoration(
                resources.getDimensionPixelSize(R.dimen.gutter_default)
            )
        )
        material_recycler.layoutManager = LinearLayoutManager(context)
    }

    override fun render(viewState: DiscountlViewState) {
        val state = when {
            viewState.isInitialLoading -> RecyclerState.LOADING
            viewState.initialError != null -> RecyclerState.ERROR
            viewState.products.isEmpty() -> RecyclerState.EMPTY
            else -> RecyclerState.ITEM
        }
        val isRefreshable = !(viewState.isInitialLoading || viewState.initialError != null)

        material_refresher.isEnabled = isRefreshable
        material_refresher.isRefreshing = viewState.isRefreshLoading
        materialAdapter.updateData(viewState.products, state)

        if (viewState.refreshError != null) {
            Snackbar.make(
                material_recycler,
                viewState.refreshError.localizedMessage ?: "",
                Snackbar.LENGTH_SHORT
            ).show()
        }
    }
}