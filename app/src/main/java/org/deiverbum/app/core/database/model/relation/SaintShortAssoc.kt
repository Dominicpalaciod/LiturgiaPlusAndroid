package org.deiverbum.app.core.database.model.relation

import androidx.room.Embedded
import androidx.room.Relation
import org.deiverbum.app.core.database.model.entity.LiturgySaintJoinEntity
import org.deiverbum.app.core.database.model.entity.SaintEntity
import org.deiverbum.app.core.database.model.entity.SaintLifeEntity
import org.deiverbum.app.core.database.model.entity.SaintShortLifeEntity
import org.deiverbum.app.core.model.data.Sanctus

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2023.1.3
 */
data class SaintShortAssoc(
    @Embedded
    var join: LiturgySaintJoinEntity,

    @Relation(
        parentColumn = "saintFK",
        entityColumn = "saintFK",
        entity = SaintShortLifeEntity::class
    )
    var entity: SaintShortLifeEntity,

    @Relation(parentColumn = "saintFK", entityColumn = "saintID", entity = SaintEntity::class)
    var saint: SaintEntity,

    @Relation(parentColumn = "saintFK", entityColumn = "saintFK", entity = SaintLifeEntity::class)
    var longLife: SaintLifeEntity
) {
    val domainModel: Sanctus
        get() {
            val dm = Sanctus()
            val saintLife: String = if (entity.shortLife != "") {
                entity.shortLife
            } else {
                longLife.martyrology
            }
            dm.vida = saintLife
            dm.theName = saint.theName
            return dm
        }
}

