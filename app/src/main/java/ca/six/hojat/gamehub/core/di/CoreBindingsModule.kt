package ca.six.hojat.gamehub.core.di

import ca.six.hojat.gamehub.core.ErrorMapper
import ca.six.hojat.gamehub.core.ErrorMapperImpl
import ca.six.hojat.gamehub.core.GameLikeCountCalculator
import ca.six.hojat.gamehub.core.GameLikeCountCalculatorImpl
import ca.six.hojat.gamehub.core.GamedgeLogger
import ca.six.hojat.gamehub.core.Logger
import ca.six.hojat.gamehub.core.factories.IgdbImageUrlFactory
import ca.six.hojat.gamehub.core.factories.IgdbImageUrlFactoryImpl
import ca.six.hojat.gamehub.core.factories.ImageViewerGameUrlFactory
import ca.six.hojat.gamehub.core.factories.ImageViewerGameUrlFactoryImpl
import ca.six.hojat.gamehub.core.factories.YoutubeMediaUrlFactory
import ca.six.hojat.gamehub.core.factories.YoutubeMediaUrlFactoryImpl
import ca.six.hojat.gamehub.core.formatters.ArticlePublicationDateFormatter
import ca.six.hojat.gamehub.core.formatters.ArticlePublicationDateFormatterImpl
import ca.six.hojat.gamehub.core.formatters.GameAgeRatingFormatter
import ca.six.hojat.gamehub.core.formatters.GameAgeRatingFormatterImpl
import ca.six.hojat.gamehub.core.formatters.GameCategoryFormatter
import ca.six.hojat.gamehub.core.formatters.GameCategoryFormatterImpl
import ca.six.hojat.gamehub.core.formatters.GameRatingFormatter
import ca.six.hojat.gamehub.core.formatters.GameRatingFormatterImpl
import ca.six.hojat.gamehub.core.formatters.GameReleaseDateFormatter
import ca.six.hojat.gamehub.core.formatters.GameReleaseDateFormatterImpl
import ca.six.hojat.gamehub.core.formatters.RelativeDateFormatter
import ca.six.hojat.gamehub.core.formatters.RelativeDateFormatterImpl
import ca.six.hojat.gamehub.core.providers.CustomTabsProvider
import ca.six.hojat.gamehub.core.providers.CustomTabsProviderImpl
import ca.six.hojat.gamehub.core.providers.LocaleProvider
import ca.six.hojat.gamehub.core.providers.LocaleProviderImpl
import ca.six.hojat.gamehub.core.providers.NetworkStateProvider
import ca.six.hojat.gamehub.core.providers.NetworkStateProviderImpl
import ca.six.hojat.gamehub.core.providers.StringProvider
import ca.six.hojat.gamehub.core.providers.StringProviderImpl
import ca.six.hojat.gamehub.core.providers.TimeFormatProvider
import ca.six.hojat.gamehub.core.providers.TimeFormatProviderImpl
import ca.six.hojat.gamehub.core.providers.TimeProvider
import ca.six.hojat.gamehub.core.providers.TimeProviderImpl
import ca.six.hojat.gamehub.core.providers.TimestampProvider
import ca.six.hojat.gamehub.core.providers.TimestampProviderImpl
import ca.six.hojat.gamehub.core.providers.VersionNameProvider
import ca.six.hojat.gamehub.core.providers.VersionNameProviderImpl
import ca.six.hojat.gamehub.core.providers.WebsiteIconProvider
import ca.six.hojat.gamehub.core.providers.WebsiteIconProviderImpl
import ca.six.hojat.gamehub.core.providers.WebsiteNameProvider
import ca.six.hojat.gamehub.core.providers.WebsiteNameProviderImpl
import ca.six.hojat.gamehub.core.sharers.TextSharer
import ca.six.hojat.gamehub.core.sharers.TextSharerImpl
import ca.six.hojat.gamehub.core.urlopener.BrowserUrlOpener
import ca.six.hojat.gamehub.core.urlopener.CompositeUrlOpener
import ca.six.hojat.gamehub.core.urlopener.CustomTabUrlOpener
import ca.six.hojat.gamehub.core.urlopener.NativeAppUrlOpener
import ca.six.hojat.gamehub.core.urlopener.UrlOpener
import ca.six.hojat.gamehub.core.urlopener.UrlOpenerKey
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
