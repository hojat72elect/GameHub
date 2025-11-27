package ca.six.hojat.gamehub.core.providers

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

interface StringProvider {

    fun getString(id: Int, vararg args: Any): String

    fun getQuantityString(id: Int, quantity: Int, vararg formatArgs: Any): String
}

internal class StringProviderImpl @Inject constructor(
    @ApplicationContext private val context: Context,
) : StringProvider {

    override fun getString(id: Int, vararg args: Any): String {
        return context.getString(id, *args)
    }

    override fun getQuantityString(id: Int, quantity: Int, vararg formatArgs: Any): String {
        return context.resources.getQuantityString(id, quantity, *formatArgs)
    }
}
