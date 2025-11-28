package ca.six.hojat.gamehub.common.extensions

import android.view.View

fun View.onClick(action: (View) -> Unit) {
    setOnClickListener(action)
}

fun View.postAction(action: () -> Unit) {
    post(action)
}