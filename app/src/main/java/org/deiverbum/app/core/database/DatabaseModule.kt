package org.deiverbum.app.core.database

import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2023.1
 */
@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    /*
    @Provides
    @Singleton
    fun provideDatabase(application: Application?): AppDatabase {

        return databaseBuilder(
            application!!, AppDatabase::class.java,
            "LiturgiaPlusDB"
        )
            .createFromAsset(DATABASE_PATH)
            .fallbackToDestructiveMigration()
            //.allowMainThreadQueries()
            //.addTypeConverter(roomConverter)

            .build()
    }

    @Provides
    @Singleton
    fun provideTodayDao(appDB: AppDatabase): TodayDao {
        return appDB.todayDao()
    }
*/
    @Provides
    @Singleton
    fun provideMoshi(): Moshi {
        return Moshi.Builder().build()
    }

}