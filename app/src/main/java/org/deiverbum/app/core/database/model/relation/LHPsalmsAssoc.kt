package org.deiverbum.app.core.database.model.relation

import androidx.room.Embedded
import androidx.room.Relation
import org.deiverbum.app.core.database.model.entity.LHPsalmJoinEntity
import org.deiverbum.app.core.database.model.entity.LHPsalmodyJoinEntity
import org.deiverbum.app.core.model.data.LHPsalm

/**
 * Obtiene la lista de salmos, asociando a [LHPsalmodyJoinEntity] con [LHPsalmWithAll]
 * la cual trae todos los elementos del salmo.
 *
 * @author A. Cedano
 * @version 1.0
 * @since 2023.1.3
 */
data class LHPsalmsAssoc(
    @Embedded
    var join: LHPsalmodyJoinEntity,

    @Relation(parentColumn = "groupID", entityColumn = "groupFK", entity = LHPsalmJoinEntity::class)
    var psalms: List<LHPsalmWithAll>,
    /*
        @Relation(parentColumn = "groupID", entityColumn = "groupFK", entity = LHAntiphonJoinEntity::class)
        var ant: List<LHAntiphonWithAll>*/
) {
    val domainModel: MutableList<LHPsalm>
        get() {
            val psalmsList: MutableList<LHPsalm> = ArrayList()
            for (s in psalms) {
                val dm = LHPsalm(1, "", "", "", 1, "")
                dm.psalm = s.psalm
                dm.quote = s.theQuote
                dm.theme = s.theme
                dm.epigraph = s.epigraph
                //dm.part = s.thePart!!
                dm.theOrder = s.theOrder
                psalmsList.add(dm)
            }
            return psalmsList
        }
}

fun LHPsalmsAssoc.asExternalModel(): MutableList<LHPsalm> {
    val emList: MutableList<LHPsalm> = ArrayList()
    for (ps in psalms) {
        emList.add(ps.asExternalModel())
    }
    return emList
}