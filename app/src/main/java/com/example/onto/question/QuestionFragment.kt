package com.example.onto.question

import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.example.onto.R
import com.example.onto.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_question.*

@AndroidEntryPoint
class QuestionFragment : BaseFragment<QuestionViewState, QuestionIntent>() {
    override val layoutResourceId: Int
        get() = R.layout.fragment_question
    override val viewModel: QuestionViewModel by viewModels()

    override fun backStackIntent(): QuestionIntent = QuestionIntent.SaveDraftIntent(
        email = email_edit.text?.toString() ?: "",
        question = question_edit.text?.toString() ?: ""
    )

    override fun initialIntent(): QuestionIntent? = QuestionIntent.InitialIntent

    override fun initViews() {
        arrow_back_btn.setOnClickListener {
            _intentLiveData.value = QuestionIntent.GoBackIntent
        }
        post_question_btn.setOnClickListener {
            _intentLiveData.value = QuestionIntent.SendQuestionIntent(
                email_edit.text.toString(),
                question_edit.text.toString()
            )
        }
    }

    override fun render(viewState: QuestionViewState) {
        if (email_edit.text.isNullOrEmpty()) {
            email_edit.setText(viewState.savedEmail)
        }
        if (question_edit.text.isNullOrEmpty()) {
            question_edit.setText(viewState.savedMessage)
        }
        loading_view.isVisible = viewState.isLoading

    }

    companion object {
        fun newInstance() = QuestionFragment()
    }

}