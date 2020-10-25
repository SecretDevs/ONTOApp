package com.example.onto.materials

import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.onto.R
import com.example.onto.base.BaseFragment
import com.example.onto.base.recycler.RecyclerState
import com.example.onto.materials.recycler.MaterialAdapter
import com.example.onto.products.recycler.ProductAdapter
import com.example.onto.products.recycler.ProductItemDecoration
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_materials.*
import kotlinx.android.synthetic.main.fragment_products.*

@AndroidEntryPoint
class MaterialsFragment : BaseFragment<MaterialViewState, MaterialIntent>() {
    override val layoutResourceId: Int
        get() = R.layout.fragment_materials

    override val viewModel: MaterialViewModel by viewModels()

    private lateinit var materialAdapter: MaterialAdapter

    private val intentLiveData = MutableLiveData<MaterialIntent>().also { intents ->
        intents.value = MaterialIntent.InitialIntent
        _intentLiveData.addSource(intents) {
            _intentLiveData.value = it
        }
    }

    override fun initViews() {

        //refresher.setColorSchemeResources(R.color.colorPrimary)
        material_refresher.setOnRefreshListener {
            intentLiveData.value = MaterialIntent.RefreshIntent
        }

        materialAdapter = MaterialAdapter(
            onRetry = { intentLiveData.value = MaterialIntent.ReloadIntent },
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

    override fun render(viewState: MaterialViewState) {
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