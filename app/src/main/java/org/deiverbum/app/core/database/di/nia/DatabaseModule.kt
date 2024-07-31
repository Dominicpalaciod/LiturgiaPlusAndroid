package org.deiverbum.app.core.database.di.nia

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import org.deiverbum.app.core.database.di.DatabaseMigrations.MIGRATION_1_2
import org.deiverbum.app.core.database.nia.NiaDatabase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object DatabaseModule {
    @Provides
    @Singleton
    fun providesNiaDatabase(
        @ApplicationContext context: Context,
    ): NiaDatabase = Room.databaseBuilder(
        context,
        NiaDatabase::class.java,
        "nia-database",
    )
        //.fallbackToDestructiveMigration()
        .addMigrations(MIGRATION_1_2)
        .build()
}
