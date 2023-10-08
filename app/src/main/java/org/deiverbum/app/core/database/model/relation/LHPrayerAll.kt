package org.deiverbum.app.core.database.model.relation

import androidx.room.Embedded
import androidx.room.Relation
import org.deiverbum.app.core.database.model.entity.LHPrayerEntity
import org.deiverbum.app.core.database.model.entity.PrayerEntity
import org.deiverbum.app.core.database.model.entity.asExternalModel
import org.deiverbum.app.core.model.data.Oratio

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2023.1
 */
data class LHPrayerAll(
    @Embedded
    var lhPrayerEntity: LHPrayerEntity,

    @Relation(parentColumn = "prayerFK", entityColumn = "prayerID", entity = PrayerEntity::class)
    var prayerEntity: PrayerEntity
) {
    val domainModel: Oratio
        get() {
            val theModel = Oratio(1, "", 1)
            theModel.prayer = prayerEntity.texto
            return theModel
        }
}

fun LHPrayerAll.asExternalModel() = prayerEntity.asExternalModel()

