package ca.six.hojat.gamehub.common.ui.images

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import coil.request.ImageRequest

private const val CROSSFADE_ANIMATION_DURATION = 200

@Composable
fun defaultImageRequest(
    data: Any?,
    builder: ImageRequest.Builder.() -> Unit,
): ImageRequest {
    return ImageRequest.Builder(LocalContext.current)
        .data(data)
        .crossfade(CROSSFADE_ANIMATION_DURATION)
        .apply(builder)
        .build()
}

fun ImageRequest.Builder.secondaryImage(
    @DrawableRes drawableResId: Int,
): ImageRequest.Builder = apply {
    placeholder(drawableResId)
    fallback(drawableResId)
    error(drawableResId)
}
