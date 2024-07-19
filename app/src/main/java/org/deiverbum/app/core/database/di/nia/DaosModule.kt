package org.deiverbum.app.core.database.di.nia

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.deiverbum.app.core.database.AppDatabase
import org.deiverbum.app.core.database.dao.TodayDao
import org.deiverbum.app.core.database.dao.nia.NewsResourceDao
import org.deiverbum.app.core.database.dao.nia.NewsResourceFtsDao
import org.deiverbum.app.core.database.dao.nia.RecentSearchQueryDao
import org.deiverbum.app.core.database.dao.nia.TopicDao
import org.deiverbum.app.core.database.dao.nia.TopicFtsDao
import org.deiverbum.app.core.database.dao.nia.UniversalisDao
import org.deiverbum.app.core.database.nia.NiaDatabase

@Module
@InstallIn(SingletonComponent::class)
internal object DaosModule {
    @Provides
    fun providesTopicsDao(
        database: NiaDatabase,
    ): TopicDao = database.topicDao()

    @Provides
    fun providesTodaysDao(
        database: NiaDatabase,
    ): TodayDao = database.todayDao()

    @Provides
    fun providesNewsResourceDao(
        database: NiaDatabase,
    ): NewsResourceDao = database.newsResourceDao()

    /*
        @Provides
        fun providesUniversalisDao(
            database: NiaDatabase,
        ): UniversalisDao = database.universalisDao()
    */
    @Provides
    fun providesUniversalisDaoo(
        database: AppDatabase,
    ): UniversalisDao = database.universalisDaoo()
    @Provides
    fun providesTopicFtsDao(
        database: NiaDatabase,
    ): TopicFtsDao = database.topicFtsDao()

    @Provides
    fun providesNewsResourceFtsDao(
        database: NiaDatabase,
    ): NewsResourceFtsDao = database.newsResourceFtsDao()

    @Provides
    fun providesRecentSearchQueryDao(
        database: NiaDatabase,
    ): RecentSearchQueryDao = database.recentSearchQueryDao()
}