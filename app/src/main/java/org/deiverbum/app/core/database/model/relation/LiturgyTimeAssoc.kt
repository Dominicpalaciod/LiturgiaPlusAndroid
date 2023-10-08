package org.deiverbum.app.core.database.model.relation

import androidx.room.Embedded
import androidx.room.Relation
import org.deiverbum.app.core.database.model.entity.LiturgyEntity
import org.deiverbum.app.core.database.model.entity.LiturgyTimeEntity

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2023.1.3
 */
data class LiturgyTimeAssoc(
    @Embedded
    val parent: LiturgyEntity,

    @Relation(parentColumn = "timeFK", entityColumn = "timeID")
    val entity: LiturgyTimeEntity
)