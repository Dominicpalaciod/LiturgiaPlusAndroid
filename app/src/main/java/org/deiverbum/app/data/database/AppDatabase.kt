package org.deiverbum.app.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import org.deiverbum.app.data.database.dao.TodayDao
import org.deiverbum.app.data.entity.*

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2023.1
 */
@Database(
    entities = [TodayEntity::class, LiturgyEntity::class, LiturgyTimeEntity::class, LiturgySaintJoinEntity::class, SaintEntity::class, SaintLifeEntity::class, SaintShortLifeEntity::class, LHInvitatoryEntity::class, LHInvitatoryJoinEntity::class, LHHymnEntity::class, LHHymnJoinEntity::class, LHPsalmodyEntity::class, LHPsalmodyJoinEntity::class, PsalmEntity::class, LHAntiphonEntity::class, LHThemeEntity::class, EpigraphEntity::class, LHOfficeVerseEntity::class, LHOfficeVerseJoinEntity::class, LHOfficeBiblicalJoinEntity::class, LHOfficeBiblicalEntity::class, LHOfficeBiblicalEasterEntity::class, LHOfficePatristicEntity::class, LHOfficePatristicJoinEntity::class, HomilyEntity::class, PaterEntity::class, PaterOpusEntity::class, LHResponsoryEntity::class, LHResponsoryShortEntity::class, BibleBookEntity::class, BibleReadingEntity::class, LHReadingShortEntity::class, LHReadingShortJoinEntity::class, LHGospelCanticleEntity::class, LHIntercessionsEntity::class, LHIntercessionsJoinEntity::class, PrayerEntity::class, LHPrayerEntity::class, LiturgyHomilyJoinEntity::class, BibleHomilyJoinEntity::class, BibleHomilyThemeEntity::class, LiturgyGroupEntity::class, LiturgyColorEntity::class, MassReadingEntity::class, MassReadingJoinEntity::class, SyncStatusEntity::class, DBTableEntity::class,
               KyrieEntity::class,LHKyrieJoinEntity::class,VirginAntiphonEntity::class, LHVirginAntiphonJoinEntity::class,LHNightPrayerEntity::class],
    version = 2
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun todayDao(): TodayDao
}

