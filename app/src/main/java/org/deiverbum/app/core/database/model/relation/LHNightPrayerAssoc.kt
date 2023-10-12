package org.deiverbum.app.core.database.model.relation

import androidx.room.Embedded
import androidx.room.Relation
import org.deiverbum.app.core.database.model.entity.LHGospelCanticleEntity
import org.deiverbum.app.core.database.model.entity.LHHymnEntity
import org.deiverbum.app.core.database.model.entity.LHKyrieJoinEntity
import org.deiverbum.app.core.database.model.entity.LHNightPrayerEntity
import org.deiverbum.app.core.database.model.entity.LHPrayerEntity
import org.deiverbum.app.core.database.model.entity.LHPsalmodyJoinEntity
import org.deiverbum.app.core.database.model.entity.LHReadingShortJoinEntity
import org.deiverbum.app.core.database.model.entity.LHVirginAntiphonJoinEntity

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2023.1
 */
data class LHNightPrayerAssoc(
    @Embedded
    var nightPrayer: LHNightPrayerEntity,

    @Relation(entity = LHKyrieJoinEntity::class, parentColumn = "kyrieFK", entityColumn = "groupID")
    var kyrie: KyrieWithAll?,

    @Relation(entity = LHHymnEntity::class, parentColumn = "hymnFK", entityColumn = "hymnID")
    var hymn: LHHymnEntity,


    @Relation(
        entity = LHPsalmodyJoinEntity::class,
        parentColumn = "psalmFK",
        entityColumn = "groupID"
    )
    var salmodia: LHPsalmodyAssoc,

    @Relation(
        entity = LHReadingShortJoinEntity::class,
        parentColumn = "readingFK",
        entityColumn = "groupID"
    )
    var readingFK: LHReadingShortAll,

    @Relation(
        entity = LHGospelCanticleEntity::class,
        parentColumn = "nuncDimitisFK",
        entityColumn = "groupID"
    )
    var nuncDimitis: LHGospelCanticleWithAntiphon,

    @Relation(entity = LHPrayerEntity::class, parentColumn = "prayerFK", entityColumn = "groupID")
    var prayer: LHPrayerAll,

    @Relation(
        entity = LHVirginAntiphonJoinEntity::class,
        parentColumn = "virginFK",
        entityColumn = "groupID"
    )
    var virgin: LHVirginAntiphonWithAll
)
