package com.paulrybitskyi.gamedge.core.providers

import android.content.Context
import com.paulrybitskyi.gamedge.R
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

interface VersionNameProvider {
    fun getVersionName(): String
}

internal class VersionNameProviderImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val stringProvider: StringProvider,
) : VersionNameProvider {

    override fun getVersionName(): String {
        return stringProvider.getString(
            R.string.version_name_template,
            checkNotNull(context.packageManager.getPackageInfo(context.packageName, 0).versionName) {
                "The version name must not be null."
            },
        )
    }
}
