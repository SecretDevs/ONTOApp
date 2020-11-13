package com.example.onto.material

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.example.onto.R
import com.example.onto.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_article_details.*
import kotlinx.android.synthetic.main.fragment_article_details_content.*
import kotlinx.android.synthetic.main.fragment_product_details.arrow_back_btn
import kotlinx.android.synthetic.main.fragment_product_details.error_view
import kotlinx.android.synthetic.main.fragment_product_details.loading_view
import kotlinx.android.synthetic.main.item_error.*
import java.text.DecimalFormat

@AndroidEntryPoint
class MaterialDetailsFragment : BaseFragment<MaterialDetailsViewState, MaterialDetailsIntent>() {
    override val layoutResourceId: Int
        get() = R.layout.fragment_article_details
    override val viewModel: MaterialDetailsViewModel by viewModels()

    override fun initViews() {
        arrow_back_btn.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }

        material_fab.setOnClickListener{
            article_details_content.scrollTo(0,0)
        }
        retry_button.setOnClickListener {
            _intentLiveData.value = MaterialDetailsIntent.ReloadIntent(getArticleId())
        }
    }

    override fun render(viewState: MaterialDetailsViewState) {
        loading_view.isVisible = viewState.isInitialLoading
        error_view.isVisible = viewState.initialLoadingError != null
        article_details_content.isVisible = viewState.article != null

        if (viewState.article != null) {
            Glide.with(article_details_image)
                .load(viewState.article.image)
                .into(article_details_image)
            article_details_name.text = viewState.article.name
            article_details_date.text = viewState.article.date.subSequence(0, 10)
            article_details_text.text = viewState.article.text
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (savedInstanceState == null) {
            _intentLiveData.value =
                MaterialDetailsIntent.InitialIntent(getArticleId())
        }
    }

    private fun getArticleId() = arguments?.getLong(ARTICLE_ID_KEY) ?: 0L

    companion object {
        private val priceFormat = DecimalFormat("#.##")
        private const val ARTICLE_ID_KEY = "article_id"

        fun newInstance(articleId: Long): MaterialDetailsFragment =
            MaterialDetailsFragment().apply {
                arguments = Bundle().apply { putLong(ARTICLE_ID_KEY, articleId) }
            }

    }

}