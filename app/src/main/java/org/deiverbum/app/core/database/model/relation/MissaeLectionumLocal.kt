package org.deiverbum.app.core.database.model.relation

import androidx.room.Embedded
import androidx.room.Relation
import org.deiverbum.app.core.database.model.entity.LiturgyEntity
import org.deiverbum.app.core.database.model.entity.MassReadingEntity
import org.deiverbum.app.core.database.model.entity.MassReadingJoinEntity
import org.deiverbum.app.core.database.model.entity.UniversalisEntity
import org.deiverbum.app.core.database.model.entity.asExternalModel
import org.deiverbum.app.core.model.data.Liturgy
import org.deiverbum.app.core.model.data.Missae
import org.deiverbum.app.core.model.data.MissaeLectionum
import org.deiverbum.app.core.model.data.MissaeLectionumList
import org.deiverbum.app.core.model.data.Universalis

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2023.1
 */
data class MissaeLectionumLocal(
    @Embedded
    var universalis: UniversalisEntity,

    @Relation(
        entity = LiturgyEntity::class,
        parentColumn = "massReadingFK",
        entityColumn = "liturgyID"
    )
    var liturgia: LiturgyTimeAssoc,

    @Relation(
        entity = LiturgyEntity::class,
        parentColumn = "previousFK",
        entityColumn = "liturgyID"
    )
    var previo: LiturgyWithTime,

    @Relation(
        entity = MassReadingJoinEntity::class,
        parentColumn = "massReadingFK",
        entityColumn = "liturgyFK"
    )
    var join: MassReadingJoinEntity,

    @Relation(
        entity = MassReadingEntity::class,
        parentColumn = "massReadingFK",
        entityColumn = "liturgyFK"
    )
    var lectionum: List<MassReadingWithAll>
)

fun MissaeLectionumLocal.asExternalModel(): Universalis {
    val emList = ArrayList<MissaeLectionum?>()
    for (item in lectionum) {
        emList.add(item.asExternalModel())
    }
    return Universalis(
        universalis.todayDate,
        universalis.timeFK,
        Liturgy(
            liturgia.parent.semana,
            liturgia.parent.dia,
            liturgia.parent.nombre,
            liturgia.entity.asExternalModel(),
            Missae(
                false,
                universalis.timeFK,
                "missae",
                MissaeLectionumList(emList, join.type)
            ),

            )
    )
}