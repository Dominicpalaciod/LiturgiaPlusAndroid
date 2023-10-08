package org.deiverbum.app.core.database.model.relation

import androidx.room.Embedded
import androidx.room.Relation
import org.deiverbum.app.core.database.model.entity.LHOfficeBiblicalEasterEntity
import org.deiverbum.app.core.database.model.entity.LHOfficeBiblicalJoinEntity
import org.deiverbum.app.core.database.model.entity.LHPsalmodyJoinEntity
import org.deiverbum.app.core.model.data.LHOfficeBiblicalEaster
import org.deiverbum.app.core.model.data.LHOfficeOfReadingEaster
import org.deiverbum.app.core.model.data.LHOfficiumLectioAltera
import org.deiverbum.app.core.model.data.LHOfficiumLectioPrior

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2023.1
 */
data class LHOfficeEasterJoin(
    @Embedded
    var biblical: LHOfficeBiblicalJoinEntity,

    @Relation(
        parentColumn = "groupID",
        entityColumn = "groupFK",
        entity = LHOfficeBiblicalEasterEntity::class
    )
    var lstEaster: List<LHOfficeEasterAll>,

    @Relation(
        parentColumn = "groupID",
        entityColumn = "groupID",
        entity = LHPsalmodyJoinEntity::class
    )
    var entityPsalmody: LHPsalmodyAssoc? = null
) {
    val domainModel: LHOfficeOfReadingEaster
        get() {
            val a: MutableList<LHOfficiumLectioPrior> = ArrayList()
            val b: MutableList<LHOfficiumLectioAltera> = ArrayList()

            val theModel = LHOfficeOfReadingEaster(
                a,
                b,
                false
            )
            val theList: MutableList<LHOfficeBiblicalEaster> = ArrayList()
            for (item in lstEaster) {
                theList.add(item.domainModel)
            }
            theList.sortBy { it.getOrden() }
            theModel.setBiblicalE(theList)
            theModel.lhPsalmody = entityPsalmody!!.domainModel
            return theModel
        }
}