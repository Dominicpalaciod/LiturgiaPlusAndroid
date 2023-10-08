package org.deiverbum.app.core.database.model.relation

import androidx.room.Embedded
import androidx.room.Relation
import org.deiverbum.app.core.database.model.entity.LHAntiphonJoinEntity
import org.deiverbum.app.core.database.model.entity.LHPsalmJoinEntity
import org.deiverbum.app.core.database.model.entity.LHPsalmodyJoinEntity
import org.deiverbum.app.core.model.data.LHAntiphon
import org.deiverbum.app.core.model.data.LHPsalm
import org.deiverbum.app.core.model.data.LHPsalmody

/**
 * Obtiene la salmodia, asociando a [LHPsalmodyJoinEntity] con [LHPsalmodyWithAll]
 * la cual trae todos los elementos del salmo.
 *
 * @author A. Cedano
 * @version 1.0
 * @since 2023.1.3
 */
@Deprecated("Será reemplazada por LHPsalmsAssoc")

data class LHPsalmodyAssoc(
    @Embedded
    var salmodia: LHPsalmodyJoinEntity,

    @Relation(parentColumn = "groupID", entityColumn = "groupFK", entity = LHPsalmJoinEntity::class)
    var salmos: List<LHPsalmodyWithAll>,

    @Relation(
        parentColumn = "groupID",
        entityColumn = "groupFK",
        entity = LHAntiphonJoinEntity::class
    )
    var ant: List<LHAntiphonWithAll>
) {
    val domainModel: LHPsalmody
        get() {

            val salmosList: MutableList<LHPsalm> = ArrayList()
            val antiphonList: MutableList<LHAntiphon> = ArrayList()
            for (a in ant) {
                val dm = LHAntiphon()
                //dm.antiphon=a.antiphon
                //dm.theOrder=a.theOrder
                antiphonList.add(dm)
            }

            for (salmo in salmos) {
                val s = LHPsalm(1, "", "", "", 1, "")
                /*val a = LHAntiphon()

                s.psalm = salmo.salmoText
                //s.setRef(salmo.ref)
                s.lhAntiphon= ant[0].antiphonEntity.domainModel// salmo.antiphonEntity.domainModel
                s.theme = salmo.theme
                s.epigraph = salmo.epigraph
                s.part = salmo.part!!
                s.theOrder = salmo.orden*/
                salmosList.add(s)
            }
            val theModel = LHPsalmody(salmosList, antiphonList)
            theModel.theType = salmodia.theType
            //theModel.setSalmos(salmosList)
            //theModel.setAntiphons(antiphonList)
            return theModel
        }
}