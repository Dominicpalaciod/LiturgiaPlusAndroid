package org.deiverbum.app.core.database.model.relation

import androidx.room.Embedded
import androidx.room.Relation
import org.deiverbum.app.core.database.model.entity.LiturgyEntity
import org.deiverbum.app.core.database.model.entity.MassReadingEntity
import org.deiverbum.app.core.database.model.entity.MassReadingJoinEntity
import org.deiverbum.app.core.database.model.entity.UniversalisEntity
import org.deiverbum.app.core.database.model.entity.asExternalModel
import org.deiverbum.app.core.model.data.LiturgiaNew
import org.deiverbum.app.core.model.data.Missae
import org.deiverbum.app.core.model.data.MissaeLectionum
import org.deiverbum.app.core.model.data.MissaeLectionumList
import org.deiverbum.app.core.model.data.Universalis

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2023.1
 */
data class TodayMisaLecturas(
    @Embedded
    var universalis: UniversalisEntity,

    @Relation(
        entity = LiturgyEntity::class,
        parentColumn = "massReadingFK",
        entityColumn = "liturgyID"
    )
    var feria: LiturgyTimeAssoc,

    @Relation(
        entity = LiturgyEntity::class,
        parentColumn = "previousFK",
        entityColumn = "liturgyID"
    )
    var previo: LiturgyWithTime,

    @Relation(
        entity = MassReadingJoinEntity::class,
        parentColumn = "massReadingFK",
        entityColumn = "liturgyFK"
    )
    var joinTable: MassReadingJoinEntity,

    @Relation(
        entity = MassReadingEntity::class,
        parentColumn = "massReadingFK",
        entityColumn = "liturgyFK"
    )
    var lectionum: List<MassReadingWithAll>
) {

    val domainModel: Universalis
        get() {
            val dm = Universalis()
            /*
            dm.liturgyDay = feria.domainModel
            dm.liturgyPrevious = if (today.previousFK > 1) previo.domainModel else null
            dm.todayDate = today.todayDate
            dm.hasSaint = today.hasSaint
            dm.setMLecturasFK(today.massReadingFK)
            dm.liturgyDay.typeID = 10
            val mr = MissaeLectionumList()
            mr.type = joinTable.type
            val listModel: MutableList<MissaeLectionum?> = ArrayList()
            for (item in lecturas) {
                listModel.add(item.domainModel)
            }
            listModel.sortBy { it?.getOrden() }
            mr.lecturas = listModel
            dm.liturgyDay.massReadingList = mr

             */
            return dm
        }
}

fun TodayMisaLecturas.asExternalModel(): Universalis {
    val em = universalis.asExternalModel()
    val emList: MutableList<MissaeLectionum?> = ArrayList()
    val mr = MissaeLectionumList()
    mr.type = joinTable.type
    for (item in lectionum) {
        emList.add(item.domainModel)
    }
    emList.sortBy { it?.getOrden() }
    //mr.lecturas = emList
    //em.liturgyDay.massReadingList = mr
    val l = LiturgiaNew(Missae(emList))
    l.name = feria.parent.nombre
    em.liturgy = l
    em.liturgyTime = feria.entity.asExternalModel()
    //em.liturgyDay.liturgyTime=liturgyTime.entity.asExternalModel()
    return em

}