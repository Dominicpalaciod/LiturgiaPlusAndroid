package org.deiverbum.app.core.database.model.relation

import androidx.room.Embedded
import androidx.room.Relation
import org.deiverbum.app.core.database.model.entity.SaintEntity
import org.deiverbum.app.core.database.model.entity.SaintLifeEntity
import org.deiverbum.app.core.model.data.Universalis

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2022.2
 */
data class SanctiLocal(
    @Embedded
    var saint: SaintEntity,

    @Relation(entity = SaintLifeEntity::class, parentColumn = "saintID", entityColumn = "saintFK")
    var saintLife: SaintLifeEntity,
) {

    val domainModel: Universalis
        get() {
            val dm = Universalis()
            dm.liturgyDay.typeID = 12
            val saintLife = saintLife.domainModel
            //saintLife.dia = saint.theDay.toString()
            //saintLife.mes = saint.theMonth.toString()
            //saintLife.vitaBrevis = s
            dm.liturgyDay.saintLife = saintLife
            return dm
        }
}