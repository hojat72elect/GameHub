package ca.six.hojat.gamehub.gamespot.api.common

import ca.six.hojat.gamehub.common.api.HttpHeaders
import ca.six.hojat.gamehub.common.api.UserAgentProvider
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

internal class UserAgentInterceptor @Inject constructor(
    private val userAgentProvider: UserAgentProvider,
) : Interceptor {

    private val userAgent by lazy {
        userAgentProvider.getUserAgent()
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val modifiedRequest = chain.request()
            .newBuilder()
            .header(HttpHeaders.USER_AGENT, userAgent)
            .build()

        return chain.proceed(modifiedRequest)
    }
}
