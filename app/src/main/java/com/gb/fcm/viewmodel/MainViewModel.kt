package com.gb.fcm.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.gb.fcm.usecase.GetTokenUseCase
import com.gb.fcm.usecase.ResetTokenUseCase
import com.gb.fcm.data.model.Token
import com.gb.fcm.data.model.Message
import com.gb.fcm.data.repository.MessageRepository
import kotlinx.coroutines.launch

class MainViewModel(
    val presence: PresenceLiveData,
    getTokenUseCase: GetTokenUseCase,
    private val resetTokenUseCase: ResetTokenUseCase,
    private val repository: MessageRepository,
) : ViewModel() {

    val messages = repository.get()

    val token: LiveData<Token> = getTokenUseCase().asLiveData()

    fun insert(vararg messages: Message) = viewModelScope.launch { repository.insert(*messages) }

    fun delete(vararg messages: Message) = viewModelScope.launch { repository.delete(*messages) }

    fun resetToken() = viewModelScope.launch { resetTokenUseCase() }

}