package com.gb.fcm.usecase

import com.gb.fcm.data.repository.TokenRepository

class ResetTokenUseCase(private val repo: TokenRepository) {
    suspend operator fun invoke(): Unit = repo.reset()
}