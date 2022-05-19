package com.gb.fcm.data.repository

import com.gb.fcm.data.db.MessageDao
import com.gb.fcm.data.model.Message

class MessageRepository(private val dao: MessageDao) {

    fun get() = dao.get()

    suspend fun insert(vararg messages: Message) = dao.insert(*messages)

    suspend fun delete(vararg messages: Message) = dao.delete(*messages)

}