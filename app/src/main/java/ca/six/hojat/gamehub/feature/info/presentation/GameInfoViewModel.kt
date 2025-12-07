package ca.six.hojat.gamehub.feature.info.presentation

import androidx.lifecycle.viewModelScope
import ca.six.hojat.gamehub.R
import ca.six.hojat.gamehub.common.domain.common.DispatcherProvider
import ca.six.hojat.gamehub.common.domain.common.extensions.resultOrError
import ca.six.hojat.gamehub.common.ui.base.BaseViewModel
import ca.six.hojat.gamehub.common.ui.base.events.common.GeneralCommand
import ca.six.hojat.gamehub.common.ui.di.qualifiers.TransitionAnimationDuration
import ca.six.hojat.gamehub.core.ErrorMapper
import ca.six.hojat.gamehub.core.Logger
import ca.six.hojat.gamehub.core.providers.StringProvider
import ca.six.hojat.gamehub.core.utils.onError
import ca.six.hojat.gamehub.feature.info.domain.entities.GameImageType
import ca.six.hojat.gamehub.feature.info.domain.usecases.GetGameImageUrlsUseCase
import ca.six.hojat.gamehub.feature.info.domain.usecases.GetGameInfoUseCase
import ca.six.hojat.gamehub.feature.info.domain.usecases.likes.ToggleGameLikeStateUseCase
import ca.six.hojat.gamehub.feature.info.presentation.widgets.companies.GameInfoCompanyUiModel
import ca.six.hojat.gamehub.feature.info.presentation.widgets.links.GameInfoLinkUiModel
import ca.six.hojat.gamehub.feature.info.presentation.widgets.main.GameInfoUiModelMapper
import ca.six.hojat.gamehub.feature.info.presentation.widgets.relatedgames.GameInfoRelatedGameUiModel
import ca.six.hojat.gamehub.feature.info.presentation.widgets.videos.GameInfoVideoUiModel
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel(assistedFactory = GameInfoViewModel.Factory::class)
@Suppress("LongParameterList")
internal class GameInfoViewModel @AssistedInject constructor(
    @TransitionAnimationDuration
    transitionAnimationDuration: Long,
    @Assisted
    private val route: GameInfoScreenRoute,
    private val useCases: GameInfoUseCases,
    private val uiModelMapper: GameInfoUiModelMapper,
    private val dispatcherProvider: DispatcherProvider,
    private val stringProvider: StringProvider,
    private val errorMapper: ErrorMapper,
    private val logger: Logger,
) : BaseViewModel() {

    @AssistedFactory
    interface Factory {
        fun create(route: GameInfoScreenRoute): GameInfoViewModel
    }

    private var isObservingGameData = false

    private val _uiState = MutableStateFlow(GameInfoUiState(isLoading = false, game = null))

    private val currentUiState: GameInfoUiState
        get() = _uiState.value

    val uiState: StateFlow<GameInfoUiState> = _uiState.asStateFlow()

    init {
        observeGameInfo(resultEmissionDelay = transitionAnimationDuration)
    }

    private fun observeGameInfo(resultEmissionDelay: Long) {
        if (isObservingGameData) return

        viewModelScope.launch {
            observeGameInfoInternal(resultEmissionDelay)
        }
    }

    private suspend fun observeGameInfoInternal(resultEmissionDelay: Long) {
        useCases.getGameInfoUseCase.execute(GetGameInfoUseCase.Params(route.gameId))
            .map(uiModelMapper::mapToUiModel)
            .flowOn(dispatcherProvider.computation)
            .map { game -> currentUiState.toSuccessState(game) }
            .onError {
                logger.error(logTag, "Failed to load game info data.", it)
                dispatchCommand(GeneralCommand.ShowLongToast(errorMapper.mapToMessage(it)))
                emit(currentUiState.toEmptyState())
            }
            .onStart {
                isObservingGameData = true
                emit(currentUiState.toLoadingState())
                delay(resultEmissionDelay)
            }
            .onCompletion { isObservingGameData = false }
            .collect { emittedUiState -> _uiState.update { emittedUiState } }
    }

    fun onArtworkClicked(artworkIndex: Int) {
        navigateToImageViewer(
            title = stringProvider.getString(R.string.artwork),
            imageType = GameImageType.ARTWORK,
            initialPosition = artworkIndex,
        )
    }

    private fun navigateToImageViewer(
        title: String,
        imageType: GameImageType,
        initialPosition: Int = 0,
    ) {
        viewModelScope.launch {
            useCases.getGameImageUrlsUseCase.execute(
                GetGameImageUrlsUseCase.Params(
                    gameId = route.gameId,
                    imageType = imageType,
                ),
            )
                .resultOrError()
                .onError {
                    logger.error(logTag, "Failed to get the image urls of type = $imageType.", it)
                    dispatchCommand(GeneralCommand.ShowLongToast(errorMapper.mapToMessage(it)))
                }
                .collect { imageUrls ->
                    navigate(GameInfoDirection.ImageViewer(imageUrls, title, initialPosition))
                }
        }
    }

    fun onBackButtonClicked() {
        navigate(GameInfoDirection.Back)
    }

    fun onCoverClicked() {
        navigateToImageViewer(
            title = stringProvider.getString(R.string.cover),
            imageType = GameImageType.COVER,
        )
    }

    fun onLikeButtonClicked() {
        viewModelScope.launch {
            useCases.toggleGameLikeStateUseCase
                .execute(ToggleGameLikeStateUseCase.Params(route.gameId))
        }
    }

    fun onVideoClicked(video: GameInfoVideoUiModel) {
        openUrl(video.videoUrl)
    }

    fun onScreenshotClicked(screenshotIndex: Int) {
        navigateToImageViewer(
            title = stringProvider.getString(R.string.screenshot),
            imageType = GameImageType.SCREENSHOT,
            initialPosition = screenshotIndex,
        )
    }

    fun onLinkClicked(link: GameInfoLinkUiModel) {
        openUrl(link.url)
    }

    fun onCompanyClicked(company: GameInfoCompanyUiModel) {
        openUrl(company.websiteUrl)
    }

    private fun openUrl(url: String) {
        dispatchCommand(GameInfoCommand.OpenUrl(url))
    }

    fun onRelatedGameClicked(game: GameInfoRelatedGameUiModel) {
        navigate(GameInfoDirection.Info(gameId = game.id))
    }
}
