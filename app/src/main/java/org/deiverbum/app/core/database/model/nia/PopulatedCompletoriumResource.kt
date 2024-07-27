package org.deiverbum.app.core.database.model.nia

import androidx.room.Embedded
import androidx.room.Relation
import org.deiverbum.app.core.database.model.entity.LHNightPrayerEntity
import org.deiverbum.app.core.database.model.entity.LiturgyEntity
import org.deiverbum.app.core.database.model.entity.UniversalisEntity
import org.deiverbum.app.core.database.model.entity.asExternalModel
import org.deiverbum.app.core.database.model.relation.LHNightPrayerAssoc
import org.deiverbum.app.core.database.model.relation.LiturgyTimeAssoc
import org.deiverbum.app.core.database.model.relation.asExternalModel
import org.deiverbum.app.core.database.model.relation.asExternalModelPsalmodia
import org.deiverbum.app.core.model.data.Conclusion
import org.deiverbum.app.core.model.data.LHCompletorium
import org.deiverbum.app.core.model.data.Liturgy
import org.deiverbum.app.core.model.data.Universalis
import org.deiverbum.app.core.model.data.UniversalisResource

data class PopulatedCompletoriumResource(
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

fun PopulatedCompletoriumResource.asExternalModel(): UniversalisResource {
    val conclusion = Conclusion()
    conclusion.setAntifonaVirgen(nightPrayer.virgin.virginEntity.antiphon)
    val breviarium = LHCompletorium(
        nightPrayer.kyrie.entity.asExternalModel(),
        nightPrayer.hymnus.himno.asExternalModel(),
        nightPrayer.asExternalModelPsalmodia(),
        nightPrayer.lectioBrevis.asExternalModel(),
        nightPrayer.nuncDimitis.asExternalModel(7),
        nightPrayer.prayer.asExternalModel(),
        conclusion, "completorium"
    )
    return UniversalisResource(
        data = listOf(
            Universalis(
                universalis.todayDate,
                Liturgy(
                    liturgia.parent.semana,
                    liturgia.parent.dia,
                    liturgia.parent.nombre,
                    liturgia.entity.asExternalModel(),
                    breviarium
                )
            )
        )
    )
}