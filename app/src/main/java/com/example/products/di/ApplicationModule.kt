package com.example.products.di

import android.content.Context
import androidx.room.Room
import com.example.products.database.ProductDao
import com.example.products.database.ProductDatabase
import com.example.products.database.ProductEntity
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {
    @Provides
    fun provideChannelDao(appDatabase: ProductDatabase): ProductDao {
        return appDatabase.productsDao()
    }

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): ProductDatabase {
        return Room.databaseBuilder(
            appContext,
            ProductDatabase::class.java,
            "database"
        )
            .fallbackToDestructiveMigration()
            .build()
    }
}