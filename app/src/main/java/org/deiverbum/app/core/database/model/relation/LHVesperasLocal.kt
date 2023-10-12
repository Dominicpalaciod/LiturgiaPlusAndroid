package org.deiverbum.app.core.database.model.relation

import androidx.room.Embedded
import androidx.room.Relation
import org.deiverbum.app.core.database.model.entity.LHGospelCanticleEntity
import org.deiverbum.app.core.database.model.entity.LHHymnJoinEntity
import org.deiverbum.app.core.database.model.entity.LHIntercessionsJoinEntity
import org.deiverbum.app.core.database.model.entity.LHPrayerEntity
import org.deiverbum.app.core.database.model.entity.LHPsalmodyJoinEntity
import org.deiverbum.app.core.database.model.entity.LHReadingShortJoinEntity
import org.deiverbum.app.core.database.model.entity.LiturgyEntity
import org.deiverbum.app.core.database.model.entity.UniversalisEntity
import org.deiverbum.app.core.database.model.entity.asExternalModel
import org.deiverbum.app.core.model.data.LHPsalmody
import org.deiverbum.app.core.model.data.LHVesperas
import org.deiverbum.app.core.model.data.Liturgy
import org.deiverbum.app.core.model.data.Universalis

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2022.2
 */
data class LHVesperasLocal(
    @Embedded
    var universalis: UniversalisEntity,

    @Relation(entity = LiturgyEntity::class, parentColumn = "liturgyFK", entityColumn = "liturgyID")
    var liturgia: LiturgyTimeAssoc,

    @Relation(
        entity = LiturgyEntity::class,
        parentColumn = "previousFK",
        entityColumn = "liturgyID"
    )
    var primaVesperas: LiturgyTimeAssoc,

    @Relation(entity = LHHymnJoinEntity::class, parentColumn = "lHymnFK", entityColumn = "groupID")
    var hymnus: LHHymnAssoc,

    @Relation(
        entity = LHPsalmodyJoinEntity::class,
        parentColumn = "vAntiphonFK",
        entityColumn = "groupID"
    )
    var antiphonae: LHAntiphonAssoc,

    @Relation(
        entity = LHPsalmodyJoinEntity::class,
        parentColumn = "vPsalmFK",
        entityColumn = "groupID"
    )
    var psalmus: LHPsalmsAssoc,

    @Relation(
        entity = LHReadingShortJoinEntity::class,
        parentColumn = "vBiblicalFK",
        entityColumn = "groupID"
    )
    var lectioBrevis: LHReadingShortAll,

    @Relation(
        entity = LHIntercessionsJoinEntity::class,
        parentColumn = "vIntercessionsFK",
        entityColumn = "groupID"
    )
    var preces: LHIntercessionsWithAll,

    @Relation(entity = LHPrayerEntity::class, parentColumn = "vPrayerFK", entityColumn = "groupID")
    var oratio: LHPrayerAll,

    @Relation(
        entity = LHGospelCanticleEntity::class,
        parentColumn = "vMagnificatFK",
        entityColumn = "groupID"
    )
    var canticumEvangelicum: LHGospelCanticleWithAntiphon
)

fun LHVesperasLocal.asExternalModel(): Universalis {
    val extModel = universalis.asExternalModel()
    val liturgiaAssoc: LiturgyTimeAssoc
    var isPrimaVesperas = false
    if (universalis.previousFK > 1) {
        liturgiaAssoc = primaVesperas
        isPrimaVesperas = true
    } else {
        liturgiaAssoc = liturgia
    }

    val breviarium = LHVesperas(
        hymnus.entity.asExternalModel(),
        LHPsalmody(psalmus.asExternalModel(), antiphonae.asExternalModel()),
        lectioBrevis.domainModel,
        canticumEvangelicum.getDomainModel(6),
        preces.domainModel,
        oratio.asExternalModel(),
        isPrimaVesperas
    )
    extModel.liturgyTime = liturgiaAssoc.entity.asExternalModel()
    extModel.liturgyDay = Liturgy(breviarium, liturgiaAssoc.parent.nombre, 6)
    return extModel
}