package ca.six.hojat.gamehub.core.providers

import java.util.Locale
import javax.inject.Inject

interface LocaleProvider {
    fun getLocale(): Locale
}

internal class LocaleProviderImpl @Inject constructor() : LocaleProvider {

    override fun getLocale(): Locale {
        // App only supports the English language
        return Locale.ENGLISH
    }
}
