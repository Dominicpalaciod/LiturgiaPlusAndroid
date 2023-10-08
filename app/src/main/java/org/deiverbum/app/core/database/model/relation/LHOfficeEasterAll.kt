package org.deiverbum.app.core.database.model.relation

import androidx.room.Embedded
import androidx.room.Relation
import org.deiverbum.app.core.database.model.entity.BibleReadingEntity
import org.deiverbum.app.core.database.model.entity.LHOfficeBiblicalEasterEntity
import org.deiverbum.app.core.database.model.entity.PrayerEntity
import org.deiverbum.app.core.model.data.LHOfficeBiblicalEaster

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2023.1
 */
data class LHOfficeEasterAll(
    @Embedded
    var biblical: LHOfficeBiblicalEasterEntity,

    @Relation(
        parentColumn = "readingFK",
        entityColumn = "readingID",
        entity = BibleReadingEntity::class
    )
    var reading: BibleReadingWithBook,

    @Relation(parentColumn = "prayerFK", entityColumn = "prayerID", entity = PrayerEntity::class)
    var prayer: PrayerEntity
) {
    val domainModel: LHOfficeBiblicalEaster
        get() {
            val dm = LHOfficeBiblicalEaster()
            dm.setOrden(biblical.theOrder)
            dm.theme = biblical.theme
            dm.book = reading.libro.domainModel
            dm.verseChapter = reading.lectura.capitulo.toString()
            dm.verseFrom = reading.lectura.desde.toString()
            dm.verseTo = reading.lectura.hasta.toString()
            dm.setCita(reading.lectura.cita)
            dm.text = reading.lectura.texto
            dm.prayer = prayer.domainModel
            return dm
        }
}