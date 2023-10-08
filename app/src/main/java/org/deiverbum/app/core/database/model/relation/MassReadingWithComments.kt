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
            val theModel = lecturaOne.domainModelMisa
            theModel.tema = misaLectura.tema
            theModel.setOrden(misaLectura.orden)
            return theModel
        }
    val domainModel: MutableList<BibleComment>
        get() {
            val listModel: MutableList<BibleComment> = ArrayList()
            if (lectura.isNotEmpty()) {
                for (item in lectura) {
                    val theModel = item.domainModel
                    val biblica = biblicaMisa
                    biblica.setOrden(misaLectura.orden)
                    theModel.biblica = biblica
                    listModel.add(theModel)
                }
            }
            return listModel
        }
}