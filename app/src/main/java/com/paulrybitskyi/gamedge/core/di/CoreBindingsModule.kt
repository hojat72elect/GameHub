package com.paulrybitskyi.gamedge.core.di

import com.paulrybitskyi.gamedge.core.ErrorMapper
import com.paulrybitskyi.gamedge.core.ErrorMapperImpl
import com.paulrybitskyi.gamedge.core.GameLikeCountCalculator
import com.paulrybitskyi.gamedge.core.GameLikeCountCalculatorImpl
import com.paulrybitskyi.gamedge.core.GamedgeLogger
import com.paulrybitskyi.gamedge.core.Logger
import com.paulrybitskyi.gamedge.core.factories.IgdbImageUrlFactory
import com.paulrybitskyi.gamedge.core.factories.IgdbImageUrlFactoryImpl
import com.paulrybitskyi.gamedge.core.factories.ImageViewerGameUrlFactory
import com.paulrybitskyi.gamedge.core.factories.ImageViewerGameUrlFactoryImpl
import com.paulrybitskyi.gamedge.core.factories.YoutubeMediaUrlFactory
import com.paulrybitskyi.gamedge.core.factories.YoutubeMediaUrlFactoryImpl
import com.paulrybitskyi.gamedge.core.formatters.ArticlePublicationDateFormatter
import com.paulrybitskyi.gamedge.core.formatters.ArticlePublicationDateFormatterImpl
import com.paulrybitskyi.gamedge.core.formatters.GameAgeRatingFormatter
import com.paulrybitskyi.gamedge.core.formatters.GameAgeRatingFormatterImpl
import com.paulrybitskyi.gamedge.core.formatters.GameCategoryFormatter
import com.paulrybitskyi.gamedge.core.formatters.GameCategoryFormatterImpl
import com.paulrybitskyi.gamedge.core.formatters.GameRatingFormatter
import com.paulrybitskyi.gamedge.core.formatters.GameRatingFormatterImpl
import com.paulrybitskyi.gamedge.core.formatters.GameReleaseDateFormatter
import com.paulrybitskyi.gamedge.core.formatters.GameReleaseDateFormatterImpl
import com.paulrybitskyi.gamedge.core.formatters.RelativeDateFormatter
import com.paulrybitskyi.gamedge.core.formatters.RelativeDateFormatterImpl
import com.paulrybitskyi.gamedge.core.providers.CustomTabsProvider
import com.paulrybitskyi.gamedge.core.providers.CustomTabsProviderImpl
import com.paulrybitskyi.gamedge.core.providers.LocaleProvider
import com.paulrybitskyi.gamedge.core.providers.LocaleProviderImpl
import com.paulrybitskyi.gamedge.core.providers.NetworkStateProvider
import com.paulrybitskyi.gamedge.core.providers.NetworkStateProviderImpl
import com.paulrybitskyi.gamedge.core.providers.StringProvider
import com.paulrybitskyi.gamedge.core.providers.StringProviderImpl
import com.paulrybitskyi.gamedge.core.providers.TimeFormatProvider
import com.paulrybitskyi.gamedge.core.providers.TimeFormatProviderImpl
import com.paulrybitskyi.gamedge.core.providers.TimeProvider
import com.paulrybitskyi.gamedge.core.providers.TimeProviderImpl
import com.paulrybitskyi.gamedge.core.providers.TimestampProvider
import com.paulrybitskyi.gamedge.core.providers.TimestampProviderImpl
import com.paulrybitskyi.gamedge.core.providers.VersionNameProvider
import com.paulrybitskyi.gamedge.core.providers.VersionNameProviderImpl
import com.paulrybitskyi.gamedge.core.providers.WebsiteIconProvider
import com.paulrybitskyi.gamedge.core.providers.WebsiteIconProviderImpl
import com.paulrybitskyi.gamedge.core.providers.WebsiteNameProvider
import com.paulrybitskyi.gamedge.core.providers.WebsiteNameProviderImpl
import com.paulrybitskyi.gamedge.core.sharers.TextSharer
import com.paulrybitskyi.gamedge.core.sharers.TextSharerImpl
import com.paulrybitskyi.gamedge.core.urlopener.BrowserUrlOpener
import com.paulrybitskyi.gamedge.core.urlopener.CompositeUrlOpener
import com.paulrybitskyi.gamedge.core.urlopener.CustomTabUrlOpener
import com.paulrybitskyi.gamedge.core.urlopener.NativeAppUrlOpener
import com.paulrybitskyi.gamedge.core.urlopener.UrlOpener
import com.paulrybitskyi.gamedge.core.urlopener.UrlOpenerKey
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoMap
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal interface CoreBindingsModule {

    @Binds
    @Singleton
    fun bindCustomTabsProvider(binding: CustomTabsProviderImpl): CustomTabsProvider

    @Binds
    fun bindTimestampProvider(binding: TimestampProviderImpl): TimestampProvider

    @Binds
    fun bindVersionNameProvider(binding: VersionNameProviderImpl): VersionNameProvider

    @Binds
    fun bindCompositeUrlOpener(binding: CompositeUrlOpener): UrlOpener

    @Binds
    @IntoMap
    @UrlOpenerKey(UrlOpenerKey.Type.CUSTOM_TAB)
    fun bindCustomTabUrlOpener(binding: CustomTabUrlOpener): UrlOpener

    @Binds
    @IntoMap
    @UrlOpenerKey(UrlOpenerKey.Type.BROWSER)
    fun bindBrowserUrlOpener(binding: BrowserUrlOpener): UrlOpener

    @Binds
    @IntoMap
    @UrlOpenerKey(UrlOpenerKey.Type.NATIVE_APP)
    fun bindNativeAppUrlOpener(binding: NativeAppUrlOpener): UrlOpener

    @Binds
    fun bindYoutubeMediaUrlFactory(binding: YoutubeMediaUrlFactoryImpl): YoutubeMediaUrlFactory

    @Binds
    fun bindGameCategoryFormatter(binding: GameCategoryFormatterImpl): GameCategoryFormatter

    @Binds
    fun bindWebsiteIconProvider(binding: WebsiteIconProviderImpl): WebsiteIconProvider

    @Binds
    fun bindNetworkStateProvider(binding: NetworkStateProviderImpl): NetworkStateProvider

    @Binds
    fun bindRelativeDateFormatter(binding: RelativeDateFormatterImpl): RelativeDateFormatter

    @Binds
    fun bindTextSharer(binding: TextSharerImpl): TextSharer

    @Binds
    fun bindImageViewerGameUrlFactory(binding: ImageViewerGameUrlFactoryImpl): ImageViewerGameUrlFactory

    @Binds
    fun bindIgdbImageUrlFactory(binding: IgdbImageUrlFactoryImpl): IgdbImageUrlFactory

    @Binds
    fun bindGameAgeRatingFormatter(binding: GameAgeRatingFormatterImpl): GameAgeRatingFormatter

    @Binds
    fun bindGameReleaseDateFormatter(binding: GameReleaseDateFormatterImpl): GameReleaseDateFormatter

    @Binds
    fun bindErrorMapper(binding: ErrorMapperImpl): ErrorMapper

    @Binds
    fun bindLogger(binding: GamedgeLogger): Logger

    @Binds
    fun bindStringProvider(binding: StringProviderImpl): StringProvider

    @Binds
    fun bindGameLikeCountCalculator(binding: GameLikeCountCalculatorImpl): GameLikeCountCalculator

    @Binds
    fun bindGameRatingFormatter(binding: GameRatingFormatterImpl): GameRatingFormatter

    @Binds
    fun bindTimeFormatProvider(binding: TimeFormatProviderImpl): TimeFormatProvider

    @Binds
    fun bindLocaleProvider(binding: LocaleProviderImpl): LocaleProvider

    @Binds
    fun bindWebsiteNameProvider(binding: WebsiteNameProviderImpl): WebsiteNameProvider

    @Binds
    fun bindTimeProvider(binding: TimeProviderImpl): TimeProvider

    @Binds
    fun bindArticlePublicationDateFormatter(binding: ArticlePublicationDateFormatterImpl): ArticlePublicationDateFormatter
}
