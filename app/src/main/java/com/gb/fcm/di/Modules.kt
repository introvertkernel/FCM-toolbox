package com.gb.fcm.di

import androidx.room.Room
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.messaging.FirebaseMessaging
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapters.PolymorphicJsonAdapterFactory
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.gb.fcm.data.db.AppDatabase
import com.gb.fcm.data.model.Payload
import com.gb.fcm.data.repository.InMemoryTokenRepository
import com.gb.fcm.data.repository.MessageRepository
import com.gb.fcm.data.repository.TokenRepository
import com.gb.fcm.usecase.GetTokenUseCase
import com.gb.fcm.usecase.ResetTokenUseCase
import com.gb.fcm.usecase.UpdateTokenUseCase
import com.gb.fcm.viewmodel.MainViewModel
import com.gb.fcm.viewmodel.PresenceLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Default
import kotlinx.coroutines.SupervisorJob
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val main = module {
    viewModel { MainViewModel(get(), get(), get(), get()) }
    single { MessageRepository(get()) }
    single { PresenceLiveData(get(), get(), get(), scope = CoroutineScope(SupervisorJob() + Default)) }
}

val token = module {
    single<TokenRepository> { InMemoryTokenRepository(get(), scope = CoroutineScope(SupervisorJob() + Default)) }
    single { GetTokenUseCase(get()) }
    single { UpdateTokenUseCase(get()) }
    single { ResetTokenUseCase(get()) }
}

val firebase = module {
    single { FirebaseMessaging.getInstance() }
    single { FirebaseDatabase.getInstance() }
}

val database = module {
    single { Room.databaseBuilder(androidContext(), com.gb.fcm.data.db.AppDatabase::class.java, "database").build() }
    single { get<com.gb.fcm.data.db.AppDatabase>().dao() }
}

val json = module {
    single {
        Moshi.Builder()
                .add(PolymorphicJsonAdapterFactory.of(Payload::class.java, "type")
                        .withSubtype(Payload.App::class.java, "app")
                        .withSubtype(Payload.Link::class.java, "link")
                        .withSubtype(Payload.Ping::class.java, "ping")
                        .withSubtype(Payload.Raw::class.java, "raw")
                        .withSubtype(Payload.Text::class.java, "text"))
                .add(KotlinJsonAdapterFactory()).build()
    }
    single {
        mapOf(
                "app" to Payload.App::class.java,
                "link" to Payload.Link::class.java,
                "ping" to Payload.Ping::class.java,
                "text" to Payload.Text::class.java
        )
    }
}
