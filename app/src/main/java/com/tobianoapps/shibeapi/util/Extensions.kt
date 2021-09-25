package com.tobianoapps.shibeapi.util

import androidx.fragment.app.FragmentActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.tobianoapps.shibeapi.R

object Extensions {

    /**
     * Use this function to show or hide a [FloatingActionButton] hosted in a parent
     * [FragmentActivity].
     *
     * Usage:
     *
     * `activity?.toggleFabVisibility(true/false)`
     *
     */
    fun FragmentActivity.toggleFabVisibility(show: Boolean) {
        this.findViewById<FloatingActionButton>(R.id.fab).apply {
            if (show) this.show() else this.hide()
        }
    }
}