/*
 * Copyright 2022 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.deiverbum.app.core.database.nia

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import org.deiverbum.app.core.database.dao.TodayDao
import org.deiverbum.app.core.database.dao.nia.NewsResourceDao
import org.deiverbum.app.core.database.dao.nia.NewsResourceFtsDao
import org.deiverbum.app.core.database.dao.nia.RecentSearchQueryDao
import org.deiverbum.app.core.database.dao.nia.TopicDao
import org.deiverbum.app.core.database.dao.nia.TopicFtsDao
import org.deiverbum.app.core.database.model.entity.BibleBookEntity
import org.deiverbum.app.core.database.model.entity.BibleHomilyJoinEntity
import org.deiverbum.app.core.database.model.entity.BibleHomilyThemeEntity
import org.deiverbum.app.core.database.model.entity.BibleReadingEntity
import org.deiverbum.app.core.database.model.entity.DBTableEntity
import org.deiverbum.app.core.database.model.entity.EpigraphEntity
import org.deiverbum.app.core.database.model.entity.HomilyEntity
import org.deiverbum.app.core.database.model.entity.KyrieEntity
import org.deiverbum.app.core.database.model.entity.LHAntiphonEntity
import org.deiverbum.app.core.database.model.entity.LHAntiphonJoinEntity
import org.deiverbum.app.core.database.model.entity.LHEasterBiblicalEntity
import org.deiverbum.app.core.database.model.entity.LHEasterBiblicalJoinEntity
import org.deiverbum.app.core.database.model.entity.LHGospelCanticleEntity
import org.deiverbum.app.core.database.model.entity.LHHymnEntity
import org.deiverbum.app.core.database.model.entity.LHHymnJoinEntity
import org.deiverbum.app.core.database.model.entity.LHIntercessionsEntity
import org.deiverbum.app.core.database.model.entity.LHIntercessionsJoinEntity
import org.deiverbum.app.core.database.model.entity.LHInvitatoryEntity
import org.deiverbum.app.core.database.model.entity.LHInvitatoryJoinEntity
import org.deiverbum.app.core.database.model.entity.LHKyrieJoinEntity
import org.deiverbum.app.core.database.model.entity.LHNightPrayerEntity
import org.deiverbum.app.core.database.model.entity.LHOfficeBiblicalEntity
import org.deiverbum.app.core.database.model.entity.LHOfficeBiblicalJoinEntity
import org.deiverbum.app.core.database.model.entity.LHOfficePatristicEntity
import org.deiverbum.app.core.database.model.entity.LHOfficePatristicJoinEntity
import org.deiverbum.app.core.database.model.entity.LHOfficeVerseEntity
import org.deiverbum.app.core.database.model.entity.LHOfficeVerseJoinEntity
import org.deiverbum.app.core.database.model.entity.LHPrayerEntity
import org.deiverbum.app.core.database.model.entity.LHPsalmEntity
import org.deiverbum.app.core.database.model.entity.LHPsalmJoinEntity
import org.deiverbum.app.core.database.model.entity.LHPsalmodyJoinEntity
import org.deiverbum.app.core.database.model.entity.LHReadingShortEntity
import org.deiverbum.app.core.database.model.entity.LHReadingShortJoinEntity
import org.deiverbum.app.core.database.model.entity.LHResponsoryEntity
import org.deiverbum.app.core.database.model.entity.LHResponsoryShortEntity
import org.deiverbum.app.core.database.model.entity.LHThemeEntity
import org.deiverbum.app.core.database.model.entity.LHVirginAntiphonJoinEntity
import org.deiverbum.app.core.database.model.entity.LiturgyColorEntity
import org.deiverbum.app.core.database.model.entity.LiturgyEntity
import org.deiverbum.app.core.database.model.entity.LiturgyGroupEntity
import org.deiverbum.app.core.database.model.entity.LiturgyHomilyJoinEntity
import org.deiverbum.app.core.database.model.entity.LiturgySaintJoinEntity
import org.deiverbum.app.core.database.model.entity.LiturgyTimeEntity
import org.deiverbum.app.core.database.model.entity.MassReadingEntity
import org.deiverbum.app.core.database.model.entity.MassReadingJoinEntity
import org.deiverbum.app.core.database.model.entity.PaterEntity
import org.deiverbum.app.core.database.model.entity.PaterOpusEntity
import org.deiverbum.app.core.database.model.entity.PrayerEntity
import org.deiverbum.app.core.database.model.entity.SaintEntity
import org.deiverbum.app.core.database.model.entity.SaintLifeEntity
import org.deiverbum.app.core.database.model.entity.SaintShortLifeEntity
import org.deiverbum.app.core.database.model.entity.SyncStatusEntity
import org.deiverbum.app.core.database.model.entity.UniversalisEntity
import org.deiverbum.app.core.database.model.entity.VirginAntiphonEntity
import org.deiverbum.app.core.database.model.nia.NewsResourceEntity
import org.deiverbum.app.core.database.model.nia.NewsResourceFtsEntity
import org.deiverbum.app.core.database.model.nia.NewsResourceTopicCrossRef
import org.deiverbum.app.core.database.model.nia.RecentSearchQueryEntity
import org.deiverbum.app.core.database.model.nia.TopicEntity
import org.deiverbum.app.core.database.model.nia.TopicFtsEntity
import org.deiverbum.app.core.database.util.InstantConverter

@Database(
    entities = [
        NewsResourceEntity::class,
        NewsResourceTopicCrossRef::class,
        NewsResourceFtsEntity::class,
        TopicEntity::class,
        TopicFtsEntity::class,
        RecentSearchQueryEntity::class,
        LHEasterBiblicalJoinEntity::class, LHEasterBiblicalEntity::class, UniversalisEntity::class, LiturgyEntity::class, LiturgyTimeEntity::class, LiturgySaintJoinEntity::class, SaintEntity::class, SaintLifeEntity::class, SaintShortLifeEntity::class, LHInvitatoryEntity::class, LHInvitatoryJoinEntity::class, LHHymnEntity::class, LHHymnJoinEntity::class, LHPsalmodyJoinEntity::class, LHPsalmJoinEntity::class, LHPsalmEntity::class, LHAntiphonJoinEntity::class, LHAntiphonEntity::class, LHThemeEntity::class, EpigraphEntity::class, LHOfficeVerseEntity::class, LHOfficeVerseJoinEntity::class, LHOfficeBiblicalJoinEntity::class, LHOfficeBiblicalEntity::class, LHOfficePatristicEntity::class, LHOfficePatristicJoinEntity::class, HomilyEntity::class, PaterEntity::class, PaterOpusEntity::class, LHResponsoryEntity::class, LHResponsoryShortEntity::class, BibleBookEntity::class, BibleReadingEntity::class, LHReadingShortEntity::class, LHReadingShortJoinEntity::class, LHGospelCanticleEntity::class, LHIntercessionsEntity::class, LHIntercessionsJoinEntity::class, PrayerEntity::class, LHPrayerEntity::class, LiturgyHomilyJoinEntity::class, BibleHomilyJoinEntity::class, BibleHomilyThemeEntity::class, LiturgyGroupEntity::class, LiturgyColorEntity::class, MassReadingEntity::class, MassReadingJoinEntity::class, SyncStatusEntity::class, DBTableEntity::class,
        KyrieEntity::class, LHKyrieJoinEntity::class, VirginAntiphonEntity::class, LHVirginAntiphonJoinEntity::class, LHNightPrayerEntity::class


    ],
    version = 2,
    /*autoMigrations = [
        AutoMigration(from = 1, to = 2),
        AutoMigration(from = 2, to = 3, spec = DatabaseMigrations.Schema2to3::class),
        AutoMigration(from = 3, to = 4),
        AutoMigration(from = 4, to = 5),
        AutoMigration(from = 5, to = 6),
        AutoMigration(from = 6, to = 7),
        AutoMigration(from = 7, to = 8),
        AutoMigration(from = 8, to = 9),
        AutoMigration(from = 9, to = 10),
        AutoMigration(from = 10, to = 11, spec = DatabaseMigrations.Schema10to11::class),
        AutoMigration(from = 11, to = 12, spec = DatabaseMigrations.Schema11to12::class),
        AutoMigration(from = 12, to = 13),
        AutoMigration(from = 13, to = 14),
        AutoMigration(from = 14, to = 15),

    ],
    exportSchema = true,*/
)
@TypeConverters(
    InstantConverter::class,
)
internal abstract class NiaDatabase : RoomDatabase() {
    abstract fun topicDao(): TopicDao
    abstract fun todayDao(): TodayDao

    abstract fun newsResourceDao(): NewsResourceDao
    abstract fun topicFtsDao(): TopicFtsDao
    abstract fun newsResourceFtsDao(): NewsResourceFtsDao
    abstract fun recentSearchQueryDao(): RecentSearchQueryDao
}
