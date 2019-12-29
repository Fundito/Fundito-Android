package com.fundito.fundito.di.module

import android.content.Context
import androidx.room.Room
import com.fundito.fundito.data.database.AppDatabase
import com.fundito.fundito.data.database.SearchDao
import com.fundito.fundito.di.AppScope
import dagger.Module
import dagger.Provides

/**
 * Created by mj on 27, December, 2019
 */
@Module
class AppModule {

    @Provides
    @AppScope
    fun provideRoomDatabase(context : Context) : AppDatabase {
        return Room.databaseBuilder(context,AppDatabase::class.java,AppDatabase.DATABASE_NAME)
            .build()
    }

    @Provides
    @AppScope
    fun provideSearchDao(database : AppDatabase) : SearchDao {
        return database.searchDao()
    }
}