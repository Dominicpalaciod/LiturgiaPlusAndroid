package org.deiverbum.app.core.database.model.relation

import androidx.room.Embedded
import androidx.room.Relation
import org.deiverbum.app.core.database.model.entity.LiturgyEntity
import org.deiverbum.app.core.database.model.entity.MassReadingEntity
import org.deiverbum.app.core.database.model.entity.UniversalisEntity
import org.deiverbum.app.core.database.model.entity.asExternalModel
import org.deiverbum.app.core.model.data.BibleComment
import org.deiverbum.app.core.model.data.BibleCommentList
import org.deiverbum.app.core.model.data.Liturgy
import org.deiverbum.app.core.model.data.Traditio
import org.deiverbum.app.core.model.data.Universalis

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2023.1
 */
data class CommentariiLocal(
    @Embedded
    var universalis: UniversalisEntity,

    @Relation(entity = LiturgyEntity::class, parentColumn = "liturgyFK", entityColumn = "liturgyID")
    var liturgia: LiturgyWithTime,

    @Relation(
        entity = MassReadingEntity::class,
        parentColumn = "massReadingFK",
        entityColumn = "liturgyFK"
    )
    var comments: List<MassReadingWithComments>
)

fun CommentariiLocal.asExternalModel(): Universalis {
    val extModel = universalis.asExternalModel()
    val emList = BibleCommentList()
    val allComentarios: MutableList<List<BibleComment>> = ArrayList()
    for (item in comments) {
        allComentarios += item.asExternalModel()
    }
    emList.commentarii = allComentarios.toMutableList()
    val traditio = Traditio.Comment(emList)
    extModel.liturgyTime = liturgia.liturgyTime.asExternalModel()
    val extLiturgyDay = Liturgy(traditio, liturgia.liturgiaEntity.nombre, 11)
    extModel.liturgyDay = extLiturgyDay
    return extModel
}