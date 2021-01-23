package com.example.onto.data.usecase

import android.content.Context
import androidx.core.content.edit
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

interface QuestionUseCase {
    fun setEmail(email: String)
    fun setMessage(message: String)
    fun getEmail(): String
    fun getMessage(): String
}

class QuestionUseCaseImpl @Inject constructor(
    @ApplicationContext context: Context
) : QuestionUseCase {
    private val pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    override fun setEmail(email: String) {
        pref.edit {
            putString(EMAIL_FIELD, email)
        }
    }

    override fun setMessage(message: String) {
        pref.edit {
            putString(MESSAGE_FIELD, message)
        }
    }

    override fun getEmail(): String = pref.getString(EMAIL_FIELD, "") ?: ""

    override fun getMessage(): String = pref.getString(MESSAGE_FIELD, "") ?: ""

    companion object {
        private const val PREF_NAME = "ONTO_QUESTION_PREF"
        private const val EMAIL_FIELD = "ONTO_EMAIL_NAME"
        private const val MESSAGE_FIELD = "ONTO_MESSAGE_NAME"
    }
}