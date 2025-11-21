package com.paulrybitskyi.gamedge.common.testing

import com.paulrybitskyi.gamedge.core.ErrorMapper

class FakeErrorMapper : ErrorMapper {

    override fun mapToMessage(error: Throwable): String {
        return "error"
    }
}
