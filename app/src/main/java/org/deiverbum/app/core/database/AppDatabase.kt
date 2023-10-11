package org.deiverbum.app.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import org.deiverbum.app.core.database.dao.TodayDao
import org.deiverbum.app.core.database.model.entity.*
import org.deiverbum.app.core.database.util.RoomConverter


/**
 * @author A. Cedano
 * @version 1.0
 * @since 2023.1
 */
@Database(
    entities = [LHEasterBiblicalJoinEntity::class, LHEasterBiblicalEntity::class, UniversalisEntity::class, TodayEntity::class, LiturgyEntity::class, LiturgyTimeEntity::class, LiturgySaintJoinEntity::class, SaintEntity::class, SaintLifeEntity::class, SaintShortLifeEntity::class, LHInvitatoryEntity::class, LHInvitatoryJoinEntity::class, LHHymnEntity::class, LHHymnJoinEntity::class, LHPsalmodyJoinEntity::class, LHPsalmJoinEntity::class, LHPsalmEntity::class, LHAntiphonJoinEntity::class, LHAntiphonEntity::class, LHThemeEntity::class, EpigraphEntity::class, LHOfficeVerseEntity::class, LHOfficeVerseJoinEntity::class, LHOfficeBiblicalJoinEntity::class, LHOfficeBiblicalEntity::class, LHOfficePatristicEntity::class, LHOfficePatristicJoinEntity::class, HomilyEntity::class, PaterEntity::class, PaterOpusEntity::class, LHResponsoryEntity::class, LHResponsoryShortEntity::class, BibleBookEntity::class, BibleReadingEntity::class, LHReadingShortEntity::class, LHReadingShortJoinEntity::class, LHGospelCanticleEntity::class, LHIntercessionsEntity::class, LHIntercessionsJoinEntity::class, PrayerEntity::class, LHPrayerEntity::class, LiturgyHomilyJoinEntity::class, BibleHomilyJoinEntity::class, BibleHomilyThemeEntity::class, LiturgyGroupEntity::class, LiturgyColorEntity::class, MassReadingEntity::class, MassReadingJoinEntity::class, SyncStatusEntity::class, DBTableEntity::class,
        KyrieEntity::class, LHKyrieJoinEntity::class, VirginAntiphonEntity::class, LHVirginAntiphonJoinEntity::class, LHNightPrayerEntity::class],
    version = 2
)
@TypeConverters(RoomConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun todayDao(): TodayDao
}

