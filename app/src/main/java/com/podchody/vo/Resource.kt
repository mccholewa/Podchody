package com.podchody.vo

/**
 * Created by Misiu on 15.04.2018.
 */

interface GenericResource

sealed class Resource<out T> : GenericResource {

    abstract fun <R> map(f: (T) -> R): Resource<R>

    data class Success<out T>(val data: T) : Resource<T>() {
        override fun <R> map(f: (T) -> R): Resource<R> = Success(f(data))
    }

    data class Error(val message: String) : Resource<Nothing>() {
        constructor(t: Throwable) : this(t.message ?: "")

        override fun <R> map(f: (Nothing) -> R): Resource<R> = this
    }

    object Loading : Resource<Nothing>() {
        override fun <R> map(f: (Nothing) -> R): Resource<R> = this
    }

    object Empty : Resource<Nothing>() {
        override fun <R> map(f: (Nothing) -> R): Resource<R> = this
    }
}

fun <T> Resource<T>.orElse(defaultValue: T): T = (this as? Resource.Success)?.data ?: defaultValue

