package org.deiverbum.app.core.database.model.relation

import androidx.room.Embedded
import androidx.room.Relation
import org.deiverbum.app.core.database.model.entity.PaterEntity
import org.deiverbum.app.core.database.model.entity.PaterOpusEntity
import org.deiverbum.app.core.model.data.PaterOpus

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2023.1
 */
data class PaterOpusAll(
    @Embedded val paterOpusEntity: PaterOpusEntity,

    @Relation(parentColumn = "paterFK", entityColumn = "paterID")
    val paterEntity: PaterEntity
) {

    val domainModel: PaterOpus
        get() {
            val dm = PaterOpus()
            dm.pater = paterEntity.domainModel
            dm.opusName = paterOpusEntity.opusName
            dm.liturgyName = paterOpusEntity.liturgyName
            return dm
        }
}