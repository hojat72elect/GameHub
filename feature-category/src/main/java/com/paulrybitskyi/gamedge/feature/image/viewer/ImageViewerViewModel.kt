package com.paulrybitskyi.gamedge.feature.image.viewer

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.paulrybitskyi.gamedge.common.ui.base.BaseViewModel
import com.paulrybitskyi.gamedge.core.providers.StringProvider
import com.paulrybitskyi.gamedge.feature.category.R
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import com.paulrybitskyi.gamedge.core.R as CoreR

internal const val KEY_SELECTED_POSITION = "selected_position"

@HiltViewModel(assistedFactory = ImageViewerViewModel.Factory::class)
internal class ImageViewerViewModel @AssistedInject constructor(
    @Assisted
    route: ImageViewerRoute,
    private val stringProvider: StringProvider,
    private val savedStateHandle: SavedStateHandle,
) : BaseViewModel() {

    @AssistedFactory
    interface Factory {
        fun create(route: ImageViewerRoute): ImageViewerViewModel
    }

    private val args = route.copy(
        title = route.title ?: stringProvider.getString(
            R.string.image_viewer_default_toolbar_title,
        ),
    )

    private val _uiState = MutableStateFlow(createInitialUiState())

    private val currentUiState: ImageViewerUiState
        get() = _uiState.value

    val uiState: StateFlow<ImageViewerUiState> = _uiState.asStateFlow()

    init {
        _uiState.update {
            it.copy(
                selectedImageUrlIndex = getSelectedPosition(),
                imageUrls = args.imageUrls,
            )
        }

        observeSelectedPositionChanges()
    }

    private fun createInitialUiState(): ImageViewerUiState {
        return ImageViewerUiState(
            toolbarTitle = "",
            imageUrls = emptyList(),
            selectedImageUrlIndex = 0,
        )
    }

    private fun getSelectedPosition(): Int {
        return savedStateHandle[KEY_SELECTED_POSITION] ?: checkNotNull(args.initialPosition)
    }

    private fun observeSelectedPositionChanges() {
        uiState
            .map { it.selectedImageUrlIndex }
            .distinctUntilChanged()
            .onEach { selectedImageUrlIndex ->
                _uiState.update { it.copy(toolbarTitle = updateToolbarTitle()) }
                savedStateHandle[KEY_SELECTED_POSITION] = selectedImageUrlIndex
            }
            .launchIn(viewModelScope)
    }

    private fun updateToolbarTitle(): String {
        if (currentUiState.imageUrls.size == 1) return args.requireTitle()

        return stringProvider.getString(
            R.string.image_viewer_toolbar_title_template,
            args.requireTitle(),
            (currentUiState.selectedImageUrlIndex + 1),
            currentUiState.imageUrls.size,
        )
    }

    fun onToolbarRightButtonClicked() {
        val currentImageUrl = currentUiState.imageUrls[currentUiState.selectedImageUrlIndex]
        val textToShare = stringProvider.getString(
            CoreR.string.text_sharing_message_template,
            stringProvider.getString(CoreR.string.image),
            currentImageUrl,
        )

        dispatchCommand(ImageViewerCommand.ShareText(textToShare))
    }

    fun onImageChanged(imageIndex: Int) {
        _uiState.update { it.copy(selectedImageUrlIndex = imageIndex) }
    }

    fun onBackPressed() {
        navigate(ImageViewerDirection.Back)
    }
}
