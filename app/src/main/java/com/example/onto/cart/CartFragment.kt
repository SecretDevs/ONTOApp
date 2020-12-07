package com.example.onto.cart

import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.onto.R
import com.example.onto.base.BaseFragment
import com.example.onto.base.recycler.RecyclerState
import com.example.onto.cart.recycler.CartAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_cart.*

@AndroidEntryPoint
class CartFragment : BaseFragment<CartViewState, CartIntent>() {
    override val layoutResourceId: Int
        get() = R.layout.fragment_cart
    override val viewModel: CartViewModel by viewModels()

    private lateinit var adapter: CartAdapter

    override fun backStackIntent(): CartIntent = CartIntent.CartNothingIntent
    override fun initialIntent(): CartIntent? = CartIntent.InitialIntent

    override fun initViews() {
        arrow_back_btn.setOnClickListener { _intentLiveData.value = CartIntent.GoBackIntent }
        order_history_btn.setOnClickListener {
            _intentLiveData.value = CartIntent.OpenOrderHistoryIntent
        }
        adapter = CartAdapter(
            onEmptyClick = { _intentLiveData.value = CartIntent.OpenShopIntent },
            onErrorClick = { _intentLiveData.value = CartIntent.ReloadIntent },
            onProductClick = { }, //??
            onRemoveClick = { _intentLiveData.value = CartIntent.RemoveProductIntent(it) },
            onMinusClick = { _intentLiveData.value = CartIntent.RemoveOneItemForProductIntent(it) },
            onAddClick = { _intentLiveData.value = CartIntent.AddOneItemForProductIntent(it) }
        )
        products_recycler.adapter = adapter
        products_recycler.layoutManager = LinearLayoutManager(requireContext())
    }

    override fun render(viewState: CartViewState) {
        val state = when {
            viewState.isInitialLoading -> RecyclerState.LOADING
            viewState.initialLoadingError != null -> RecyclerState.ERROR
            viewState.data.isEmpty() -> RecyclerState.EMPTY
            else -> RecyclerState.ITEM
        }
        adapter.updateData(viewState.data, state)

    }

    companion object {
        fun newInstance() = CartFragment()
    }

}