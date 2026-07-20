package com.roohi.app.identity.di

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.roohi.app.identity.data.local.IdentityDatabase
import com.roohi.app.identity.data.local.dao.IdentityDao
import com.roohi.app.identity.data.repository.SystemIdentityRepositoryImpl
import com.roohi.app.identity.domain.SystemIdentityRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object IdentityModule {

    @Provides
    @Singleton
    fun provideIdentityDatabase(@ApplicationContext context: Context): IdentityDatabase {
        return Room.databaseBuilder(
            context,
            IdentityDatabase::class.java,
            "roohi_identity_db"
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    fun provideIdentityDao(database: IdentityDatabase): IdentityDao {
        return database.identityDao()
    }

    @Provides
    @Singleton
    fun provideStandardPrefs(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences("roohi_identity_standard_prefs", Context.MODE_PRIVATE)
    }

    @Provides
    @Singleton
    fun provideSystemIdentityRepository(
        dao: IdentityDao,
        prefs: SharedPreferences
    ): SystemIdentityRepository {
        return SystemIdentityRepositoryImpl(dao, prefs)
    }
}
