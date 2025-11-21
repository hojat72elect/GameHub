package com.paulrybitskyi.gamedge.common.testing.domain

import com.paulrybitskyi.gamedge.common.domain.common.DispatcherProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.rules.TestWatcher
import org.junit.runner.Description

class MainCoroutineRule(
    private val dispatcher: TestDispatcher = UnconfinedTestDispatcher(),
) : TestWatcher() {

    val dispatcherProvider = object : DispatcherProvider {
        override val main = dispatcher
        override val io = dispatcher
        override val computation = dispatcher
    }

    override fun starting(description: Description) {
        super.starting(description)

        Dispatchers.setMain(dispatcher)
    }

    override fun finished(description: Description) {
        super.finished(description)

        Dispatchers.resetMain()
    }
}
