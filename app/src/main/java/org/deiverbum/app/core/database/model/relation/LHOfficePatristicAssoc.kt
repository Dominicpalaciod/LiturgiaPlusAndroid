package org.deiverbum.app.core.database.model.relation

import androidx.room.Embedded
import androidx.room.Relation
import org.deiverbum.app.core.database.model.entity.LHOfficePatristicEntity
import org.deiverbum.app.core.database.model.entity.LHOfficePatristicJoinEntity
import org.deiverbum.app.core.model.data.LHOfficiumLectioAltera

/**
 *
 * Obtiene las lecturas patrísticas del Oficio de Lecturas,
 * asociando a [LHOfficePatristicJoinEntity] con [LHOfficePatristicWithAll].
 *
 * @author A. Cedano
 * @version 1.0
 * @since 2023.1.3
 */
data class LHOfficePatristicAssoc(
    @Embedded
    var joinEntity: LHOfficePatristicJoinEntity,

    @Relation(
        parentColumn = "groupID",
        entityColumn = "groupFK",
        entity = LHOfficePatristicEntity::class
    )
    var lhEntity: List<LHOfficePatristicWithAll> = emptyList()
) {


    val domainModel: MutableList<LHOfficiumLectioAltera>
        get() {
            val theList: MutableList<LHOfficiumLectioAltera> = ArrayList()
            if (lhEntity.isNotEmpty()) {
                for (item in lhEntity) {
                    theList.add(item.domainModelOficio)
                }
            }
            return theList
        }
}
