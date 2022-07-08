package link.jingweih.jingwei.core.framework.exts

import android.widget.TextView

fun TextView.applyText(newText: CharSequence?) {
    newText.takeUnless { it.isNullOrBlank() }?.let {
        text = newText
    }
}