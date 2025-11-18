package com.borayildirim.foodiehub.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.borayildirim.foodiehub.data.local.preferences.UserPreferencesManager
import com.borayildirim.foodiehub.domain.model.Address
import com.borayildirim.foodiehub.domain.usecase.address.DeleteAddressUseCase
import com.borayildirim.foodiehub.domain.usecase.address.GetUserAddressesUseCase
import com.borayildirim.foodiehub.domain.usecase.address.SetDefaultAddressUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

data class AddressListUiState(
    val addresses: List<Address> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)

@HiltViewModel
class AddressListViewModel @Inject constructor(
    private val getUserAddressesUseCase: GetUserAddressesUseCase,
    private val deleteAddressUseCase: DeleteAddressUseCase,
    private val setDefaultAddressUseCase: SetDefaultAddressUseCase,
    private val userPreferencesManager: UserPreferencesManager
) : ViewModel() {

    private val _uiState = MutableStateFlow(AddressListUiState())
    val uiState = _uiState.asStateFlow()

    init {
        loadAddresses()
    }

    private fun loadAddresses() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)

            try {
                val userId = userPreferencesManager.getUserId().first()

                if (userId != null) {
                    getUserAddressesUseCase(userId).collect { addresses ->
                        _uiState.value = AddressListUiState(
                            addresses = addresses,
                            isLoading = false,
                            error = null
                        )
                    }
                } else {
                    _uiState.value = AddressListUiState(
                        addresses = emptyList(),
                        isLoading = false,
                        error = "Kullanıcı bulunamadı"
                    )
                }
            } catch (e: Exception) {
                _uiState.value = AddressListUiState(
                    addresses = emptyList(),
                    isLoading = false,
                    error = e.message
                )
            }
        }
    }

    fun deleteAddress(address: Address) {
        viewModelScope.launch {
            try {
                deleteAddressUseCase(address)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(error = e.message)
            }
        }
    }

    fun setDefaultAddress(addressId: String) {
        viewModelScope.launch {
            try {
                val userId = userPreferencesManager.getUserId().first()
                if (userId != null) {
                    setDefaultAddressUseCase(userId, addressId)
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(error = e.message)
            }
        }
    }

}