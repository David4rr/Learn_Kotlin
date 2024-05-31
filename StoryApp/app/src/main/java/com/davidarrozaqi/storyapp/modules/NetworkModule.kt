package com.davidarrozaqi.storyapp.modules

import com.davidarrozaqi.storyapp.utils.HeaderInterceptor
import com.davidarrozaqi.storyapp.data.network.StoryService
import com.davidarrozaqi.storyapp.utils.ConstVal.BASE_URL
import com.davidarrozaqi.storyapp.utils.PreferenceManager
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

val networkModule = module {

    single { PreferenceManager(get()) }
    single {
        OkHttpClient.Builder()
            .addInterceptor(getHeaderInterceptor(get()))
            .addInterceptor(HttpLoggingInterceptor().setLevel(
                HttpLoggingInterceptor.Level.BODY
            ))
            .build()
    }
    single {
       Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
    }
    single {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(get()))
            .client(get())
            .build()
    }
    single {
        get<Retrofit>().create(StoryService::class.java)
    }
}

private fun getHeaderInterceptor(preferenceManager: PreferenceManager): Interceptor {
    val headers = HashMap<String, String>()
    headers["content-type"] = "application/json"

    return HeaderInterceptor(headers, preferenceManager)
}
