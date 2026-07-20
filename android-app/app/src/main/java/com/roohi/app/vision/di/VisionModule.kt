package com.roohi.app.vision.di

import android.content.Context
import androidx.room.Room
import com.roohi.app.vision.data.VisionDao
import com.roohi.app.vision.data.VisionDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object VisionModule {

    @Provides
    @Singleton
    fun provideVisionDatabase(@ApplicationContext context: Context): VisionDatabase {
        return Room.databaseBuilder(
            context,
            VisionDatabase::class.java,
            "vision_database"
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    fun provideVisionDao(db: VisionDatabase): VisionDao = db.visionDao()
}
