package org.deiverbum.app.core.database.model.relation

import androidx.room.Embedded
import androidx.room.Relation
import org.deiverbum.app.core.database.model.entity.LHGospelCanticleEntity
import org.deiverbum.app.core.database.model.entity.LHHymnJoinEntity
import org.deiverbum.app.core.database.model.entity.LHIntercessionsJoinEntity
import org.deiverbum.app.core.database.model.entity.LHInvitatoryJoinEntity
import org.deiverbum.app.core.database.model.entity.LHOfficeBiblicalJoinEntity
import org.deiverbum.app.core.database.model.entity.LHOfficePatristicJoinEntity
import org.deiverbum.app.core.database.model.entity.LHOfficeVerseJoinEntity
import org.deiverbum.app.core.database.model.entity.LHPrayerEntity
import org.deiverbum.app.core.database.model.entity.LHPsalmodyJoinEntity
import org.deiverbum.app.core.database.model.entity.LHReadingShortJoinEntity
import org.deiverbum.app.core.database.model.entity.LiturgyEntity
import org.deiverbum.app.core.database.model.entity.LiturgySaintJoinEntity
import org.deiverbum.app.core.database.model.entity.MassReadingEntity
import org.deiverbum.app.core.database.model.entity.UniversalisEntity
import org.deiverbum.app.core.database.model.entity.asExternalModel
import org.deiverbum.app.core.model.data.LHMixtus
import org.deiverbum.app.core.model.data.Liturgy
import org.deiverbum.app.core.model.data.MissaeLectionum
import org.deiverbum.app.core.model.data.OficioEaster
import org.deiverbum.app.core.model.data.Universalis

/**
 * @author A. Cedano
 * @version 2.0
 * @since 2023.1
 */
data class LHMixtumLocal(
    @Embedded
    var universalis: UniversalisEntity,

    @Relation(entity = LiturgyEntity::class, parentColumn = "liturgyFK", entityColumn = "liturgyID")
    var liturgy: LiturgyEntity,

    @Relation(entity = LiturgyEntity::class, parentColumn = "liturgyFK", entityColumn = "liturgyID")
    var liturgyTime: LiturgyTimeAssoc,

    @Relation(entity = LHHymnJoinEntity::class, parentColumn = "lHymnFK", entityColumn = "groupID")
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

    @Relation(entity = LHHymnJoinEntity::class, parentColumn = "lHymnFK", entityColumn = "groupID")
    var hymnn: LHHymnWithAll,


    @Relation(
        entity = LHPsalmodyJoinEntity::class,
        parentColumn = "lAntiphonFK",
        entityColumn = "groupID"
    )
    var antiphons: LHAntiphonAssoc,

    @Relation(
        entity = LHPsalmodyJoinEntity::class,
        parentColumn = "lPsalmFK",
        entityColumn = "groupID"
    )
    var psalms: LHPsalmsAssoc,

    @Relation(
        entity = LHReadingShortJoinEntity::class,
        parentColumn = "lBiblicalFK",
        entityColumn = "groupID"
    )
    var readingShort: LHReadingShortAll,

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
    /*
        @Relation(
            entity = LHOfficeBiblicalJoinEntity::class,
            parentColumn = "oBiblicalFK",
            entityColumn = "groupID"
        )
        var officeBiblicalEaster: LHOfficeEasterJoin,
    */

    @Relation(
        entity = LHOfficePatristicJoinEntity::class,
        parentColumn = "oPatristicFK",
        entityColumn = "groupID"
    )
    var officePatristic: LHOfficePatristicAssoc,


    @Relation(
        entity = LHGospelCanticleEntity::class,
        parentColumn = "lBenedictusFK",
        entityColumn = "groupID"
    )
    var gospelCanticle: LHGospelCanticleWithAntiphon,

    @Relation(
        entity = MassReadingEntity::class,
        parentColumn = "massReadingFK",
        entityColumn = "liturgyFK"
    )
    var massReading: List<MassReadingWithAll>,

    @Relation(
        entity = LHIntercessionsJoinEntity::class,
        parentColumn = "lIntercessionsFK",
        entityColumn = "groupID"
    )
    var intercessions: LHIntercessionsWithAll,

    @Relation(entity = LHPrayerEntity::class, parentColumn = "lPrayerFK", entityColumn = "groupID")
    var prayer: LHPrayerAll
) {
    private val getGospels: List<MissaeLectionum?>
        get() {
            val listModel: MutableList<MissaeLectionum?> = ArrayList()
            for (item in massReading) {
                if (item.entity.orden >= 40) {
                    listModel.add(item.domainModel)
                }
            }
            return listModel
        }


    val domainModel: Universalis
        get() {
            val dm = Universalis()
            //dm.saintFK = universalis.saintFK
            //dm.liturgyDay = liturgy.domainModel
            dm.todayDate = universalis.todayDate
            //dm.hasSaint = today.hasSaint
            dm.liturgyDay.typeID = 0
            //dm.timeFK=liturgy.liturgyTime.timeID

            //val bh = BreviaryHour()
            //bh.calendarTime = universalis.timeFK

            if (universalis.oBiblicalFK == 600010101) {
                dm.oBiblicalFK = universalis.oBiblicalFK
                val oEaster = OficioEaster()
                //oEaster.lhOfficeOfReadingEaster = officeBiblicalEaster.domainModel
                //bh.setOficioEaster(oEaster)
            } else {
                //val LHMixtus = LHMixtus(hymn.entity.asExternalModel())
                /*val oficio = Oficio()
                val laudes = Laudes()
                if (universalis.hasSaint == 1) {
                    bh.setHasSaint(true)
                    bh.santo = saint?.domainModel
                    oficio.santo = saint?.domainModel

                }
                oficio.invitatorio = invitatory.domainModel
                laudes.lhHymn=hymn.domainModel

                laudes.salmodia = LHPsalmody(psalms.domainModel, antiphons.domainModel)
                laudes.lecturaBreve = readingShort.domainModel
                laudes.gospelCanticle = gospelCanticle.getDomainModel(2)
                laudes.preces = intercessions.domainModel
                laudes.setOracion(prayer.domainModel)
                val ol = LHOfficeOfReading()
                ol.biblica = officeBiblical.domainModel
                ol.patristica = officePatristic.domainModel
                ol.responsorio = officeVerse.theEntity.verse
                oficio.setOfficeOfReading(ol)
                LHMixtus.setOficio(oficio)
                LHMixtus.setLaudes(laudes)
                LHMixtus.misaLecturas = getGospels
                bh.setMixto(LHMixtus)
                bh.setOficio(oficio)
                bh.setLaudes(laudes)*/
            }
            //dm.liturgyDay.breviaryHour = bh
            return dm
        }
}

fun LHMixtumLocal.asExternalModel(): Universalis {
    val em = universalis.asExternalModel()
    em.liturgyDay = Liturgy(LHMixtus(hymn.entity.asExternalModel()))
    em.liturgyTime = liturgyTime.entity.asExternalModel()
    //em.liturgyDay.liturgyTime=liturgyTime.entity.asExternalModel()
    return em

}