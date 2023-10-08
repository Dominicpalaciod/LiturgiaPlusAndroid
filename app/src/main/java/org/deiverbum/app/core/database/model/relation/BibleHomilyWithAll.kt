package org.deiverbum.app.core.database.model.relation

import androidx.room.Embedded
import androidx.room.Relation
import org.deiverbum.app.core.database.model.entity.BibleHomilyJoinEntity
import org.deiverbum.app.core.database.model.entity.BibleHomilyThemeEntity
import org.deiverbum.app.core.database.model.entity.HomilyEntity
import org.deiverbum.app.core.model.data.BibleComment

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2023.1
 */
data class BibleHomilyWithAll(
    @Embedded
    val bibliaLecturaEntity: BibleHomilyJoinEntity,

    @Relation(
        parentColumn = "homilyFK",
        entityColumn = "homilyFK"
    )
    val themeEntity: BibleHomilyThemeEntity,

    @Relation(parentColumn = "homilyFK", entityColumn = "homilyID", entity = HomilyEntity::class)
    val homilia: HomilyAll
) {
    val domainModel: BibleComment
        get() {
            val theModel = BibleComment()
            theModel.cita = themeEntity.biblical!!
            theModel.tema = themeEntity.theological!!
            theModel.ref = themeEntity.reference!!
            theModel.paterOpus = homilia.paterOpusAll.domainModel
            theModel.texto = homilia.homilia.texto
            theModel.fecha = homilia.homilia.fecha.toString()
            return theModel
        }
}