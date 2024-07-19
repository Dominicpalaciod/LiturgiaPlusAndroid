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

package org.deiverbum.app.core.database.dao.nia

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow
import org.deiverbum.app.core.database.model.entity.UniversalisEntity
import org.deiverbum.app.core.database.model.nia.NewsResourceEntity
import org.deiverbum.app.core.database.model.nia.PopulatedUniversalisResource

/**
 * DAO for [NewsResource] and [NewsResourceEntity] access
 */
@Dao
interface UniversalisDao {


    @Query(value = "SELECT * FROM universalis")
    fun getUniversalisList(): Flow<List<UniversalisEntity>>

    /**
     * Fetches news resources that match the query parameters
     */
    @Transaction
    @Query(
        value = """
            SELECT * FROM universalis
            WHERE 
                todayDate IN(:filterTopicIds)
    """,
    )
    fun getUniversalisByDate(
        /*useFilterTopicIds: Boolean = false,
        filterTopicIds: Set<String> = emptySet(),
        useFilterNewsIds: Boolean = false,
        filterNewsIds: Set<String> = emptySet(),*/
        //todayDate: Int = 0
        filterTopicIds: Set<String> = emptySet(),

        //): Flow<UniversalisEntity>
    ): Flow<List<PopulatedUniversalisResource>>

    /**
     * Fetches news resources that match the query parameters
     */
    @Transaction
    @Query(
        value = """
            SELECT * FROM universalis
            WHERE 
                todayDate =:todayDate
    """,
    )
    fun getUniversalisPopulatedByDate(
        /*useFilterTopicIds: Boolean = false,
        filterTopicIds: Set<String> = emptySet(),
        useFilterNewsIds: Boolean = false,
        filterNewsIds: Set<String> = emptySet(),*/
        //todayDate: Int = 0
        todayDate: Int = 0,

        //): Flow<UniversalisEntity>
    ): Flow<PopulatedUniversalisResource>

    @Transaction
    @Query(
        value = """
            SELECT * FROM universalis
            WHERE 
                todayDate IN(:todayDate)
    """,
    )
    fun getTopicEntity(todayDate: Int): Flow<UniversalisEntity>

    @Transaction
    @Query(
        value = """
            SELECT * FROM universalis
            WHERE 
                todayDate IN(:todayDate)
    """,
    )
    fun getUniversalisByDate(todayDate: Int): Flow<UniversalisEntity>


    /**
     * Inserts [topicEntities] into the db if they don't exist, and ignores those that do
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertOrIgnoreUniversalis(topicEntities: List<UniversalisEntity>): List<Long>

    /**
     * Inserts or updates [entities] in the db under the specified primary keys
     */
    @Upsert
    suspend fun upsertUniversalis(entities: List<UniversalisEntity>)

    /**
     * Deletes rows in the db matching the specified [ids]
     */
    @Query(
        value = """
            DELETE FROM universalis
            WHERE todayDate in (:ids)
        """,
    )
    suspend fun deleteUniversalis(ids: List<String>)
}
