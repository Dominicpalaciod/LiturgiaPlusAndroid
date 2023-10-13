package org.deiverbum.app.core.database.model.relation

import androidx.room.Embedded
import androidx.room.Relation
import org.deiverbum.app.core.database.model.entity.LiturgyEntity
import org.deiverbum.app.core.database.model.entity.LiturgyHomilyJoinEntity
import org.deiverbum.app.core.database.model.entity.UniversalisEntity
import org.deiverbum.app.core.database.model.entity.asExternalModel
import org.deiverbum.app.core.model.data.HomilyList
import org.deiverbum.app.core.model.data.Liturgy
import org.deiverbum.app.core.model.data.Missae
import org.deiverbum.app.core.model.data.Universalis

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2023.1
 */
data class HomiliaeLocal(
    @Embedded
    var universalis: UniversalisEntity,

    @Relation(entity = LiturgyEntity::class, parentColumn = "liturgyFK", entityColumn = "liturgyID")
    var liturgia: LiturgyWithTime,

    @Relation(
        entity = LiturgyHomilyJoinEntity::class,
        parentColumn = "liturgyFK",
        entityColumn = "liturgyFK"
    )
    var homilyes: List<LiturgyWithHomilies>
)

fun HomiliaeLocal.asExternalModel(): Universalis {
    val extModel = universalis.asExternalModel()
    val homilyList = HomilyList()
    for (item in homilyes) {
        homilyList.homilyes.add(item.asExternalModel())
    }
    val missae = Missae(
        universalis.timeFK,
        homilyList
    )
    extModel.liturgyTime = liturgia.liturgyTime.asExternalModel()
    val extLiturgyDay = Liturgy(missae, liturgia.liturgiaEntity.nombre, 9)
    extModel.liturgyDay = extLiturgyDay
    return extModel
}