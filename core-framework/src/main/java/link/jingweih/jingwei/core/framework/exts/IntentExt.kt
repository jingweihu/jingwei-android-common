package link.jingweih.jingwei.core.framework.exts

import android.content.Intent
import androidx.fragment.app.Fragment

fun Fragment.getIntent(): Intent? {
    return activity?.intent
}