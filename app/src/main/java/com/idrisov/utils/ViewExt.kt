package com.idrisov.utils

import android.content.res.Resources
import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableString
import android.text.style.AbsoluteSizeSpan
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.util.DisplayMetrics
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.annotation.DimenRes
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import com.google.android.material.textfield.TextInputLayout
import kotlin.math.roundToInt

private val displayMetrics: DisplayMetrics
    get() = Resources.getSystem().displayMetrics

val screenWidth: Int
    get() = displayMetrics.widthPixels

val screenHeight: Int
    get() = displayMetrics.heightPixels

val Int.toDp: Int
    get() = (this * displayMetrics.density).roundToInt()

val Int.toPx: Int
    get() = (this / displayMetrics.density).roundToInt()

fun View.makeGone() {
    visibility = View.GONE
}

fun View.makeVisible() {
    visibility = View.VISIBLE
}

fun View.makeInvisible() {
    visibility = View.INVISIBLE
}

fun TextView.setTextAsterisk() {
    this.text = "${this.text}*"
}

fun EditText.setHintAsterisk() {
    this.hint = "${this.hint}*"
}

fun TextInputLayout.setHintAsterisk() {
    this.hint = "${this.hint}*"
}

fun View.setMargins(
    top: Int = 0,
    bottom: Int = 0,
    start: Int = 0,
    end: Int = 0
) {

    val layoutParams = LinearLayout.LayoutParams(
        ViewGroup.LayoutParams.WRAP_CONTENT,
        ViewGroup.LayoutParams.WRAP_CONTENT
    )

    layoutParams.setMargins(start, top, end, bottom)
    setLayoutParams(layoutParams)
}

fun AppCompatTextView.setSpanColor(
    subString: String,
    @ColorRes color: Int,
    @DimenRes size: Int? = null,
    bold: Boolean = false
) {

    val start = text.length - subString.length
    val end = text.length

    val span = SpannableString(text)
    span.setSpan(
        /* what = */ ForegroundColorSpan(/* color = */ ContextCompat.getColor(context, color)),
        /* start = */ start, /* end = */ end,
        /* flags = */ Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
    )

    if (bold) {
        span.setSpan(
            /* what = */ StyleSpan(/* style = */ Typeface.BOLD),
            /* start = */ start,
            /* end = */ end,
            /* flags = */ Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
    }

    size?.let {
        span.setSpan(
            /* what = */ AbsoluteSizeSpan(/* size = */ resources.getDimensionPixelSize(size)),
            /* start = */ start, /* end = */ end,
            /* flags = */ Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
    }

    text = span
}