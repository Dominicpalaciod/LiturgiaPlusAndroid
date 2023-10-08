package org.deiverbum.app.core.database.model.relation

import androidx.room.Embedded
import androidx.room.Relation
import org.deiverbum.app.core.database.model.entity.LHAntiphonEntity
import org.deiverbum.app.core.database.model.entity.LHGospelCanticleEntity
import org.deiverbum.app.core.model.data.LHGospelCanticle

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2023.1
 */
data class LHGospelCanticleWithAntiphon(
    @Embedded
    var ce: LHGospelCanticleEntity,

    @Relation(
        parentColumn = "antiphonFK",
        entityColumn = "antiphonID",
        entity = LHAntiphonEntity::class
    )
    var antifona: LHAntiphonEntity
) {

    fun getDomainModel(tipo: Int?): LHGospelCanticle {
        val ce = LHGospelCanticle()
        ce.typeID = tipo!!
        ce.lhAntiphon = antifona.domainModel
        return ce
    }
}