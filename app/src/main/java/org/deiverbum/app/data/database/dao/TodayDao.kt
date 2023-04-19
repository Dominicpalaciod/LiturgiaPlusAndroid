package org.deiverbum.app.data.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.RewriteQueriesToDropUnusedColumns
import androidx.room.Transaction
import org.deiverbum.app.data.db.dao.TodayDao
import org.deiverbum.app.data.entity.HomilyEntity
import org.deiverbum.app.data.entity.LiturgyEntity
import org.deiverbum.app.data.entity.relation.TodayMixto
import org.deiverbum.app.domain.model.BiblicalComment

@Dao
interface TodayDao {

/*
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(note: List<PrayEntity>)
*/
    @Query("SELECT * FROM liturgy WHERE timeFK >= :from AND timeFK <= :to")
    fun getLiturgy(from: Int, to: Int): List<LiturgyEntity>

    @Query("SELECT * FROM homily WHERE homilyID>1 LIMIT 5")
    fun getHomily(): List<HomilyEntity>

    @RewriteQueriesToDropUnusedColumns
    @Query("SELECT * FROM homily WHERE homilyID>10 LIMIT 5")
    fun getBiblicalComment(): List<BiblicalComment>

    @Transaction
    @Query(TodayDao.todayByDate)
    fun getMixtoOfToday(theDate: Int?): TodayMixto?


}