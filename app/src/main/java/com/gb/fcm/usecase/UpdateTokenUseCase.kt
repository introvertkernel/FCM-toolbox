package com.gb.fcm.usecase

import com.gb.fcm.data.model.Token
import com.gb.fcm.data.repository.TokenRepository

class UpdateTokenUseCase(private val repo: TokenRepository) {
    suspend operator fun invoke(token: String): Unit = repo.update(Token.Success(token))
}