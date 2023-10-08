package org.deiverbum.app.core.database.model.relation

import androidx.room.Embedded
import androidx.room.Relation
import org.deiverbum.app.core.database.model.entity.LHIntercessionsEntity
import org.deiverbum.app.core.database.model.entity.LHIntercessionsJoinEntity
import org.deiverbum.app.core.model.data.LHIntercession

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2023.1
 */
data class LHIntercessionsWithAll(
    @Embedded
    val lhPatristica: LHIntercessionsJoinEntity,

    @Relation(
        parentColumn = "intercessionFK",
        entityColumn = "intercessionID",
        entity = LHIntercessionsEntity::class
    )
    val preces: LHIntercessionsEntity
) {
    val domainModel: LHIntercession
        get() {
            val theModel = LHIntercession()
            theModel.intro = preces.intro
            theModel.intercession = preces.preces
            return theModel
        }
}