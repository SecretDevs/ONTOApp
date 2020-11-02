package com.example.onto.materials

import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.onto.R
import com.example.onto.base.BaseFragment
import com.example.onto.base.recycler.RecyclerState
import com.example.onto.material.MaterialDetailsFragment
import com.example.onto.materials.recycler.MaterialAdapter
import com.example.onto.products.recycler.ProductItemDecoration
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_materials.*

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
        refresher.setColorSchemeResources(R.color.colorPrimary)
        refresher.setOnRefreshListener {
            intentLiveData.value = MaterialIntent.RefreshIntent
        }

        materialAdapter = MaterialAdapter(
            onRetry = { intentLiveData.value = MaterialIntent.ReloadIntent },
            onCLick = {
                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, MaterialDetailsFragment.newInstance(it))
                    .addToBackStack(MaterialDetailsFragment::class.java.name)
                    .commitAllowingStateLoss()
            },
        )

        materialAdapter.setHasStableIds(true)

        materials_recycler.adapter = materialAdapter
        materials_recycler.addItemDecoration(
            ProductItemDecoration(
                resources.getDimensionPixelSize(R.dimen.gutter_default)
            )
        )
        materials_recycler.layoutManager = LinearLayoutManager(context)
    }

    override fun render(viewState: MaterialViewState) {
        val state = when {
            viewState.isInitialLoading -> RecyclerState.LOADING
            viewState.initialError != null -> RecyclerState.ERROR
            viewState.products.isEmpty() -> RecyclerState.EMPTY
            else -> RecyclerState.ITEM
        }
        val isRefreshable = !(viewState.isInitialLoading || viewState.initialError != null)

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
}