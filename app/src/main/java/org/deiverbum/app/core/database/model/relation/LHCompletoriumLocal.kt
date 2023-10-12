package org.deiverbum.app.core.database.model.relation

import androidx.room.Embedded
import androidx.room.Relation
import org.deiverbum.app.core.database.model.entity.LHHymnJoinEntity
import org.deiverbum.app.core.database.model.entity.LHKyrieJoinEntity
import org.deiverbum.app.core.database.model.entity.LHNightPrayerEntity
import org.deiverbum.app.core.database.model.entity.LHPsalmodyJoinEntity
import org.deiverbum.app.core.database.model.entity.LiturgyEntity
import org.deiverbum.app.core.database.model.entity.UniversalisEntity
import org.deiverbum.app.core.database.model.entity.asExternalModel
import org.deiverbum.app.core.model.data.Conclusion
import org.deiverbum.app.core.model.data.LHCompletorium
import org.deiverbum.app.core.model.data.LHPsalmody
import org.deiverbum.app.core.model.data.Liturgy
import org.deiverbum.app.core.model.data.RitosIniciales
import org.deiverbum.app.core.model.data.Universalis

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2023.1
 */
data class LHCompletoriumLocal(
    @Embedded
    var universalis: UniversalisEntity,

    @Relation(entity = LiturgyEntity::class, parentColumn = "liturgyFK", entityColumn = "liturgyID")
    var liturgia: LiturgyTimeAssoc,

    @Relation(
        entity = LHNightPrayerEntity::class,
        parentColumn = "nightPrayerFK",
        entityColumn = "groupID"
    )
    //var nightPrayer: LHNightPrayerWithAll,
    var nightPrayer: LHNightPrayerAssoc,

    @Relation(
        entity = LHKyrieJoinEntity::class,
        parentColumn = "nightPrayerFK",
        entityColumn = "groupID"
    )
    //var nightPrayer: LHNightPrayerWithAll,
    var kyrie: LHKyrieAssoc,

    @Relation(
        entity = LHHymnJoinEntity::class,
        parentColumn = "nightPrayerFK",
        entityColumn = "groupID"
    )
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


    ) {

    val domainModel: Universalis
        get() {
            val dm = Universalis()
            dm.saintFK = universalis.saintFK
            //dm.liturgyDay = liturgy.domainModel
            dm.todayDate = universalis.todayDate
            dm.hasSaint = universalis.hasSaint
            dm.liturgyDay.typeID = 7
            //val bh = BreviaryHour()
            //val completas = LHCompletorium()
            val ritosIniciales = RitosIniciales()
            /*val kyrie = nightPrayer.kyrie.kyrieEntity.getDomainModel()
            ritosIniciales.kyrie = kyrie
            completas.setRitosIniciales(ritosIniciales)
            completas.lhHymn=nightPrayer.hymn.domainModel

            completas.salmodia=nightPrayer.salmodia.domainModel

            completas.biblicalShort = nightPrayer.readingFK.domainModel
            completas.setNuncDimitis(nightPrayer.nuncDimitisFK.getDomainModel(7))
            completas.setOracion(nightPrayer.prayer.domainModel)
            val conclusion = Conclusion()
            conclusion.setAntifonaVirgen(nightPrayer.virgin.virginEntity.antiphon)
            completas.setConclusion(conclusion)*/
            //bh.completas = completas
            //dm.liturgyDay.breviaryHour = bh
            return dm
        }
}

fun LHCompletoriumLocal.asExternalModel(): Universalis {
    val extModel = universalis.asExternalModel()
    val conclusion = Conclusion()
    conclusion.setAntifonaVirgen(nightPrayer.virgin.virginEntity.antiphon)
    val breviarium = LHCompletorium(
        kyrie.entity.asExternalModel(),
        hymnus.entity.asExternalModel(),
        LHPsalmody(psalmus.asExternalModel(), antiphonae.asExternalModel()),
        nightPrayer.readingFK.domainModel,
        nightPrayer.nuncDimitis.getDomainModel(7),
        nightPrayer.prayer.asExternalModel(),
        conclusion,
    )
    if (universalis.hasSaint == 1) {
        //breviarium.sanctus = sanctus!!.asExternalModel()
    }
    extModel.liturgyTime = liturgia.entity.asExternalModel()
    val extLiturgyDay = Liturgy(breviarium, liturgia.parent.nombre, 7)
    extModel.liturgyDay = extLiturgyDay
    return extModel

}
