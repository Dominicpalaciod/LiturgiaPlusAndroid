package org.deiverbum.app.core.database.model.relation

import androidx.room.Embedded
import androidx.room.Relation
import org.deiverbum.app.core.database.model.entity.LHAntiphonJoinEntity
import org.deiverbum.app.core.database.model.entity.LHPsalmodyJoinEntity
import org.deiverbum.app.core.model.data.LHAntiphon

/**
 * Obtiene las antífonas, asociando a [LHAntiphonJoinEntity] con [LHAntiphonJoinEntity]
 * la cual trae todos los elementos del salmo.
 *
 * @author A. Cedano
 * @version 1.0
 * @since 2023.1.3
 */
data class LHAntiphonAssoc(
    @Embedded
    var join: LHPsalmodyJoinEntity,

    @Relation(
        parentColumn = "groupID",
        entityColumn = "groupFK",
        entity = LHAntiphonJoinEntity::class
    )
    var antiphons: List<LHAntiphonWithAll>,

    /*@Relation(
        parentColumn = "groupFK",
        entityColumn = "antiphonID",
        associateBy = Junction(LHAntiphonJoinEntity::class)

        //entity = LHAntiphonJoinEntity::class
    )
    var antiphonss: List<LHAntiphonEntity>,*/


) {
    val domainModel: MutableList<LHAntiphon>
        get() {
            //val dmLHPsalmody = LHPsalmody()
            //dmLHPsalmody.theType=joinEntity.theType
            val antiphonList: MutableList<LHAntiphon> = ArrayList()
            for (a in antiphons) {
                //val dm= LHAntiphon()
                //dm.antiphon=a.antiphon
                //dm.theOrder=a.theOrder
                //antiphonList.add(org.deiverbum.app.core.database.model.relation.asExternalModel())
            }
            return antiphonList
        }
}

fun LHAntiphonAssoc.asExternalModel(): MutableList<LHAntiphon> {
    val emList: MutableList<LHAntiphon> = ArrayList()
    for (a in antiphons) {
        emList.add(a.asExternalModel())
    }
    return emList
}