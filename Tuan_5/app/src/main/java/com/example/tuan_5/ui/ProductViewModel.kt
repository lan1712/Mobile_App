package com.example.tuan_5.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apidemo.model.Product
import com.example.tuan_5.network.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class UiState(
    val loading: Boolean = false,
    val data: List<Product> = emptyList(),
    val error: String? = null
)

class ProductViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState

    fun loadProducts() {
        // nếu đã có data rồi thì không gọi lại nữa
        if (_uiState.value.data.isNotEmpty()) return

        _uiState.value = UiState(loading = true)

        viewModelScope.launch {
            try {
                val result = RetrofitClient.api.getProducts()
                _uiState.value = UiState(
                    loading = false,
                    data = result,
                    error = null
                )
            } catch (e: Exception) {
                _uiState.value = UiState(
                    loading = false,
                    data = emptyList(),
                    error = e.message ?: "Lỗi không xác định"
                )
            }
        }
    }
}
