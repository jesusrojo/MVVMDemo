package com.jesusrojo.mvvmdemo.presentation.di.hilt

import android.app.Application
import androidx.room.Room
import com.jesusrojo.mvvmdemo.data.db.UiDataDAO
import com.jesusrojo.mvvmdemo.data.db.UiDataDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RoomDataBaseModule {

    @Singleton
    @Provides
    fun provideRoomDatabase(app: Application): UiDataDatabase {
        return Room.databaseBuilder(app,
            UiDataDatabase::class.java,
            "uidata_db")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideMyDao(database: UiDataDatabase): UiDataDAO {
        return database.getMyDAO()
    }
}