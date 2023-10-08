package org.deiverbum.app.core.database.model.relation

import androidx.room.Embedded
import androidx.room.Relation
import org.deiverbum.app.core.database.model.entity.EpigraphEntity
import org.deiverbum.app.core.database.model.entity.LHPsalmEntity
import org.deiverbum.app.core.database.model.entity.LHPsalmJoinEntity
import org.deiverbum.app.core.database.model.entity.LHThemeEntity

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2023.1
 */

@Deprecated("Será reemplazada por LHPsalmWithAll")
data class LHPsalmodyWithAll(
    @Embedded
    var psalmodyEntity: LHPsalmJoinEntity,

    @Relation(parentColumn = "readingFK", entityColumn = "psalmID", entity = LHPsalmEntity::class)
    var psalmEntity: LHPsalmEntity,

    @Relation(parentColumn = "themeFK", entityColumn = "themeID", entity = LHThemeEntity::class)
    var themeEntity: LHThemeEntity? = null,

    @Relation(
        parentColumn = "epigraphFK",
        entityColumn = "epigraphID",
        entity = EpigraphEntity::class
    )
    var epigraphEntity: EpigraphEntity? = null
) {
    val epigraph: String
        get() = if (epigraphEntity != null && epigraphEntity!!.epigrafe.isNotEmpty()) epigraphEntity!!.epigrafe else ""

    val salmoText: String
        get() = psalmEntity.salmo
    val ref: String
        get() = if (!psalmEntity.salmoRef.isNullOrEmpty()) psalmEntity.salmoRef!! else ""

    val theme: String
        get() = if (!themeEntity?.tema.isNullOrEmpty()) themeEntity?.tema!! else ""

    val part: Int?
        get() = if (psalmodyEntity.thePart != null) psalmodyEntity.thePart else 0

    val orden: Int
        get() = psalmodyEntity.theOrder
}