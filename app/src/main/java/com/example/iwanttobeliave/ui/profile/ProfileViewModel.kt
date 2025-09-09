package com.example.iwanttobelieve.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.iwanttobelieve.data.model.User
import com.example.iwanttobelieve.data.repository.AuthRepository
import com.example.iwanttobelieve.data.repository.UserRepository
import com.example.iwanttobelieve.utils.Result
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val authRepository: AuthRepository = AuthRepository(),
    private val userRepository: UserRepository = UserRepository()
) : ViewModel() {

    private val _userState = MutableStateFlow<Result<User?>?>(null)
    val userState: StateFlow<Result<User?>?> = _userState

    init {
        loadUserProfile()
    }

    fun loadUserProfile() {
        val currentUser = authRepository.currentUser()
        if (currentUser != null) {
            viewModelScope.launch {
                _userState.value = Result.Loading

                val userDoc = userRepository.getUser(currentUser.uid)
                if (userDoc is Result.Success && userDoc.data != null) {
                    _userState.value = userDoc
                } else {
                    // Cria usuário temporário a partir do Firebase Auth
                    val tempUser = User(
                        name = currentUser.displayName ?: "Usuário",
                        email = currentUser.email ?: ""
                    )
                    _userState.value = Result.Success(tempUser)
                }
            }
        } else {
            _userState.value = Result.Error(Exception("Nenhum usuário logado"))
        }
    }

    fun logout() {
        authRepository.logout()
    }
}
