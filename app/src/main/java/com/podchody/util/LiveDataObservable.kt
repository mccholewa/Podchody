package com.podchody.util

/**
 * Created by Misiu on 04.01.2018.
 */

import android.arch.lifecycle.LifecycleOwner

interface LiveDataObservable<out T> {

    fun observe(owner: LifecycleOwner, observer: (T) -> Unit)

    fun observeForever(observer: (T) -> Unit)
}