package andersen.two_clues.utills

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper

fun Context.findActivity(): Activity? = when (this) {
    is Activity -> this
    is ContextWrapper -> baseContext.findActivity()
    else -> null
}

fun <A, B> Pair<A, B>.compare(second: Pair<A, B>): Boolean {
    return this.toList().containsAll(second.toList())
}