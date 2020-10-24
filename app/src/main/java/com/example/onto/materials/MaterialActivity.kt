package com.example.onto.materials

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.cardview.widget.CardView
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.onto.ExampleIntent

import  com.example.onto.R
import com.example.onto.base.BaseActivity
import kotlinx.android.synthetic.main.activity_material.*


class MaterialActivity : BaseActivity<MaterialViewState, MaterialIntent>() {

//
    private val updateIntent = MutableLiveData<MaterialIntent>()
    private val otherIntent = MutableLiveData<MaterialIntent>().also {
        it.value = MaterialIntent.InitialIntent
    }
    private lateinit var runnable: Runnable
    private lateinit var handler: Handler


    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    override val layoutResourceId: Int = R.layout.activity_material

    override val viewModel: MaterialViewModel by viewModels()

    override fun initViews() {

        swipeRefreshLayout.setOnRefreshListener {
                updateIntent.value = MaterialIntent.RefreshIntent
        }

        _intentLiveData.addSource(updateIntent) {
            _intentLiveData.value = it
        }
        _intentLiveData.addSource(otherIntent) {
            _intentLiveData.value = it
        }

    }

    override fun render(viewState: MaterialViewState) {

        swipeRefreshLayout.isRefreshing = viewState.isRefreshing

        viewManager = LinearLayoutManager(this)
        viewAdapter = MaterialAdapter(viewState.materials)


        recyclerView = findViewById<RecyclerView>(R.id.material_recycler_view).apply {
            setHasFixedSize(true)

            layoutManager = viewManager

            adapter = viewAdapter

        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MaterialActivity.materialPrefs = getSharedPreferences("amount", Context.MODE_PRIVATE)
    }


    companion object {
        var materialPrefs: SharedPreferences? = null
    }

}