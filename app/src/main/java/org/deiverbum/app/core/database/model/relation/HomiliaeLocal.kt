package org.deiverbum.app.core.database.model.relation

import androidx.room.Embedded
import androidx.room.Relation
import org.deiverbum.app.core.database.model.entity.LiturgyEntity
import org.deiverbum.app.core.database.model.entity.LiturgyHomilyJoinEntity
import org.deiverbum.app.core.database.model.entity.UniversalisEntity
import org.deiverbum.app.core.model.data.Homily
import org.deiverbum.app.core.model.data.HomilyList
import org.deiverbum.app.core.model.data.Universalis

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2023.1
 */
data class HomiliaeLocal(
    @Embedded
    var today: UniversalisEntity,

    @Relation(entity = LiturgyEntity::class, parentColumn = "liturgyFK", entityColumn = "liturgyID")
    var liturgy: LiturgyWithTime,

    @Relation(
        entity = LiturgyHomilyJoinEntity::class,
        parentColumn = "liturgyFK",
        entityColumn = "liturgyFK"
    )
    var homilyes: List<LiturgyWithHomilies>
) {
    val domainModel: Universalis
        get() {
            val dm = Universalis()
            dm.liturgyDay = liturgy.domainModel
            dm.todayDate = today.todayDate
            dm.hasSaint = today.hasSaint
            dm.liturgyDay.typeID = 9
            val homilyList = HomilyList()
            val listModel: MutableList<Homily?> = ArrayList()
            for (item in homilyes) {
                listModel.add(item.domainModel)
            }
            homilyList.homilyes = listModel
            dm.liturgyDay.homilyList = homilyList
            return dm
        }
}