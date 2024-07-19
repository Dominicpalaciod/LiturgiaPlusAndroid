/*
 * Copyright 2022 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.deiverbum.app.core.database.model.nia

import androidx.room.Embedded
import androidx.room.Relation
import org.deiverbum.app.core.database.model.entity.LHHymnJoinEntity
import org.deiverbum.app.core.database.model.entity.LHPrayerEntity
import org.deiverbum.app.core.database.model.entity.LHPsalmodyJoinEntity
import org.deiverbum.app.core.database.model.entity.LHReadingShortJoinEntity
import org.deiverbum.app.core.database.model.entity.LiturgyEntity
import org.deiverbum.app.core.database.model.entity.UniversalisEntity
import org.deiverbum.app.core.database.model.entity.asExternalModel
import org.deiverbum.app.core.database.model.relation.LHAntiphonAssoc
import org.deiverbum.app.core.database.model.relation.LHHymnAssoc
import org.deiverbum.app.core.database.model.relation.LHPrayerAll
import org.deiverbum.app.core.database.model.relation.LHPsalmsAssoc
import org.deiverbum.app.core.database.model.relation.LHReadingShortAll
import org.deiverbum.app.core.database.model.relation.LiturgyTimeAssoc
import org.deiverbum.app.core.database.model.relation.asExternalModel
import org.deiverbum.app.core.model.data.LHIntermedia
import org.deiverbum.app.core.model.data.LHPsalmody
import org.deiverbum.app.core.model.data.Liturgy
import org.deiverbum.app.core.model.data.Universalis
import org.deiverbum.app.core.model.data.UniversalisResource

/**
 * External data layer representation of a fully populated NiA news resource
 */
data class PopulatedUniversalisResource(
    /*
    @Embedded
    val entity: UniversalisEntity,
    @Relation(entity = LiturgyEntity::class, parentColumn = "liturgyFK", entityColumn = "liturgyID")
    var liturgia: LiturgyTimeAssoc,
    @Relation(entity = LHHymnJoinEntity::class, parentColumn = "sHymnFK", entityColumn = "groupID")
    var hymnus: LHHymnAssoc,
    /*@Relation(
        parentColumn = "oHymnFK",
        entityColumn = "hymnID",
        associateBy = Junction(
            value = UniversalisHimnusCrossRef::class,
            parentColumn = "hymnID",
            entityColumn = "hymnFK",
        ),
    )*/
//    val topics: UniversalisEntity,
)*/
    @Embedded
    var universalis: UniversalisEntity,

    @Relation(entity = LiturgyEntity::class, parentColumn = "liturgyFK", entityColumn = "liturgyID")
    var liturgia: LiturgyTimeAssoc,

    @Relation(entity = LHHymnJoinEntity::class, parentColumn = "sHymnFK", entityColumn = "groupID")
    var hymnus: LHHymnAssoc,

    @Relation(
        entity = LHPsalmodyJoinEntity::class,
        parentColumn = "sAntiphonFK",
        entityColumn = "groupID"
    )
    var antiphonae: LHAntiphonAssoc,

    @Relation(
        entity = LHPsalmodyJoinEntity::class,
        parentColumn = "sPsalmFK",
        entityColumn = "groupID"
    )
    var psalmus: LHPsalmsAssoc,

    @Relation(
        entity = LHReadingShortJoinEntity::class,
        parentColumn = "sBiblicalFK",
        entityColumn = "groupID"
    )
    var lectioBrevis: LHReadingShortAll,

    @Relation(entity = LHPrayerEntity::class, parentColumn = "sPrayerFK", entityColumn = "groupID")
    var oratio: LHPrayerAll,
)

fun PopulatedUniversalisResource.asExternalModel() = UniversalisResource(
    universalis.todayDate,
    "",
    listOf(
        Universalis(
            universalis.todayDate,
            //universalis.timeFK,
            Liturgy(
                liturgia.parent.semana,
                liturgia.parent.dia,
                liturgia.parent.nombre,
                liturgia.entity.asExternalModel(),
                LHIntermedia(
                    hymnus.entity.asExternalModel(),
                    LHPsalmody(
                        psalmus.asExternalModel(),
                        antiphonae.asExternalModel(),
                        psalmus.join.theType
                    ),
                    lectioBrevis.asExternalModel(),
                    oratio.asExternalModel(),
                    4,
                    "intermedia"
                )
            )
        )
    )
)

fun PopulatedUniversalisResource.asExternalModelk() = Universalis(
    universalis.todayDate,
    //universalis.timeFK,
    Liturgy(
        liturgia.parent.semana,
        liturgia.parent.dia,
        liturgia.parent.nombre,
        liturgia.entity.asExternalModel(),
        LHIntermedia(
            hymnus.entity.asExternalModel(),
            LHPsalmody(
                psalmus.asExternalModel(),
                antiphonae.asExternalModel(),
                psalmus.join.theType
            ),
            lectioBrevis.asExternalModel(),
            oratio.asExternalModel(),
            4,
            "intermedia"
        )
    )
)

/*
fun PopulatedUniversalisResourcee.asExternalModel() = UniversalisResource(
    todayDate = entity.todayDate,
    //content = liturgia.parent.nombre,
    content = hymnus.entity.asExternalModel().all.toString(),

    /*title = entity.title,
    content = entity.content,
    url = entity.url,
    headerImageUrl = entity.headerImageUrl,
    publishDate = entity.publishDate,
    type = entity.type,*/
    topics = emptyList(),
)
*/
/*fun PopulatedUniversalisResource.asFtsEntity() = NewsResourceFtsEntity(
    newsResourceId = entity.id,
    title = entity.title,
    content = entity.content,
)*/
