package com.example.weatherapp.database

import android.content.Context
import androidx.room.Room
import com.example.weatherapp.network.ConstantsApi.Companion.DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    //Each component comes with a scoping annotation that can be used to memoize
    // a binding to the lifetime of the component.
    // For example, to scope a binding to the SingletonComponent component, use the @Singleton

    @Singleton
    @Provides
    fun provideDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(
        context,
        LocationsDatabase::class.java,
        DATABASE_NAME
    ).build()

    @Singleton
    @Provides
    fun provideDao(database: LocationsDatabase) = database.locationsDao()
}