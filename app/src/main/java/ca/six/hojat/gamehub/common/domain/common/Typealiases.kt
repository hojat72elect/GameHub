package ca.six.hojat.gamehub.common.domain.common

import ca.six.hojat.gamehub.common.domain.common.entities.Error
import com.github.michaelbull.result.Result

typealias DomainResult<T> = Result<T, Error>
