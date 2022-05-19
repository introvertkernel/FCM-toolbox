package com.gb.fcm.usecase

import com.gb.fcm.data.model.Token
import com.gb.fcm.data.repository.TokenRepository
import kotlinx.coroutines.flow.Flow

class GetTokenUseCase(private val repo: TokenRepository) {
    operator fun invoke(): Flow<Token> = repo.get()
}