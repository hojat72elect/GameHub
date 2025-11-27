package ca.six.hojat.gamehub.common.api

import okhttp3.Interceptor
import okhttp3.OkHttpClient

fun OkHttpClient.Builder.addInterceptorAsFirstInChain(
    interceptor: Interceptor,
): OkHttpClient.Builder = apply {
    interceptors().add(0, interceptor)
}
