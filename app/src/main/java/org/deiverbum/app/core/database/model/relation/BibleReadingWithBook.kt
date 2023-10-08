package org.deiverbum.app.core.database.model.relation

import androidx.room.Embedded
import androidx.room.Relation
import org.deiverbum.app.core.database.model.entity.BibleBookEntity
import org.deiverbum.app.core.database.model.entity.BibleReadingEntity
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
    val domainModel: LectioBiblica
        get() {
            val theModel = LectioBiblica()
            theModel.book = libro.domainModel
            theModel.verseChapter = lectura.capitulo.toString()
            theModel.verseFrom = lectura.desde.toString()
            theModel.verseTo = lectura.hasta.toString()
            theModel.setCita(lectura.cita)
            theModel.text = lectura.texto
            return theModel
        }
    val domainModelMisa: MissaeLectionum
        get() {
            val theModel = MissaeLectionum()
            theModel.book = libro.domainModel
            theModel.verseChapter = lectura.capitulo.toString()
            theModel.verseFrom = lectura.desde.toString()
            theModel.verseTo = lectura.hasta.toString()
            theModel.setCita(lectura.cita)
            theModel.text = lectura.texto
            theModel.book = libro.domainModel
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