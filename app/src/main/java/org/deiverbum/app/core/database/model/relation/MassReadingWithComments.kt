package org.deiverbum.app.core.database.model.relation

import androidx.room.Embedded
import androidx.room.Relation
import org.deiverbum.app.core.database.model.entity.BibleHomilyJoinEntity
import org.deiverbum.app.core.database.model.entity.BibleReadingEntity
import org.deiverbum.app.core.database.model.entity.MassReadingEntity
import org.deiverbum.app.core.model.data.BibleComment
import org.deiverbum.app.core.model.data.MissaeLectionum

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2023.1
 */
data class MassReadingWithComments(
    @Embedded
    val misaLectura: MassReadingEntity,

    @Relation(
        parentColumn = "readingFK",
        entityColumn = "readingFK",
        entity = BibleHomilyJoinEntity::class
    )
    val lectura: List<BibleHomilyWithAll>,

    @Relation(
        parentColumn = "readingFK",
        entityColumn = "readingID",
        entity = BibleReadingEntity::class
    )
    val lecturaOne: BibleReadingWithBook
) {
    private val biblicaMisa: MissaeLectionum
        get() {
            return lecturaOne.asExternalModelMissae(misaLectura.orden, misaLectura.tema)
        }
}

fun MassReadingWithComments.asExternalModel(): MutableList<BibleComment> {
    val extModel: MutableList<BibleComment> = ArrayList()
    if (lectura.isNotEmpty()) {
        for (item in lectura) {
            val theModel = item.asExternalModel()
            theModel.biblica = lecturaOne.asExternalModelMissae(misaLectura.orden, misaLectura.tema)
            extModel.add(theModel)
        }
    }
    return extModel
}