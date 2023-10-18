package org.deiverbum.app.core.database.model.relation

import androidx.room.Embedded
import androidx.room.Relation
import org.deiverbum.app.core.database.model.entity.LHNightPrayerEntity
import org.deiverbum.app.core.database.model.entity.LiturgyEntity
import org.deiverbum.app.core.database.model.entity.UniversalisEntity
import org.deiverbum.app.core.database.model.entity.asExternalModel
import org.deiverbum.app.core.model.data.Conclusion
import org.deiverbum.app.core.model.data.LHCompletorium
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
    var nightPrayer: LHNightPrayerAssoc,

    )

fun LHCompletoriumLocal.asExternalModel(): Universalis {
    val extModel = universalis.asExternalModel()
    val conclusion = Conclusion()
    conclusion.setAntifonaVirgen(nightPrayer.virgin.virginEntity.antiphon)
    val breviarium = LHCompletorium(
        nightPrayer.kyrie.entity.asExternalModel(),
        nightPrayer.hymnus.himno.asExternalModel(),
        nightPrayer.asExternalModelPsalmodia(),
        nightPrayer.lectioBrevis.asExternalModel(),
        nightPrayer.nuncDimitis.asExternalModel(7),
        nightPrayer.prayer.asExternalModel(),
        conclusion, "completorium",
        //liturgia.entity.asExternalModel()
    )

    extModel.liturgyTime = liturgia.entity.asExternalModel()
    //val extLiturgyDay = Liturgy(breviarium, liturgia.parent.nombre, 7)
    //extModel.liturgia = extLiturgyDay
    return extModel
}
