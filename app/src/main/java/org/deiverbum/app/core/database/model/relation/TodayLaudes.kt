package org.deiverbum.app.core.database.model.relation

import androidx.room.Embedded
import androidx.room.Relation
import org.deiverbum.app.core.database.model.entity.LHGospelCanticleEntity
import org.deiverbum.app.core.database.model.entity.LHHymnJoinEntity
import org.deiverbum.app.core.database.model.entity.LHIntercessionsJoinEntity
import org.deiverbum.app.core.database.model.entity.LHInvitatoryJoinEntity
import org.deiverbum.app.core.database.model.entity.LHPrayerEntity
import org.deiverbum.app.core.database.model.entity.LHPsalmodyJoinEntity
import org.deiverbum.app.core.database.model.entity.LHReadingShortJoinEntity
import org.deiverbum.app.core.database.model.entity.LiturgyEntity
import org.deiverbum.app.core.database.model.entity.LiturgySaintJoinEntity
import org.deiverbum.app.core.database.model.entity.UniversalisEntity
import org.deiverbum.app.core.database.model.entity.asExternalModel
import org.deiverbum.app.core.model.data.LHLaudes
import org.deiverbum.app.core.model.data.LHPsalmody
import org.deiverbum.app.core.model.data.LiturgiaNew
import org.deiverbum.app.core.model.data.Universalis

/**
 * @author A. Cedano
 * @version 2023.1.3
 * @since 2023.1
 */
data class TodayLaudes(
    @Embedded
    var universalis: UniversalisEntity,

    @Relation(entity = LiturgyEntity::class, parentColumn = "liturgyFK", entityColumn = "liturgyID")
    var liturgyTime: LiturgyTimeAssoc,

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
    var invitatorium: LHInvitatoryAll,

    @Relation(entity = LHHymnJoinEntity::class, parentColumn = "lHymnFK", entityColumn = "groupID")
    var hymnus: LHHymnAssoc,

    @Relation(
        entity = LHPsalmodyJoinEntity::class,
        parentColumn = "lAntiphonFK",
        entityColumn = "groupID"
    )
    var antiphonae: LHAntiphonAssoc,

    @Relation(
        entity = LHPsalmodyJoinEntity::class,
        parentColumn = "lPsalmFK",
        entityColumn = "groupID"
    )
    var psalmus: LHPsalmsAssoc,

    @Relation(
        entity = LHReadingShortJoinEntity::class,
        parentColumn = "lBiblicalFK",
        entityColumn = "groupID"
    )
    var lectioBrevis: LHReadingShortAll,

    @Relation(
        entity = LHIntercessionsJoinEntity::class,
        parentColumn = "lIntercessionsFK",
        entityColumn = "groupID"
    )
    var preces: LHIntercessionsWithAll,

    @Relation(entity = LHPrayerEntity::class, parentColumn = "lPrayerFK", entityColumn = "groupID")
    var oratio: LHPrayerAll,

    @Relation(
        entity = LHGospelCanticleEntity::class,
        parentColumn = "lBenedictusFK",
        entityColumn = "groupID"
    )
    var canticumEvangelicum: LHGospelCanticleWithAntiphon
) {

    val domainModel: Universalis
        get() {
            val dm = Universalis()
            /*dm.saintFK = today.saintFK
            dm.liturgyDay = liturgy.domainModel
            dm.todayDate = today.todayDate
            dm.hasSaint = today.hasSaint
            dm.liturgyDay.typeID = 2
            dm.timeFK=liturgy.liturgyTime.timeID

            val bh = BreviaryHour()
            val laudes = LHLaudes()
            laudes.invitatorium = invitatory.domainModel
            if (dm.hasSaint == 1 && saint != null) {
                laudes.setHasSaint(true)
                laudes.santo = saint?.domainModel
            }
            laudes.lhHymn=hymn.domainModel

            laudes.salmodia = LHPsalmody(psalms.domainModel, antiphons.domainModel)
            //laudes.salmodia=antiphons.domainModel
            laudes.lectioBrevis = readingShort.domainModel
            laudes.canticumEvangelicum = gospelCanticle.getDomainModel(2)
            laudes.preces = intercessions.domainModel
            laudes.setOracion(prayer.domainModel)
            bh.setLaudes(laudes)*/
            //dm.liturgyDay.breviaryHour = bh
            return dm
        }
}

fun TodayLaudes.asExternalModel(): Universalis {
    val em = universalis.asExternalModel()
    val breviarium = LHLaudes(
        invitatorium.asExternalModel(),
        hymnus.entity.asExternalModel(),
        LHPsalmody(psalmus.asExternalModel(), antiphonae.asExternalModel()),
        lectioBrevis.domainModel,
        canticumEvangelicum.getDomainModel(2),
        preces.domainModel,
        oratio.asExternalModel()
    )

    if (universalis.hasSaint == 1) {
        breviarium.sanctus = sanctus!!.asExternalModel()
    }
    em.liturgy = LiturgiaNew(breviarium)
    em.liturgyTime = liturgyTime.entity.asExternalModel()
    //em.liturgyDay.liturgyTime=liturgyTime.entity.asExternalModel()
    return em

}