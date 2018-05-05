package com.podchody.util

/**
 * Created by Misiu on 24.04.2018.
 */
interface OnBackPressedListener {

    /**
     * Callback, which is called if the Back Button is pressed.
     * Fragments that extend MainFragment can/should override this Method.
     *
     * @return true if the App can be closed, false otherwise
     */
    fun onBackPressed(): Boolean
}