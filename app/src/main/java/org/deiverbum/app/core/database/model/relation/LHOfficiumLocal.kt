package org.deiverbum.app.core.database.model.relation

import androidx.room.Embedded
import androidx.room.Relation
import org.deiverbum.app.core.database.model.entity.LHHymnJoinEntity
import org.deiverbum.app.core.database.model.entity.LHInvitatoryJoinEntity
import org.deiverbum.app.core.database.model.entity.LHOfficeBiblicalJoinEntity
import org.deiverbum.app.core.database.model.entity.LHOfficePatristicJoinEntity
import org.deiverbum.app.core.database.model.entity.LHOfficeVerseJoinEntity
import org.deiverbum.app.core.database.model.entity.LHPrayerEntity
import org.deiverbum.app.core.database.model.entity.LHPsalmodyJoinEntity
import org.deiverbum.app.core.database.model.entity.LiturgyEntity
import org.deiverbum.app.core.database.model.entity.LiturgySaintJoinEntity
import org.deiverbum.app.core.database.model.entity.UniversalisEntity
import org.deiverbum.app.core.database.model.entity.asExternalModel
import org.deiverbum.app.core.model.data.LHOfficium
import org.deiverbum.app.core.model.data.LHOfficiumLectionis
import org.deiverbum.app.core.model.data.LHPsalmody
import org.deiverbum.app.core.model.data.Liturgy
import org.deiverbum.app.core.model.data.Universalis
import org.deiverbum.app.util.Constants.EASTER_CODE

/**
 * @author A. Cedano
 * @version 2.0
 * @since 2023.1
 */
data class LHOfficiumLocal(
    @Embedded
    var universalis: UniversalisEntity,

    @Relation(entity = LiturgyEntity::class, parentColumn = "liturgyFK", entityColumn = "liturgyID")
    var liturgyTime: LiturgyTimeAssoc,

    @Relation(entity = LHHymnJoinEntity::class, parentColumn = "oHymnFK", entityColumn = "groupID")
    var hymnus: LHHymnAssoc,

    @Relation(
        entity = LiturgySaintJoinEntity::class,
        parentColumn = "liturgyFK",
        entityColumn = "liturgyFK"
    )
    var sanctus: SaintShortWithAll?,

    @Relation(
        entity = LHInvitatoryJoinEntity::class,
        parentColumn = "invitatoryFK",
        entityColumn = "groupID"
    )
    var invitatorio: LHInvitatoryAll,

    @Relation(
        entity = LHPsalmodyJoinEntity::class,
        parentColumn = "oAntiphonFK",
        entityColumn = "groupID"
    )
    var antiphonae: LHAntiphonAssoc,

    @Relation(
        entity = LHPsalmodyJoinEntity::class,
        parentColumn = "oPsalmFK",
        entityColumn = "groupID"
    )
    var psalmus: LHPsalmsAssoc,

    @Relation(
        entity = LHOfficeVerseJoinEntity::class,
        parentColumn = "oVerseFK",
        entityColumn = "groupID"
    )
    var officeVerse: LHOficceVerseAll,

    @Relation(
        entity = LHOfficeBiblicalJoinEntity::class,
        parentColumn = "oBiblicalFK",
        entityColumn = "groupID"
    )
    var lectioPrima: LHOfficeBiblicalAssoc,

    @Relation(
        entity = LHOfficePatristicJoinEntity::class,
        parentColumn = "oPatristicFK",
        entityColumn = "groupID"
    )
    var lectioAltera: LHOfficePatristicAssoc,

    @Relation(entity = LHPrayerEntity::class, parentColumn = "oPrayerFK", entityColumn = "groupID")
    var oratio: LHPrayerAll
)

fun LHOfficiumLocal.asExternalModel(): Universalis {
    val extModel = universalis.asExternalModel()
    if (universalis.oBiblicalFK == EASTER_CODE) {
        extModel.oBiblicalFK = universalis.oBiblicalFK
        return extModel
    }
    val psalmodia = LHPsalmody(psalmus.asExternalModel(), antiphonae.asExternalModel())
    val breviarium = LHOfficium(
        invitatorio.asExternalModel(),
        hymnus.entity.asExternalModel(),
        psalmodia,
        LHOfficiumLectionis(
            lectioPrima.domainModel,
            lectioAltera.domainModel,
            officeVerse.theEntity.verse,
            universalis.oTeDeum == 1
        ),
        oratio.domainModel
    )
    if (universalis.hasSaint == 1) {
        breviarium.sanctus = sanctus!!.asExternalModel()
    }

    extModel.liturgyTime = liturgyTime.entity.asExternalModel()
    val extLiturgyDay = Liturgy(breviarium, liturgyTime.parent.nombre, 1)
    extModel.liturgyDay = extLiturgyDay
    return extModel
}

