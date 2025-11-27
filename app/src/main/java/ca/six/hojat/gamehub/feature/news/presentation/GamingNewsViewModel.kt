package ca.six.hojat.gamehub.feature.news.presentation

import androidx.lifecycle.viewModelScope
import ca.six.hojat.gamehub.common.domain.common.DispatcherProvider
import ca.six.hojat.gamehub.common.domain.common.entities.Pagination
import ca.six.hojat.gamehub.common.domain.common.extensions.resultOrError
import ca.six.hojat.gamehub.common.ui.base.BaseViewModel
import ca.six.hojat.gamehub.common.ui.base.events.common.GeneralCommand
import ca.six.hojat.gamehub.core.ErrorMapper
import ca.six.hojat.gamehub.core.Logger
import ca.six.hojat.gamehub.core.utils.onError
import ca.six.hojat.gamehub.feature.news.domain.usecases.ObserveArticlesUseCase
import ca.six.hojat.gamehub.feature.news.domain.usecases.RefreshArticlesUseCase
import ca.six.hojat.gamehub.feature.news.presentation.mapping.GamingNewsItemUiModelMapper
import ca.six.hojat.gamehub.feature.news.presentation.mapping.mapToUiModels
import ca.six.hojat.gamehub.feature.news.presentation.widgets.GamingNewsItemUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import javax.inject.Inject

private const val MAX_ARTICLE_COUNT = 100
private const val ARTICLES_REFRESH_INITIAL_DELAY = 500L
private const val ARTICLES_REFRESH_DEFAULT_DELAY = 1000L

@HiltViewModel
internal class GamingNewsViewModel @Inject constructor(
    private val observeArticlesUseCase: ObserveArticlesUseCase,
    private val refreshArticlesUseCase: RefreshArticlesUseCase,
    private val uiModelMapper: GamingNewsItemUiModelMapper,
    private val dispatcherProvider: DispatcherProvider,
    private val errorMapper: ErrorMapper,
    private val logger: Logger,
) : BaseViewModel() {

    private var isObservingArticles = false

    private lateinit var observerUseCaseParams: ObserveArticlesUseCase.Params
    private lateinit var refresherUseCaseParams: RefreshArticlesUseCase.Params

    private val _uiState = MutableStateFlow(GamingNewsUiState())

    private val currentUiState: GamingNewsUiState
        get() = _uiState.value

    val uiState: StateFlow<GamingNewsUiState> = _uiState.asStateFlow()

    init {
        initUseCaseParams()
        observeArticles()
        refreshArticles(isFirstRefresh = true)
    }

    private fun initUseCaseParams() {
        val pagination = Pagination(limit = MAX_ARTICLE_COUNT)

        observerUseCaseParams = ObserveArticlesUseCase.Params(pagination)
        refresherUseCaseParams = RefreshArticlesUseCase.Params(pagination)
    }

    private fun observeArticles() {
        if (isObservingArticles) return

        observeArticlesUseCase.execute(observerUseCaseParams)
            .map(uiModelMapper::mapToUiModels)
            .flowOn(dispatcherProvider.computation)
            .map { news -> currentUiState.toSuccessState(news) }
            .onError {
                logger.error(logTag, "Failed to load articles.", it)
                dispatchCommand(GeneralCommand.ShowLongToast(errorMapper.mapToMessage(it)))
                emit(currentUiState.toEmptyState())
            }
            .onStart {
                isObservingArticles = true
                emit(currentUiState.toLoadingState())
            }
            .onCompletion { isObservingArticles = false }
            .onEach { emittedUiState -> _uiState.update { emittedUiState } }
            .launchIn(viewModelScope)
    }

    fun onNewsItemClicked(model: GamingNewsItemUiModel) {
        dispatchCommand(GamingNewsCommand.OpenUrl(model.siteDetailUrl))
    }

    fun onRefreshRequested() {
        if (!currentUiState.isRefreshing) {
            refreshArticles(isFirstRefresh = false)
        }
    }

    private fun refreshArticles(isFirstRefresh: Boolean) {
        refreshArticlesUseCase.execute(refresherUseCaseParams)
            .resultOrError()
            .map { currentUiState }
            .onError {
                logger.error(logTag, "Failed to refresh articles.", it)
                dispatchCommand(GeneralCommand.ShowLongToast(errorMapper.mapToMessage(it)))
            }
            .onStart {
                // Adding the delay on the first refresh to wait until the cached
                // articles are loaded and not overload the UI with loaders
                if (isFirstRefresh) {
                    delay(ARTICLES_REFRESH_INITIAL_DELAY)
                }

                emit(currentUiState.enableRefreshing())
                // Adding a delay to prevent the PullRefresh from disappearing quickly
                delay(ARTICLES_REFRESH_DEFAULT_DELAY)
            }
            .onCompletion {
                emit(currentUiState.disableRefreshing())
            }
            .onEach { emittedUiState -> _uiState.update { emittedUiState } }
            .launchIn(viewModelScope)
    }
}
