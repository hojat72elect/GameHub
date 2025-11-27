package ca.six.hojat.gamehub.core.utils

import android.content.Context
import android.content.Intent
import androidx.activity.ComponentActivity

fun Intent.attachNewTaskFlagIfNeeded(context: Context) {
    if (context !is ComponentActivity) {
        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    }
}
