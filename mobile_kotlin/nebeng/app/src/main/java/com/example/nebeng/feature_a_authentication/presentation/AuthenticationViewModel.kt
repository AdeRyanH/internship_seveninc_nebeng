package com.example.nebeng.feature_a_authentication.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nebeng.core.common.Result
import com.example.nebeng.core.session.data.UserPreferencesRepository
import com.example.nebeng.feature_a_authentication.domain.model.AuthenticationItem
import com.example.nebeng.feature_a_authentication.domain.usecase.AuthenticationUseCases
import com.example.nebeng.feature_auth.data.remote.model.request.CreateLoginRequest
import com.example.nebeng.feature_auth.data.remote.model.request.CreateRegisterRequest
import com.example.nebeng.feature_user.data.remote.model.request.UpdateUserRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthenticationViewModel @Inject constructor(
    private val useCases: AuthenticationUseCases,
    private val userPrefsRepo: UserPreferencesRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(AuthenticationUiState())
    val uiState: StateFlow<AuthenticationUiState> = _uiState

    /* ------------------------------------------------------------------
     * 🔥 LOGIN CUSTOMER
     * ------------------------------------------------------------------ */
    fun login(request: CreateLoginRequest) {
        viewModelScope.launch {
            useCases.loginCustomer(request).collectLatest { result ->
                when (result) {
                    is Result.Loading -> _uiState.updateLoading()
                    is Result.Error -> {
                        Log.d("LOGIN_UI", "username='${request.userIdentifier}', password='${request.password}'")
                        _uiState.updateError(result.message)
                    }
                    is Result.Success -> {
                        val auth = result.data

                        // 🔥 DEBUG LOG — tampilkan isi AuthenticationItem
                        Log.d("LOGIN_SUCCESS", """
                            LOGIN SUCCESS:
                            userId       = ${auth.userId}
                            name         = ${auth.name}
                            username     = ${auth.username}
                            email        = ${auth.email}
                            userType     = ${auth.userType}
                            token        = ${auth.token.take(20)}...   // token dipotong biar aman
                            customerId   = ${auth.customerId}
                            customerName = ${auth.customerName}
                        """.trimIndent())

                        /** ⬅ perbaikan: simpan full AuthenticationItem */
                        userPrefsRepo.saveSession(auth)

                        _uiState.updateSuccess(
                            auth = auth,
                            successMessage = "Login berhasil"
                        )
                    }
                }
            }
        }
    }

    /* ------------------------------------------------------------------
     * 🔥 REGISTER CUSTOMER
     * ------------------------------------------------------------------ */
    fun register(request: CreateRegisterRequest) {
        viewModelScope.launch {
            useCases.registerCustomer(request).collectLatest { result ->
                when (result) {
                    is Result.Loading -> _uiState.updateLoading()
                    is Result.Error -> _uiState.updateError(result.message)
                    is Result.Success -> {
                        val auth = result.data
                        userPrefsRepo.saveSession(auth)       // ⬅ SAMA seperti login
                        _uiState.updateSuccess(
                            auth = auth,
                            successMessage = "Registrasi berhasil"
                        )
                    }
                }
            }
        }
    }

    /* ------------------------------------------------------------------
     * 🔥 GET CUSTOMER PROFILE
     * ------------------------------------------------------------------ */
    fun getProfile(token: String, userId: Int) {
        val baseAuth = _uiState.value.auth
        viewModelScope.launch {
            useCases.getCustomerProfile(token, userId, baseAuth).collectLatest { result ->
                when (result) {
                    is Result.Loading -> _uiState.updateLoading()
                    is Result.Error -> _uiState.updateError(result.message)
                    is Result.Success -> {
                        val auth = result.data
                        userPrefsRepo.saveSession(auth)   // ⬅ simpan hasil update profil
                        _uiState.updateSuccess(auth = auth, successMessage = null)
                    }
                }
            }
        }
    }

    /* ------------------------------------------------------------------
     * 🔥 UPDATE CUSTOMER PROFILE
     * ------------------------------------------------------------------ */
    fun updateProfile(token: String, userId: Int, request: UpdateUserRequest) {
        val baseAuth = _uiState.value.auth
        if (baseAuth == null) {    // aman kalau state auth belum ada
            _uiState.updateError("Session user tidak ditemukan")
            return
        }

        viewModelScope.launch {
            useCases.updateCustomerProfile(
                token = token,
                userId = userId,
                request = request,
                baseAuth = baseAuth        // sekarang sudah non-null
            ).collectLatest { result ->
                when (result) {
                    is Result.Loading -> _uiState.updateLoading()
                    is Result.Error -> _uiState.updateError(result.message)
                    is Result.Success -> {
                        val auth = result.data
                        userPrefsRepo.saveSession(auth)
                        _uiState.updateSuccess(
                            auth = auth,
                            successMessage = "Profil berhasil diperbarui"
                        )
                    }
                }
            }
        }
    }

    /* ------------------------------------------------------------------
     * 🔥 DELETE CUSTOMER ACCOUNT
     * ------------------------------------------------------------------ */
    fun deleteAccount(token: String, userId: Int) {
        viewModelScope.launch {
            useCases.deleteCustomerAccount(token, userId).collectLatest { result ->
                when (result) {
                    is Result.Loading -> _uiState.updateLoading()
                    is Result.Error -> _uiState.updateError(result.message)
                    is Result.Success -> {
                        userPrefsRepo.clearSession() // ⬅ logout benar
                        _uiState.value = AuthenticationUiState(
                            auth = null,
                            successMessage = "Akun berhasil dihapus"
                        )
                    }
                }
            }
        }
    }

    /* ------------------------------------------------------------------
     * 🔥 UTIL
     * ------------------------------------------------------------------ */
    fun resetMessages() {
        _uiState.value = _uiState.value.copy(
            successMessage = null,
            errorMessage = null
        )
    }

    fun logout() {
        viewModelScope.launch {
            userPrefsRepo.clearSession()      // 🔥 benar-benar hapus session dari DataStore
            _uiState.value = AuthenticationUiState(auth = null)
        }
    }

    /* ------------------------------------------------------------------
     * PRIVATE STATE REDUCERS
     * ------------------------------------------------------------------ */
    private fun MutableStateFlow<AuthenticationUiState>.updateLoading() {
        value = value.copy(isLoading = true, errorMessage = null, successMessage = null)
    }

    private fun MutableStateFlow<AuthenticationUiState>.updateError(msg: String?) {
        value = value.copy(isLoading = false, errorMessage = msg)
    }

    private fun MutableStateFlow<AuthenticationUiState>.updateSuccess(
        auth: AuthenticationItem,
        successMessage: String?
    ) {
        value = value.copy(
            isLoading = false,
            auth = auth,
            successMessage = successMessage,
            errorMessage = null
        )
    }
}
