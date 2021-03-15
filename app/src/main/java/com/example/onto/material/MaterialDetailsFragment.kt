package com.example.onto.material

import android.os.Bundle
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.example.onto.R
import com.example.onto.base.BaseFragment
import com.example.onto.utils.formatPrice
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_article_details.*
import kotlinx.android.synthetic.main.fragment_article_details_content.*
import kotlinx.android.synthetic.main.item_error.*

@AndroidEntryPoint
class MaterialDetailsFragment : BaseFragment<MaterialDetailsViewState, MaterialDetailsIntent>() {
    override val layoutResourceId: Int
        get() = R.layout.fragment_article_details
    override val viewModel: MaterialDetailsViewModel by viewModels()

    override fun backStackIntent(): MaterialDetailsIntent =
        MaterialDetailsIntent.MaterialDetailsNothingIntent

    override fun initialIntent(): MaterialDetailsIntent? =
        MaterialDetailsIntent.InitialIntent(getArticleId())

    override fun initViews() {
        arrow_back_btn.setOnClickListener {
            _intentLiveData.value = MaterialDetailsIntent.GoBackIntent
        }
        cart_btn.setOnClickListener {
            _intentLiveData.value = MaterialDetailsIntent.NavigateToCartIntent
        }

        top_article_fab.setOnClickListener {
            article_details_content.smoothScrollTo(0, 0)
        }
        retry_button.setOnClickListener {
            _intentLiveData.value = MaterialDetailsIntent.ReloadIntent(getArticleId())
        }
    }

    override fun render(viewState: MaterialDetailsViewState) {
        loading_view.isVisible = viewState.isInitialLoading
        error_view.isVisible = viewState.initialLoadingError != null
        article_details_content.isVisible = viewState.article != null

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

        if (viewState.article != null) {
            Glide.with(article_details_image)
                .load(viewState.article.image)
                .into(article_details_image)
            article_details_name.text = viewState.article.name
            article_details_date.text = viewState.article.date.subSequence(0, 10)
            article_details_text.text = viewState.article.text
        }
    }

    private fun getArticleId() = arguments?.getLong(ARTICLE_ID_KEY) ?: 0L

    companion object {
        private const val ARTICLE_ID_KEY = "article_id"

        fun newInstance(articleId: Long): MaterialDetailsFragment =
            MaterialDetailsFragment().apply {
                arguments = Bundle().apply { putLong(ARTICLE_ID_KEY, articleId) }
            }

    }

}