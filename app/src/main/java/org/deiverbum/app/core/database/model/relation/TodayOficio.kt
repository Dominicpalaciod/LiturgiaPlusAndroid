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
import org.deiverbum.app.core.model.data.BreviaryHour
import org.deiverbum.app.core.model.data.LHOfficium
import org.deiverbum.app.core.model.data.LHOfficiumLectionis
import org.deiverbum.app.core.model.data.LHPsalmody
import org.deiverbum.app.core.model.data.LiturgiaNew
import org.deiverbum.app.core.model.data.Universalis

/**
 * @author A. Cedano
 * @version 2.0
 * @since 2023.1
 */
data class TodayOficio(
    @Embedded
    var universalis: UniversalisEntity,

    @Relation(entity = LiturgyEntity::class, parentColumn = "liturgyFK", entityColumn = "liturgyID")
    var liturgyTime: LiturgyTimeAssoc,

    @Relation(entity = LHHymnJoinEntity::class, parentColumn = "oHymnFK", entityColumn = "groupID")
    var hymn: LHHymnAssoc,

    @Relation(
        entity = LiturgySaintJoinEntity::class,
        parentColumn = "liturgyFK",
        entityColumn = "liturgyFK"
    )
    var saint: SaintShortWithAll?,

    @Relation(
        entity = LHInvitatoryJoinEntity::class,
        parentColumn = "invitatoryFK",
        entityColumn = "groupID"
    )
    var invitatory: LHInvitatoryAll,

    @Relation(
        entity = LHInvitatoryJoinEntity::class,
        parentColumn = "invitatoryFK",
        entityColumn = "groupID"
    )
    var invitatoryy: LHInvitatoryAll,


    @Relation(
        entity = LHPsalmodyJoinEntity::class,
        parentColumn = "oAntiphonFK",
        entityColumn = "groupID"
    )
    var antiphons: LHAntiphonAssoc,


    /*@Relation(
        entity = LHPsalmodyJoinEntity::class,
        parentColumn = "oAntiphonFK",
        entityColumn = "groupID"
    )
    var antiphons: LHAntiphonAssoc,*/

    @Relation(
        entity = LHPsalmodyJoinEntity::class,
        parentColumn = "oPsalmFK",
        entityColumn = "groupID"
    )
    var psalms: LHPsalmsAssoc,

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
    var officeBiblical: LHOfficeBiblicalAssoc,

    @Relation(
        entity = LHOfficeBiblicalJoinEntity::class,
        parentColumn = "oBiblicalFK",
        entityColumn = "groupID"
    )
    var officeBiblicalEaster: LHOfficeEasterJoin,

    @Relation(
        entity = LHOfficePatristicJoinEntity::class,
        parentColumn = "oPatristicFK",
        entityColumn = "groupID"
    )
    var officePatristic: LHOfficePatristicAssoc,

    @Relation(entity = LHPrayerEntity::class, parentColumn = "oPrayerFK", entityColumn = "groupID")
    var prayer: LHPrayerAll
) {

    val domainModel: Universalis
        get() {
            val dm = Universalis()
            val bh = BreviaryHour()
            /*
                        //dm.saintFK = universalis.saintFK
                        dm.liturgyDay = liturgy.domainModel
                        dm.todayDate = universalis.todayDate
                        dm.hasSaint = universalis.hasSaint
                        dm.liturgyDay.typeID = 1
                        //dm.timeFK=liturgy.liturgyTime.timeID
                        bh.calendarTime=universalis.timeFK
                        if (universalis.oBiblicalFK == 600010101) {
                            dm.oBiblicalFK=universalis.oBiblicalFK
                            val oEaster = OficioEaster()
                            oEaster.lhOfficeOfReadingEaster = officeBiblicalEaster.domainModel
                            bh.setOficioEaster(oEaster)
                        } else {
                            val oficio = LHOfficium()
                            val ol = LHOfficeOfReading()
                            if (universalis.hasSaint == 1) {
                                oficio.setHasSaint(true)
                                oficio.santo = saint?.domainModel
                            }

                            oficio.invitatorio = invitatory.domainModel
                            //oficio.lhHymn=hymn.domainModel

                            oficio.salmodia = LHPsalmody(psalms.domainModel, antiphons.domainModel)

                            ol.biblica = officeBiblical.domainModel
                            ol.patristica = officePatristic.domainModel
                            ol.responsorio = officeVerse.theEntity.verse
                            oficio.setOfficeOfReading(ol)
                            if (universalis.oTeDeum == 1) {
                                oficio.teDeum = TeDeum()
                            }
                            oficio.setOracion(prayer.domainModel)
                            bh.setOficio(oficio)
                        }
                        dm.liturgyDay.breviaryHour = bh*/
            return dm
        }
}

fun TodayOficio.asExternalModel(): Universalis {
    val em = universalis.asExternalModel()
    val ps = LHPsalmody(psalms.asExternalModel(), antiphons.asExternalModel())
    val o = LHOfficium(
        invitatory.asExternalModel(),
        hymn.entity.asExternalModel(), ps,
        LHOfficiumLectionis(
            officeBiblical.domainModel,
            officePatristic.domainModel,
            officeVerse.theEntity.verse,
            universalis.oTeDeum == 1
        ),
        prayer.domainModel
    )
    if (universalis.hasSaint == 1) {
        o.sanctus = saint!!.asExternalModel()
    }

    val l = LiturgiaNew(o)
    l.name = liturgyTime.parent.nombre
    //em.liturgy=LiturgiaNew(o)
    //em.liturgy.name=liturgyTime.parent.nombre
    em.liturgy = l
    em.liturgyTime = liturgyTime.entity.asExternalModel()
    //em.liturgyDay.liturgyTime=liturgyTime.entity.asExternalModel()
    return em

}