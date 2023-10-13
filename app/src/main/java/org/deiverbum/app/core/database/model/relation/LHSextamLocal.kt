package org.deiverbum.app.core.database.model.relation

import androidx.room.Embedded
import androidx.room.Relation
import org.deiverbum.app.core.database.model.entity.LHHymnJoinEntity
import org.deiverbum.app.core.database.model.entity.LHPrayerEntity
import org.deiverbum.app.core.database.model.entity.LHPsalmodyJoinEntity
import org.deiverbum.app.core.database.model.entity.LHReadingShortJoinEntity
import org.deiverbum.app.core.database.model.entity.LiturgyEntity
import org.deiverbum.app.core.database.model.entity.UniversalisEntity
import org.deiverbum.app.core.database.model.entity.asExternalModel
import org.deiverbum.app.core.model.data.LHIntermedia
import org.deiverbum.app.core.model.data.LHPsalmody
import org.deiverbum.app.core.model.data.Liturgy
import org.deiverbum.app.core.model.data.Universalis

/**
 * Obtiene el contenido de la hora **`Sexta`** desde la base de datos.
 *
 * @author A. Cedano
 * @version 1.0
 * @since 2022.2
 */
data class LHSextamLocal(
    @Embedded
    var universalis: UniversalisEntity,

    @Relation(entity = LiturgyEntity::class, parentColumn = "liturgyFK", entityColumn = "liturgyID")
    var liturgia: LiturgyTimeAssoc,

    @Relation(entity = LHHymnJoinEntity::class, parentColumn = "sHymnFK", entityColumn = "groupID")
    var hymnus: LHHymnAssoc,

    @Relation(
        entity = LHPsalmodyJoinEntity::class,
        parentColumn = "sAntiphonFK",
        entityColumn = "groupID"
    )
    var antiphonae: LHAntiphonAssoc,

    @Relation(
        entity = LHPsalmodyJoinEntity::class,
        parentColumn = "sPsalmFK",
        entityColumn = "groupID"
    )
    var psalmus: LHPsalmsAssoc,

    @Relation(
        entity = LHReadingShortJoinEntity::class,
        parentColumn = "sBiblicalFK",
        entityColumn = "groupID"
    )
    var lectioBrevis: LHReadingShortAll,

    @Relation(entity = LHPrayerEntity::class, parentColumn = "sPrayerFK", entityColumn = "groupID")
    var oratio: LHPrayerAll,
)

fun LHSextamLocal.asExternalModel(): Universalis {
    val extModel = universalis.asExternalModel()
    val breviarium = LHIntermedia(
        hymnus.entity.asExternalModel(),
        LHPsalmody(psalmus.asExternalModel(), antiphonae.asExternalModel()),
        lectioBrevis.asExternalModel(),
        oratio.asExternalModel(),
        4
    )
    extModel.liturgyTime = liturgia.entity.asExternalModel()
    val extLiturgyDay = Liturgy(breviarium, liturgia.parent.nombre, 4)
    extModel.liturgyDay = extLiturgyDay
    return extModel
}
