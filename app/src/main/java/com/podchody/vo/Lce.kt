package com.podchody.vo

/**
 * Created by Misiu on 15.04.2018.
 */
interface BaseLce

sealed class Lce<out T> : BaseLce {

    abstract fun <R> map(f: (T) -> R): Lce<R>

    inline fun doOnData(f: (T) -> Unit) {
        if (this is Success) {
            f(data)
        }
    }

    data class Success<out T>(val data: T) : Lce<T>() {
        override fun <R> map(f: (T) -> R): Lce<R> = Success(f(data))
    }

    data class Error(val message: String) : Lce<Nothing>() {
        constructor(t: Throwable) : this(t.message ?: "")

        override fun <R> map(f: (Nothing) -> R): Lce<R> = this
    }

    object Loading : Lce<Nothing>() {
        override fun <R> map(f: (Nothing) -> R): Lce<R> = this
    }

    companion object {
        inline fun <T> exec(copy: (Lce<T>) -> Unit, f: () -> T) {
            copy(Lce.Loading)
            try {
                copy(Lce.Success(f()))
            } catch (e: Exception) {
                copy(Lce.Error(e))
            }
        }
    }
}

fun <T> Lce<T>.orElse(defaultValue: T): T = (this as? Lce.Success)?.data ?: defaultValue