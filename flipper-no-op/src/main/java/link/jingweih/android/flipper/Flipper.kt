package link.jingweih.android.flipper

import android.content.Context
import okhttp3.Interceptor

object Flipper {

    fun init(context: Context, config: FlipperConfig.() -> Unit) = Unit

    fun getNetworkInterceptor(): Interceptor? {
        return null
    }
}