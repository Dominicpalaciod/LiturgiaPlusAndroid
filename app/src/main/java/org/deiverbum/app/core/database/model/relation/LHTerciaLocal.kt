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
 * Obtiene el contenido de la hora **`Tercia`** desde la base de datos.
 *
 * @author A. Cedano
 * @version 1.0
 * @since 2023.1
 */
data class LHTerciaLocal(
    @Embedded
    var universalis: UniversalisEntity,

    @Relation(entity = LiturgyEntity::class, parentColumn = "liturgyFK", entityColumn = "liturgyID")
    var liturgy: LiturgyWithTime,

    @Relation(entity = LHHymnJoinEntity::class, parentColumn = "tHymnFK", entityColumn = "groupID")
    var hymn: LHHymnWithAll,

    @Relation(
        entity = LHReadingShortJoinEntity::class,
        parentColumn = "tBiblicalFK",
        entityColumn = "groupID"
    )
    var readingShort: LHReadingShortAll,

    @Relation(
        entity = LHPsalmodyJoinEntity::class,
        parentColumn = "tAntiphonFK",
        entityColumn = "groupID"
    )
    var antiphons: LHAntiphonAssoc,

    @Relation(
        entity = LHPsalmodyJoinEntity::class,
        parentColumn = "tPsalmFK",
        entityColumn = "groupID"
    )
    var psalms: LHPsalmsAssoc,

    @Relation(entity = LHPrayerEntity::class, parentColumn = "tPrayerFK", entityColumn = "groupID")
    var prayer: LHPrayerAll
) {
    val domainModel: Universalis
        get() {
            val dm = Universalis()
            dm.saintFK = universalis.saintFK
            dm.liturgyDay = liturgy.domainModel
            dm.todayDate = universalis.todayDate
            dm.hasSaint = universalis.hasSaint
            dm.liturgyDay.typeID = 3
            val bh = BreviaryHour()
            val hi = Intermedia()
            hi.lhHymn = hymn.domainModel

            hi.salmodia = LHPsalmody(psalms.domainModel, antiphons.domainModel)


            hi.lecturaBreve = readingShort.domainModel
            hi.oracion = prayer.domainModel
            bh.setIntermedia(hi)
            dm.liturgyDay.breviaryHour = bh
            return dm
        }
}