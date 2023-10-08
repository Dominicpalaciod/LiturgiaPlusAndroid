package org.deiverbum.app.core.database.model.relation

import androidx.room.Embedded
import androidx.room.Ignore
import androidx.room.Relation
import org.deiverbum.app.core.database.model.entity.LiturgyEntity
import org.deiverbum.app.core.database.model.entity.MassReadingEntity
import org.deiverbum.app.core.database.model.entity.UniversalisEntity
import org.deiverbum.app.core.model.data.BibleComment
import org.deiverbum.app.core.model.data.BibleCommentList
import org.deiverbum.app.core.model.data.LiturgiaNew
import org.deiverbum.app.core.model.data.Traditio
import org.deiverbum.app.core.model.data.Universalis

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2023.1
 */
data class TodayComentarios(
    @Embedded
    var today: UniversalisEntity,

    @Relation(entity = LiturgyEntity::class, parentColumn = "liturgyFK", entityColumn = "liturgyID")
    var liturgy: LiturgyWithTime,

    @Relation(
        entity = MassReadingEntity::class,
        parentColumn = "massReadingFK",
        entityColumn = "liturgyFK"
    )
    var comments: List<MassReadingWithComments>
) {
    @Ignore
    var c = BibleCommentList()
    val domainModel: Universalis
        get() {
            val dm = Universalis()
            dm.liturgyDay = liturgy.domainModel
            dm.todayDate = today.todayDate
            dm.hasSaint = today.hasSaint
            dm.liturgyDay.typeID = 11
            val commentList = BibleCommentList()
            val allComentarios: MutableList<List<BibleComment>> = ArrayList()
            for (item in comments) {
                allComentarios += item.domainModel
            }
            commentList.allComentarios = allComentarios.toMutableList()
            dm.liturgyDay.bibleCommentList = commentList
            c = commentList
            return dm
        }
}

fun TodayComentarios.asExternalModel() = LiturgiaNew(
    Traditio.Comment(comments = liturgy.liturgyTime.liturgyName)
)