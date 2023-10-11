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
) {

    val domainModel: Universalis
        get() {
            val dm = Universalis()/*
            dm.saintFK = today.saintFK
            dm.liturgyDay = liturgy.domainModel
            dm.todayDate = today.todayDate
            dm.hasSaint = today.hasSaint
            dm.liturgyDay.typeID = 6
            dm.timeFK = liturgy.liturgyTime.timeID*/
            /*
                        val bh = BreviaryHour()
                        val visperas = Visperas()
                        visperas.setIsPrevious(dm.previousFK)
                        visperas.lhHymn = hymn.domainModel

                        visperas.salmodia = LHPsalmody(psalms.domainModel, antiphons.domainModel)
                        visperas.setLecturaBreve(readingShort.domainModel)
                        visperas.setGospelCanticle(gospelCanticle.getDomainModel(6))
                        visperas.setPreces(intercessions.domainModel)
                        visperas.setOracion(prayer.domainModel)
                        bh.setVisperas(visperas)
                        dm.liturgyDay.breviaryHour = bh*/
            return dm
        }
}

fun LHVesperasLocal.asExternalModel(): Universalis {
    val extModel = universalis.asExternalModel()
    if (universalis.previousFK > 1) {

    }
    val breviarium = LHVesperas(
        //invitatorium.asExternalModel(),
        hymnus.entity.asExternalModel(),
        LHPsalmody(psalmus.asExternalModel(), antiphonae.asExternalModel()),
        lectioBrevis.domainModel,
        canticumEvangelicum.getDomainModel(6),
        preces.domainModel,
        oratio.asExternalModel()
    )
    if (universalis.hasSaint == 1) {
        //breviarium.sanctus = sanctus!!.asExternalModel()
    }
    extModel.liturgyTime = liturgia.entity.asExternalModel()
    val extLiturgyDay = Liturgy(breviarium, liturgia.parent.nombre, 6)
    extModel.liturgyDay = extLiturgyDay
    //val theLiturgy=LiturgiaNew(breviarium)
    //theLiturgy.name = liturgyTime.parent.nombre
    return extModel
}