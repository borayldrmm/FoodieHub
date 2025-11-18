package com.borayildirim.foodiehub.presentation.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.borayildirim.foodiehub.R
import com.borayildirim.foodiehub.data.local.preferences.UserPreferencesManager
import com.borayildirim.foodiehub.domain.model.Address
import com.borayildirim.foodiehub.domain.model.AddressType
import com.borayildirim.foodiehub.domain.usecase.address.InsertAddressUseCase
import com.borayildirim.foodiehub.domain.usecase.address.UpdateAddressUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.lang.Exception
import java.util.UUID
import javax.inject.Inject

data class AddAddressUiState(
    val title: String = "",
    val addressType: AddressType = AddressType.HOME,
    val fullAddress: String = "",
    val city: String = "",
    val district: String = "",
    val zipCode: String = "",
    val phoneNumber: String = "",
    val isDefault: Boolean = false,
    val isLoading: Boolean = false,
    val isSaved: Boolean = false,
    val error: String? = null
)

@HiltViewModel
class AddAddressViewModel @Inject constructor(
    private val insertAddressUseCase: InsertAddressUseCase,     // To add new address
    private val updateAddressUseCase: UpdateAddressUseCase,     // To update current address
    private val userPreferencesManager: UserPreferencesManager  // To get userId
) : ViewModel() {

    private val _uiState = MutableStateFlow(AddAddressUiState())
    val uiState = _uiState.asStateFlow()

    fun onTitleChange(title: String) {
        _uiState.value = _uiState.value.copy(title = title, error = null)
    }

    fun onAddressTypeChange(addressType: AddressType) {
        _uiState.value = _uiState.value.copy(addressType = addressType, error = null)
    }

    fun onFullAddressChange(address: String) {
        _uiState.value = _uiState.value.copy(fullAddress = address, error = null)
    }

    fun onCityChange(city: String) {
        _uiState.value = _uiState.value.copy(city = city, error = null)
    }

    fun onDistrictChange(district: String) {
        _uiState.value = _uiState.value.copy(district = district, error = null)
    }

    fun onZipCodeChange(zipCode: String) {
        _uiState.value = _uiState.value.copy(zipCode = zipCode, error = null)
    }

    fun onPhoneNumberChange(phoneNumber: String) {
        // Only allow digits, max 10
        val digits = phoneNumber.filter { it.isDigit() }.take(10)
        _uiState.value = _uiState.value.copy(phoneNumber = digits, error = null)
    }

    fun onIsDefaultChange(isDefault: Boolean) {
        _uiState.value = _uiState.value.copy(isDefault = isDefault)
    }

    fun saveAddress(context: Context) {
        val state = _uiState.value

        // Validation
        when {
            state.title.isEmpty() || state.fullAddress.isEmpty() ||state.city.isEmpty() || state.district.isEmpty() || state.phoneNumber.isEmpty() -> {
                _uiState.value = state.copy(error = context.getString(R.string.fill_all_fields))
                return
            }
            state.phoneNumber.length != 10 -> {
                _uiState.value = state.copy(error = context.getString(R.string.phone_mustbe_10))
                return
            }
        }

        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)

            try {
                val userId = userPreferencesManager.getUserId().first()

                if (userId != null) {
                    val newAddress = Address(
                        id = UUID.randomUUID().toString(),
                        userId = userId,
                        title = state.title,
                        addressType = state.addressType,
                        fullAddress = state.fullAddress,
                        city = state.city,
                        district = state.district,
                        zipCode = state.zipCode.ifEmpty { null },
                        phoneNumber = state.phoneNumber,
                        isDefault = state.isDefault,
                        createdAt = System.currentTimeMillis(),
                        updatedAt = System.currentTimeMillis()
                    )

                    insertAddressUseCase.invoke(newAddress)

                    _uiState.value = state.copy(
                        isSaved = true,
                        isLoading = false,
                    )
                } else {
                    _uiState.value = state.copy(
                        isLoading = false,
                        error = "Kullanıcı bulunamadı"
                    )
                }
            } catch (e: Exception) {
                _uiState.value = state.copy(
                    isLoading = false,
                    error = e.message ?: context.getString(R.string.unknown_error)
                )
            }
        }
    }
}