package com.base.hilt.di

import android.content.Context
import android.content.SharedPreferences
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import androidx.room.Room
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.base.hilt.R
import com.base.hilt.model.local.ShoppingDao
import com.base.hilt.model.local.ShoppingItemDatabase
import com.base.hilt.network.PixabayAPI
import com.base.hilt.ui.shopping.DefaultShoppingRepository
import com.base.hilt.ui.shopping.ShoppingRepository
import com.base.hilt.utils.Constants.BASE_URL
import com.base.hilt.utils.Constants.DATABASE_NAME
import com.base.hilt.utils.MyPreference
import com.base.hilt.utils.PrefKey
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory
import javax.inject.Singleton

/**
 * Defines all the classes that need to be provided in the scope of the app.
 *
 * Define here all objects that are shared throughout the app, like SharedPreferences, navigators or
 * others. If some of those objects are singletons, they should be annotated with `@Singleton`.
 */
@Module
@InstallIn(SingletonComponent::class)
class AppModule {


    @Provides
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences =
        try {
            // this is equivalent to using deprecated MasterKeys.AES256_GCM_SPEC
            val spec = KeyGenParameterSpec.Builder(
                MasterKey.DEFAULT_MASTER_KEY_ALIAS,
                KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
            )
                .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
                .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
                .setKeySize(MasterKey.DEFAULT_AES_GCM_MASTER_KEY_SIZE)
                .build()
            val masterKey = MasterKey.Builder(context)
                .setKeyGenParameterSpec(spec)
                .build()
            //Old Deprecated code removed
            /*     EncryptedSharedPreferences.create(
                     PrefKey.PREFERENCE_NAME,
                     MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC),
                     context,
                     EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                     EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
                 )*/

            EncryptedSharedPreferences.create(
                context,
                PrefKey.PREFERENCE_NAME,
                masterKey, // masterKey created above
                EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            )
        } catch (e: Exception) {
            e.printStackTrace()
            throw e
        }

  /*  @Provides
    @Singleton
    fun provideDataStore(@ApplicationContext context: Context): DataStore<Preferences> =
        preferencesDataStore(name = BuildConfig.APPLICATION_ID,*//* produceMigrations = {
            listOf(SharedPreferencesMigration(context,  PrefKey.PREFERENCE_NAME))
        }*//*).getValue(context, String::javaClass)*/


    @Singleton
    @Provides
    fun provideMyPreference(mSharedPreferences: SharedPreferences) =
        MyPreference(mSharedPreferences)

    @Singleton
    @Provides
    fun provideShoppingItemDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(context, ShoppingItemDatabase::class.java, DATABASE_NAME).
    allowMainThreadQueries().build()

    @Singleton
    @Provides
    fun provideDefaultShoppingRepository(
        dao: ShoppingDao,
        api: PixabayAPI
    ) = DefaultShoppingRepository(dao, api) as ShoppingRepository

    @Singleton
    @Provides
    fun provideGlideInstance(
        @ApplicationContext context: Context
    ) = Glide.with(context).setDefaultRequestOptions(
        RequestOptions()
            .placeholder(R.drawable.ic_image)
            .error(R.drawable.ic_image)
    )

    @Singleton
    @Provides
    fun provideShoppingDao(
        database: ShoppingItemDatabase
    ) = database.shoppingDao()

    @Singleton
    @Provides
    fun providePixabayApi(): PixabayAPI {
        return Retrofit.Builder()
            .addConverterFactory(JacksonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(PixabayAPI::class.java)
    }

  /*  @ApplicationScope
    @Singleton
    @Provides
    fun providesApplicationScope(
        @DefaultDispatcher defaultDispatcher: CoroutineDispatcher
    ): CoroutineScope = CoroutineScope(SupervisorJob() + defaultDispatcher)*/
}