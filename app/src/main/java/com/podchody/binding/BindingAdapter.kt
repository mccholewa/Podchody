package com.podchody.binding

/**
 * Created by Misiu on 15.01.2018.
 */

import android.databinding.BindingAdapter
import android.databinding.Bindable
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.TextView


@set:BindingAdapter("visibleOrGone")
var View.visibleOrGone
    get() = visibility == View.VISIBLE
    set(value) {
        visibility = if (value) View.VISIBLE else View.GONE
    }