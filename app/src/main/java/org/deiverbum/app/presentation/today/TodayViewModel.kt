package org.deiverbum.app.presentation.homily

import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.deiverbum.app.data.networking.CoroutineDispatcherProvider
import org.deiverbum.app.domain.model.BiblicalCommentRequest
import org.deiverbum.app.domain.model.TodayRequest
import org.deiverbum.app.domain.usecase.GetBiblicalComment
import org.deiverbum.app.domain.usecase.GetToday
import org.deiverbum.app.util.ExceptionParser
import javax.inject.Inject

@HiltViewModel
class TodayViewModel @Inject constructor(
    private val getTodayUseCase: GetToday,
    private val coroutineDispatcherProvider: CoroutineDispatcherProvider
) : ViewModel() {

    private val _uiState = MutableStateFlow<TodayUiState>(TodayUiState.Empty)
    val uiState: StateFlow<TodayUiState> = _uiState

    fun loadData() {
        _uiState.value = TodayUiState.Loading
        viewModelScope.launch(coroutineDispatcherProvider.IO()) {
            try {
                val requestParam = TodayRequest("getTodayDate()",0)
                val result = getTodayUseCase.execute(requestParam)
                _uiState.value = TodayUiState.Loaded(TodayItemUiState(result))
            } catch (error: Exception) {
                _uiState.value = TodayUiState.Error(ExceptionParser.getMessage(error))
            }
        }
    }

    sealed class TodayUiState {
        object Empty : TodayUiState()
        object Loading : TodayUiState()
        class Loaded(val itemState: TodayItemUiState) : TodayUiState()
        class Error(@StringRes val message: Int) : TodayUiState()
    }
}