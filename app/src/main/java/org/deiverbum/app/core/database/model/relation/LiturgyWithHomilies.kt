package org.deiverbum.app.core.database.model.relation

import androidx.room.Embedded
import androidx.room.Relation
import org.deiverbum.app.core.database.model.entity.HomilyEntity
import org.deiverbum.app.core.database.model.entity.LiturgyHomilyJoinEntity
import org.deiverbum.app.core.model.data.Homily

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2023.1
 */
data class LiturgyWithHomilies(
    @Embedded
    val joinEntity: LiturgyHomilyJoinEntity,

    @Relation(parentColumn = "homilyFK", entityColumn = "homilyID", entity = HomilyEntity::class)
    val homilia: HomilyAll
) {
    val domainModel: Homily
        get() {
            val dm = homilia.homilyDomailModel
            dm.tema = joinEntity.tema
            return dm
        }
}