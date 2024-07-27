package org.deiverbum.app.core.database.model.nia

import androidx.room.Embedded
import androidx.room.Relation
import org.deiverbum.app.core.database.model.entity.LHHymnJoinEntity
import org.deiverbum.app.core.database.model.entity.LHPrayerEntity
import org.deiverbum.app.core.database.model.entity.LHPsalmodyJoinEntity
import org.deiverbum.app.core.database.model.entity.LHReadingShortJoinEntity
import org.deiverbum.app.core.database.model.entity.LiturgyEntity
import org.deiverbum.app.core.database.model.entity.UniversalisEntity
import org.deiverbum.app.core.database.model.entity.asExternalModel
import org.deiverbum.app.core.database.model.relation.LHAntiphonAssoc
import org.deiverbum.app.core.database.model.relation.LHHymnAssoc
import org.deiverbum.app.core.database.model.relation.LHNonamLocal
import org.deiverbum.app.core.database.model.relation.LHPrayerAll
import org.deiverbum.app.core.database.model.relation.LHPsalmsAssoc
import org.deiverbum.app.core.database.model.relation.LHReadingShortAll
import org.deiverbum.app.core.database.model.relation.LiturgyTimeAssoc
import org.deiverbum.app.core.database.model.relation.asExternalModel
import org.deiverbum.app.core.model.data.LHIntermedia
import org.deiverbum.app.core.model.data.LHPsalmody
import org.deiverbum.app.core.model.data.Liturgy
import org.deiverbum.app.core.model.data.Universalis
import org.deiverbum.app.core.model.data.UniversalisResource

data class PopulatedNonamResource(
    @Embedded
    var universalis: UniversalisEntity,

    @Relation(entity = LiturgyEntity::class, parentColumn = "liturgyFK", entityColumn = "liturgyID")
    var liturgia: LiturgyTimeAssoc,

    @Relation(entity = LHHymnJoinEntity::class, parentColumn = "nHymnFK", entityColumn = "groupID")
    var hymnus: LHHymnAssoc,

    @Relation(
        entity = LHPsalmodyJoinEntity::class,
        parentColumn = "nAntiphonFK",
        entityColumn = "groupID"
    )
    var antiphonae: LHAntiphonAssoc,

    @Relation(
        entity = LHPsalmodyJoinEntity::class,
        parentColumn = "nPsalmFK",
        entityColumn = "groupID"
    )
    var psalmus: LHPsalmsAssoc,

    @Relation(
        entity = LHReadingShortJoinEntity::class,
        parentColumn = "nBiblicalFK",
        entityColumn = "groupID"
    )
    var lectioBrevis: LHReadingShortAll,

    @Relation(entity = LHPrayerEntity::class, parentColumn = "nPrayerFK", entityColumn = "groupID")
    var oratio: LHPrayerAll,
)

fun LHNonamLocal.asExternalModel() = Universalis(
    universalis.todayDate,
    //universalis.timeFK,
    Liturgy(
        liturgia.parent.semana,
        liturgia.parent.dia,
        liturgia.parent.nombre,
        liturgia.entity.asExternalModel(),
        LHIntermedia(
            hymnus.entity.asExternalModel(),
            LHPsalmody(
                psalmus.asExternalModel(),
                antiphonae.asExternalModel(),
                psalmus.join.theType
            ),
            lectioBrevis.asExternalModel(),
            oratio.asExternalModel(),
            5,
            "nonam"
        )
    )
)

fun PopulatedNonamResource.asExternalModel() = UniversalisResource(
    data =
    listOf(
        Universalis(
            universalis.todayDate,
            Liturgy(
                liturgia.parent.semana,
                liturgia.parent.dia,
                liturgia.parent.nombre,
                liturgia.entity.asExternalModel(),
                LHIntermedia(
                    hymnus.entity.asExternalModel(),
                    LHPsalmody(
                        psalmus.asExternalModel(),
                        antiphonae.asExternalModel(),
                        psalmus.join.theType
                    ),
                    lectioBrevis.asExternalModel(),
                    oratio.asExternalModel(),
                    4,
                    "intermedia"
                )
            )
        )
    )
)
