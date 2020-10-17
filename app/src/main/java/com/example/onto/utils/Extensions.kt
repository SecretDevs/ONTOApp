package com.example.onto.utils

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations

fun <T, R> LiveData<T>.map(transformation: (T) -> R): LiveData<R> =
    Transformations.map(this, transformation)

fun <T> LiveData<T>.distinctUnilChanged(): LiveData<T> =
    Transformations.distinctUntilChanged(this)