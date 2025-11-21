
package com.paulrybitskyi.gamedge.common.testing.domain

import io.mockk.MockKVerificationScope
import io.mockk.Ordering
import io.mockk.coVerify

@Suppress("LongParameterList")
fun coVerifyNotCalled(
    ordering: Ordering = Ordering.UNORDERED,
    inverse: Boolean = false,
    atLeast: Int = 1,
    atMost: Int = Int.MAX_VALUE,
    timeout: Long = 0,
    verifyBlock: suspend MockKVerificationScope.() -> Unit,
) {
    coVerify(
        ordering = ordering,
        inverse = inverse,
        atLeast = atLeast,
        atMost = atMost,
        exactly = 0,
        timeout = timeout,
        verifyBlock = verifyBlock,
    )
}
