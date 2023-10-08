package org.deiverbum.app.core.database.model.relation

import androidx.room.Embedded
import androidx.room.Relation
import org.deiverbum.app.core.database.model.entity.HomilyEntity
import org.deiverbum.app.core.database.model.entity.PaterOpusEntity
import org.deiverbum.app.core.model.data.Homily
import org.deiverbum.app.core.model.data.LHOfficiumLectioAltera

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2023.1
 */
data class HomilyAll(
    @Embedded
    var homilia: HomilyEntity,

    @Relation(parentColumn = "opusFK", entityColumn = "opusID", entity = PaterOpusEntity::class)
    var paterOpusAll: PaterOpusAll
) {

    val patristicaDomainModel: LHOfficiumLectioAltera
        get() {
            val theModel = LHOfficiumLectioAltera()
            theModel.text = homilia.texto
            //theModel.padre = paterOpusAll.paterEntity.padre
            //theModel.obra = paterOpusAll.paterOpusEntity.opusName
            theModel.paterOpus = paterOpusAll.domainModel
            theModel.source = homilia.numero.toString()
            return theModel
        }

    val homilyDomailModel: Homily
        get() {
            val theModel = Homily()
            theModel.homily = homilia.texto
            theModel.homilyID = homilia.homiliaId
            theModel.paterOpus = paterOpusAll.domainModel
            theModel.date = homilia.fecha.toString()
            return theModel
        }
}