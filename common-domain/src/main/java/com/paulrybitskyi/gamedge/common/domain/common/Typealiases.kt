
package com.paulrybitskyi.gamedge.common.domain.common

import com.github.michaelbull.result.Result
import com.paulrybitskyi.gamedge.common.domain.common.entities.Error

typealias DomainResult<T> = Result<T, Error>
