/*
 * Copyright 2017 Simon Marquis
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.gb.fcm.services

import androidx.annotation.UiThread
import androidx.annotation.WorkerThread
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.gb.fcm.usecase.UpdateTokenUseCase
import com.gb.fcm.data.model.Message
import com.gb.fcm.data.model.Payload
import com.gb.fcm.data.repository.MessageRepository
import com.gb.fcm.utils.Notifications
import com.gb.fcm.utils.asMessage
import com.gb.fcm.utils.copyToClipboard
import com.gb.fcm.utils.uiHandler
import kotlinx.coroutines.runBlocking
import org.koin.android.ext.android.inject
import java.lang.Boolean.parseBoolean

class FcmService : FirebaseMessagingService() {

    private val repository: MessageRepository by inject()
    private val updateToken: UpdateTokenUseCase by inject()

    @WorkerThread
    override fun onNewToken(token: String) = runBlocking { updateToken(token) }

    @WorkerThread
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        val message = remoteMessage.asMessage()
        runBlocking { repository.insert(message) }
        uiHandler.post { notifyAndExecute(message) }
    }

    @UiThread
    private fun notifyAndExecute(message: Message) {
        if (!parseBoolean(message.data["hide"])) {
            Notifications.show(this, message)
        }
        val payload = message.payload
        when {
            payload is Payload.Text && payload.clipboard -> applicationContext.copyToClipboard(payload.text)
        }
    }
}