package org.deiverbum.app.core.database.model.relation

import androidx.room.Embedded
import androidx.room.Relation
import org.deiverbum.app.core.database.model.entity.BibleBookEntity
import org.deiverbum.app.core.database.model.entity.BibleReadingEntity
import org.deiverbum.app.core.database.model.entity.asExternalModel
import org.deiverbum.app.core.model.data.BibleBook
import org.deiverbum.app.core.model.data.LHLectioBrevis
import org.deiverbum.app.core.model.data.LHOfficiumLectioPrior
import org.deiverbum.app.core.model.data.LHResponsorium
import org.deiverbum.app.core.model.data.LectioBiblica
import org.deiverbum.app.core.model.data.MissaeLectionum

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2023.1
 */

data class BibleReadingWithBook(
    @Embedded
    val lectura: BibleReadingEntity,

    @Relation(parentColumn = "bookFK", entityColumn = "bookID")
    var libro: BibleBookEntity
) {


    val domainModelMisa: MissaeLectionum
        get() {
            val theModel = MissaeLectionum("", "BibleReadingWithBook")
            /*theModel.book = libro.domainModel
            theModel.verseChapter = lectura.capitulo.toString()
            theModel.verseFrom = lectura.desde.toString()
            theModel.verseTo = lectura.hasta.toString()
            theModel.setCita(lectura.cita)
            theModel.text = lectura.texto
            theModel.book = libro.domainModel*/
            return theModel
        }
    val domainModelOficio: LHOfficiumLectioPrior
        get() {
            val theModel = LHOfficiumLectioPrior("", LHResponsorium())
            theModel.book = libro.domainModel
            theModel.verseChapter = lectura.capitulo.toString()
            theModel.verseFrom = lectura.desde.toString()
            theModel.verseTo = lectura.hasta.toString()
            theModel.setCita(lectura.cita)
            theModel.text = lectura.texto
            return theModel
        }
}

fun BibleReadingWithBook.asExternalModelBrevis() = LHLectioBrevis(
    lectura.cita,
    lectura.texto
)

fun BibleReadingWithBook.asExternalModel() = LectioBiblica(
    libro.asExternalModel(),
    lectura.cita,
    lectura.texto
)

fun BibleReadingWithBook.asExternalModelBook(): BibleBook {
    return libro.asExternalModel()
}

