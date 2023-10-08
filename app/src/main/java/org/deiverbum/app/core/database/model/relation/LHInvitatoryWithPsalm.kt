package org.deiverbum.app.core.database.model.relation

import androidx.room.Embedded
import androidx.room.Relation
import org.deiverbum.app.core.database.model.entity.LHInvitatoryEntity
import org.deiverbum.app.core.database.model.entity.LHInvitatoryJoinEntity
import org.deiverbum.app.core.database.model.entity.LHPsalmEntity
import org.deiverbum.app.core.model.data.LHPsalm

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2023.1
 */
data class LHInvitatoryWithPsalm(
    @Embedded
    val invitatorio: LHInvitatoryEntity,

    @Relation(
        parentColumn = "caseID",
        entityColumn = "caseFK",
        entity = LHInvitatoryJoinEntity::class
    )
    val invitatorioEntity: LHInvitatoryJoinEntity,

    @Relation(parentColumn = "psalmFK", entityColumn = "psalmID", entity = LHPsalmEntity::class)
    val psalm: LHPsalmEntity
) {

    val domainModel: LHPsalm
        get() {
            val dm = LHPsalm(1, "", "", "", 1, "")
            dm.psalm = psalm.salmo
            dm.quote = psalm.salmoRef!!
            return dm
        }
}

fun LHInvitatoryWithPsalm.asExternalModel() = LHPsalm(
    theOrder = 0,
    quote = psalm.salmoRef!!,
    theme = "",
    epigraph = "",
    thePart = 0,
    psalm = psalm.salmo
)