package org.deiverbum.app.core.database.model.relation

import androidx.room.Embedded
import androidx.room.Relation
import org.deiverbum.app.core.database.model.entity.LHHymnJoinEntity
import org.deiverbum.app.core.database.model.entity.LHPrayerEntity
import org.deiverbum.app.core.database.model.entity.LHPsalmodyJoinEntity
import org.deiverbum.app.core.database.model.entity.LHReadingShortJoinEntity
import org.deiverbum.app.core.database.model.entity.LiturgyEntity
import org.deiverbum.app.core.database.model.entity.UniversalisEntity
import org.deiverbum.app.core.model.data.BreviaryHour
import org.deiverbum.app.core.model.data.Intermedia
import org.deiverbum.app.core.model.data.LHPsalmody
import org.deiverbum.app.core.model.data.Universalis

/**
 * Obtiene el contenido de la hora **`Sexta`** desde la base de datos.
 *
 * @author A. Cedano
 * @version 1.0
 * @since 2022.2
 */
data class TodaySexta(
    @Embedded
    var today: UniversalisEntity,

    @Relation(entity = LiturgyEntity::class, parentColumn = "liturgyFK", entityColumn = "liturgyID")
    var liturgy: LiturgyWithTime,

    @Relation(entity = LHHymnJoinEntity::class, parentColumn = "sHymnFK", entityColumn = "groupID")
    var hymn: LHHymnWithAll,

    @Relation(
        entity = LHPsalmodyJoinEntity::class,
        parentColumn = "sAntiphonFK",
        entityColumn = "groupID"
    )
    var antiphons: LHAntiphonAssoc,

    @Relation(
        entity = LHPsalmodyJoinEntity::class,
        parentColumn = "sPsalmFK",
        entityColumn = "groupID"
    )
    var psalms: LHPsalmsAssoc,
    @Relation(
        entity = LHReadingShortJoinEntity::class,
        parentColumn = "sBiblicalFK",
        entityColumn = "groupID"
    )
    var readingShort: LHReadingShortAll,


    @Relation(entity = LHPrayerEntity::class, parentColumn = "sPrayerFK", entityColumn = "groupID")
    var prayer: LHPrayerAll,
) {
    val domainModel: Universalis
        get() {
            val dm = Universalis()
            dm.saintFK = today.saintFK
            dm.liturgyDay = liturgy.domainModel
            dm.todayDate = today.todayDate
            dm.hasSaint = today.hasSaint
            dm.timeFK = liturgy.liturgyTime.timeID
            dm.liturgyDay.typeID = 4
            val bh = BreviaryHour()
            val hi = Intermedia()
            hi.lhHymn = hymn.domainModel

            hi.salmodia = LHPsalmody(psalms.domainModel, antiphons.domainModel)
            hi.lecturaBreve = readingShort.domainModel
            hi.setOracion(prayer.domainModel)
            bh.setIntermedia(hi)
            dm.liturgyDay.breviaryHour = bh
            return dm
        }
}