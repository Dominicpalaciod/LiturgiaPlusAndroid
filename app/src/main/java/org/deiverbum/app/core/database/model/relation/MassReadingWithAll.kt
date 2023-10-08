package org.deiverbum.app.core.database.model.relation

import androidx.room.Embedded
import androidx.room.Relation
import org.deiverbum.app.core.database.model.entity.BibleReadingEntity
import org.deiverbum.app.core.database.model.entity.MassReadingEntity
import org.deiverbum.app.core.model.data.MissaeLectionum

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2023.1
 */
data class MassReadingWithAll(
    @Embedded
    val massReadingEntity: MassReadingEntity,

    @Relation(
        parentColumn = "readingFK",
        entityColumn = "readingID",
        entity = BibleReadingEntity::class
    )
    var lectura: BibleReadingWithBook
) {
    val domainModel: MissaeLectionum
        get() {
            val theModel = lectura.domainModelMisa
            theModel.readingID = massReadingEntity.readingFK
            theModel.tema = massReadingEntity.tema
            theModel.setOrden(massReadingEntity.orden)
            return theModel
        }
}