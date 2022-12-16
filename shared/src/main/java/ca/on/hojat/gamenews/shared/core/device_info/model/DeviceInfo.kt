package ca.on.hojat.gamenews.shared.core.device_info.model

import ca.on.hojat.gamenews.shared.core.device_info.model.screen.ScreenMetrics

data class DeviceInfo(
    val productInfo: ProductInfo,
    val screenMetrics: ScreenMetrics
)