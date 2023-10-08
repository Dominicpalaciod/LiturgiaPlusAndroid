package org.deiverbum.app.core.database.model.relation

import androidx.room.Embedded
import androidx.room.Relation
import org.deiverbum.app.core.database.model.entity.LHAntiphonEntity
import org.deiverbum.app.core.database.model.entity.LHInvitatoryEntity
import org.deiverbum.app.core.database.model.entity.LHInvitatoryJoinEntity
import org.deiverbum.app.core.database.model.entity.asExternalModel
import org.deiverbum.app.core.model.data.LHInvitatory

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2023.1
 */
data class LHInvitatoryAll(
    @Embedded
    val joinEntity: LHInvitatoryJoinEntity,

    @Relation(parentColumn = "caseFK", entityColumn = "caseID", entity = LHInvitatoryEntity::class)
    val psalm: LHInvitatoryWithPsalm,

    @Relation(
        parentColumn = "antiphonFK",
        entityColumn = "antiphonID",
        entity = LHAntiphonEntity::class
    )
    val antiphon: LHAntiphonEntity


) {
    val domainModel: LHInvitatory
        get() {
            val ps = mutableListOf(psalm.domainModel)
            //val an=mutableListOf(antifona.domainModel)

            //val dm=psalm.domainModel
            //dm.lhAntiphon= antifona.domainModel
            return LHInvitatory(ps, mutableListOf(antiphon.asExternalModel()))
        }
}

/*fun LHInvitatoryAll.asExternalModel(): LHPsalmody{
    val aList: MutableList<LHAntiphon> = ArrayList()
    val sList: MutableList<LHPsalm> = mutableListOf(psalm.asExternalModel())

    return emList
}*/

fun LHInvitatoryAll.asExternalModel() = LHInvitatory(
    mutableListOf(
        psalm.asExternalModel()
    ), mutableListOf(antiphon.asExternalModel())
)