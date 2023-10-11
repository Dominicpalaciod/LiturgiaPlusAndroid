package org.deiverbum.app.core.database.model.relation

import androidx.room.Embedded
import androidx.room.Relation
import org.deiverbum.app.core.database.model.entity.LHAntiphonEntity
import org.deiverbum.app.core.database.model.entity.LHAntiphonJoinEntity
import org.deiverbum.app.core.model.data.LHAntiphon

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2023.1.3
 */
data class LHAntiphonsWithOrder(
    @Embedded
    var join: LHAntiphonJoinEntity,

    @Relation(
        parentColumn = "antiphonFK",
        entityColumn = "antiphonID",
        entity = LHAntiphonEntity::class
    )
    var antiphonEntity: LHAntiphonEntity
)

fun LHAntiphonsWithOrder.asExternalModel() = LHAntiphon(
    antiphonID = antiphonEntity.antiphonID,
    antiphon = antiphonEntity.antiphon,
    join.theOrder
)