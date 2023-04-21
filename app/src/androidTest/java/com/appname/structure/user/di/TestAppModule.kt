package com.appname.structure.user.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import androidx.room.Room
import com.base.hilt.R
import com.base.hilt.model.local.ShoppingItemDatabase
import com.base.hilt.utils.Constants
import com.base.hilt.utils.PrefKey
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
open class TestAppModule {

    @Provides
    @Singleton
    @Named("test-pref")
    fun providePreferencesDataStore(
        @ApplicationContext appContext: Context
    ): DataStore<Preferences> =
        PreferenceDataStoreFactory.create(
            produceFile = {
                appContext.preferencesDataStoreFile(PrefKey.PREFERENCE_NAME)
            }
        )

    @Provides
    @Named("test_db")
    fun provideInMemoryDb(@ApplicationContext context: Context) =
        Room.inMemoryDatabaseBuilder(context, ShoppingItemDatabase::class.java)
            .allowMainThreadQueries()
            .build()

    @Singleton
    @Provides
    @Named("test_glide")
    fun provideGlideInstance(
        @ApplicationContext context: Context
    ) = Glide.with(context).setDefaultRequestOptions(
        RequestOptions()
            .placeholder(R.drawable.ic_image)
            .error(R.drawable.ic_image)
    )


    @Provides
    @Named("Normal")
    fun getRetrofit(@Named("NormalOkHttpClient") okHttpClient: OkHttpClient): Retrofit {
        /*var objectMapper=ObjectMapper()
        objectMapper.enable(DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT)*/
        val builder = Retrofit.Builder()
        builder.baseUrl(Constants.DEV_BASE_URL)
        builder.addConverterFactory(JacksonConverterFactory.create())
        builder.client(okHttpClient)
        return builder.build()
    }

    @Provides
    @Singleton
    @Named("OkHttpClient")
    fun provideOkHttpClient(
        @Named("MockInterceptor") authInterceptor: Interceptor
    ): OkHttpClient = OkHttpClient
        .Builder()
//        .addInterceptor(OkHttpProfilerInterceptor())
        .addInterceptor(authInterceptor)
        .build()


}