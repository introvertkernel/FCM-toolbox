package com.gb.fcm.data.repository

import com.gb.fcm.data.model.Token
import kotlinx.coroutines.flow.Flow

interface TokenRepository {

    fun get(): Flow<Token>

    suspend fun update(token: Token)

    suspend fun reset()

}