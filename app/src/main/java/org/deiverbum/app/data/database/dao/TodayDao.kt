package org.deiverbum.app.data.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import org.deiverbum.app.data.db.dao.TodayDao
import org.deiverbum.app.data.entity.HomilyEntity
import org.deiverbum.app.data.entity.LiturgyEntity
import org.deiverbum.app.data.entity.relation.*

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

    @Transaction
    @Query(TodayDao.todayByDate)
    fun getCommentsByDate(theDate: Int?): TodayComentarios?

    @Transaction
    @Query(TodayDao.todayByDate)
    fun getMixtoByDate(theDate: Int?): TodayMixto?

    @Transaction
    @Query(TodayDao.todayByDate)
    fun getOficioByDate(theDate: Int?): TodayOficio?

    @Transaction
    @Query(TodayDao.todayByDate)
    fun getLaudesByDate(theDate: Int?): TodayLaudes?

    @Transaction
    @Query(TodayDao.todayByDate)
    fun getTerciaByDate(theDate: Int?): TodayTercia?

    @Transaction
    @Query(TodayDao.todayByDate)
    fun getSextaByDate(theDate: Int?): TodaySexta?

    @Transaction
    @Query(TodayDao.todayByDate)
    fun getNonaByDate(theDate: Int?): TodayNona?

    @Transaction
    @Query(TodayDao.todayByDate)
    fun getVisperasByDate(theDate: Int?): TodayVisperas?

    @Transaction
    @Query(TodayDao.todayByDate)
    fun getCompletasByDate(theDate: Int?): TodayCompletas?

    @Transaction
    @Query(TodayDao.todayByDate)
    fun getHomilyByDate(theDate: Int?): TodayHomilias?

    @Transaction
    @Query(TodayDao.todayByDate)
    fun getMassReadingByDate(theDate: Int?): TodayMisaLecturas?

}