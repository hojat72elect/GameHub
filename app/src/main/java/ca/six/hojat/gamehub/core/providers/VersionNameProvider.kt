package ca.six.hojat.gamehub.core.providers

import android.content.Context
import ca.six.hojat.gamehub.R
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
