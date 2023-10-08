package org.deiverbum.app.core.database.model.relation

import androidx.room.Embedded
import androidx.room.Relation
import org.deiverbum.app.core.database.model.entity.HomilyEntity
import org.deiverbum.app.core.database.model.entity.LHOfficePatristicEntity
import org.deiverbum.app.core.database.model.entity.LHResponsoryEntity
import org.deiverbum.app.core.model.data.LHOfficiumLectioAltera

/**
 *
 * Obtiene las lecturas patrísticas del Oficio de Lecturas,
 * y el responsorio correspondiente.
 *
 * @author A. Cedano
 * @version 1.0
 * @since 2023.1
 */
data class LHOfficePatristicWithAll(
    @Embedded
    var lhPatristica: LHOfficePatristicEntity,

    @Relation(parentColumn = "homilyFK", entityColumn = "homilyID", entity = HomilyEntity::class)
    var homilyAll: HomilyAll,

    @Relation(
        parentColumn = "responsoryFK",
        entityColumn = "responsoryID",
        entity = LHResponsoryEntity::class
    )
    var lhResponsorio: LHResponsoryEntity
) {
    val domainModelOficio: LHOfficiumLectioAltera
        get() {
            try {
                val theModel = homilyAll.patristicaDomainModel
                theModel.paterOpus = homilyAll.paterOpusAll.domainModel
                theModel.theme = lhPatristica.tema
                theModel.source = lhPatristica.fuente
                theModel.theOrder = lhPatristica.orden
                theModel.responsorioLargo = lhResponsorio.domainModel
                return theModel
            } catch (_: Exception) {
            }
            return LHOfficiumLectioAltera()
        }
}