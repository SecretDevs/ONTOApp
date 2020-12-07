package com.example.onto.utils

import android.content.Context
import android.graphics.Typeface.BOLD
import android.text.Annotation
import android.text.Spannable
import android.text.SpannableString
import android.text.SpannedString
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.onto.R

fun <T, R> LiveData<T>.map(transformation: (T) -> R): LiveData<R> =
    Transformations.map(this, transformation)

fun <T> LiveData<T>.distinctUntilChanged(): LiveData<T> =
    Transformations.distinctUntilChanged(this)

fun Context.getSpannableString(resource: Int): SpannableString {
    val titleText = resources.getText(resource) as SpannedString
    val annotations = titleText.getSpans(0, titleText.length, Annotation::class.java)
    val spannableString = SpannableString(titleText)
    for (annotation in annotations) {
        when (annotation.key) {
            "color" -> {
                when (annotation.value) {
                    "primary" -> {
                        spannableString.setSpan(
                            ForegroundColorSpan(ContextCompat.getColor(this, R.color.colorPrimary)),
                            titleText.getSpanStart(annotation),
                            titleText.getSpanEnd(annotation),
                            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                        )
                        spannableString.setSpan(
                            StyleSpan(BOLD),
                            titleText.getSpanStart(annotation),
                            titleText.getSpanEnd(annotation),
                            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                        )
                    }
                }
            }
        }
    }
    return spannableString
}
