package com.softaai.newsapp.utils

import android.app.Activity
import androidx.annotation.ColorRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider


fun Activity.getColorRes(@ColorRes id: Int) = ContextCompat.getColor(applicationContext, id)


inline fun <reified VM : ViewModel> AppCompatActivity.viewModelOf(
    factory: ViewModelProvider.Factory
) = ViewModelProvider(this, factory).get(VM::class.java)