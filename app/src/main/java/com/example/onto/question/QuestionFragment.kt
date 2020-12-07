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

    override fun backStackIntent(): QuestionIntent = QuestionIntent.QuestionNothingIntent

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
        if (email_edit.text == null) {
            email_edit.setText(viewState.savedEmail)
        }
        if (question_edit.text == null) {
            question_edit.setText(viewState.savedMessage)
        }
        loading_view.isVisible = viewState.isLoading

    }

    companion object {
        fun newInstance() = QuestionFragment()
    }

}