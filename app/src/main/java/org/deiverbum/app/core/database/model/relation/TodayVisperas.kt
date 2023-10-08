package org.deiverbum.app.core.database.model.relation

import androidx.room.Embedded
import androidx.room.Relation
import org.deiverbum.app.core.database.model.entity.LHGospelCanticleEntity
import org.deiverbum.app.core.database.model.entity.LHHymnJoinEntity
import org.deiverbum.app.core.database.model.entity.LHIntercessionsJoinEntity
import org.deiverbum.app.core.database.model.entity.LHPrayerEntity
import org.deiverbum.app.core.database.model.entity.LHPsalmodyJoinEntity
import org.deiverbum.app.core.database.model.entity.LHReadingShortJoinEntity
import org.deiverbum.app.core.database.model.entity.LiturgyEntity
import org.deiverbum.app.core.database.model.entity.UniversalisEntity
import org.deiverbum.app.core.model.data.BreviaryHour
import org.deiverbum.app.core.model.data.LHPsalmody
import org.deiverbum.app.core.model.data.Universalis
import org.deiverbum.app.core.model.data.Visperas

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2022.2
 */
data class TodayVisperas(
    @Embedded
    var today: UniversalisEntity,

    @Relation(entity = LiturgyEntity::class, parentColumn = "liturgyFK", entityColumn = "liturgyID")
    var liturgy: LiturgyWithTime,

    @Relation(entity = LHHymnJoinEntity::class, parentColumn = "lHymnFK", entityColumn = "groupID")
    var hymn: LHHymnWithAll,

    @Relation(
        entity = LHPsalmodyJoinEntity::class,
        parentColumn = "vAntiphonFK",
        entityColumn = "groupID"
    )
    var antiphons: LHAntiphonAssoc,

    @Relation(
        entity = LHPsalmodyJoinEntity::class,
        parentColumn = "vPsalmFK",
        entityColumn = "groupID"
    )
    var psalms: LHPsalmsAssoc,

    @Relation(
        entity = LHReadingShortJoinEntity::class,
        parentColumn = "vBiblicalFK",
        entityColumn = "groupID"
    )
    var readingShort: LHReadingShortAll,

    @Relation(
        entity = LHIntercessionsJoinEntity::class,
        parentColumn = "vIntercessionsFK",
        entityColumn = "groupID"
    )
    var intercessions: LHIntercessionsWithAll,

    @Relation(entity = LHPrayerEntity::class, parentColumn = "vPrayerFK", entityColumn = "groupID")
    var prayer: LHPrayerAll,

    @Relation(
        entity = LHGospelCanticleEntity::class,
        parentColumn = "vMagnificatFK",
        entityColumn = "groupID"
    )
    var gospelCanticle: LHGospelCanticleWithAntiphon
) {

    val domainModel: Universalis
        get() {
            val dm = Universalis()
            dm.saintFK = today.saintFK
            dm.liturgyDay = liturgy.domainModel
            dm.todayDate = today.todayDate
            dm.hasSaint = today.hasSaint
            dm.liturgyDay.typeID = 6
            dm.timeFK = liturgy.liturgyTime.timeID

            val bh = BreviaryHour()
            val visperas = Visperas()
            visperas.setIsPrevious(dm.previousFK)
            visperas.lhHymn = hymn.domainModel

            visperas.salmodia = LHPsalmody(psalms.domainModel, antiphons.domainModel)
            visperas.setLecturaBreve(readingShort.domainModel)
            visperas.setGospelCanticle(gospelCanticle.getDomainModel(6))
            visperas.setPreces(intercessions.domainModel)
            visperas.setOracion(prayer.domainModel)
            bh.setVisperas(visperas)
            dm.liturgyDay.breviaryHour = bh
            return dm
        }
}