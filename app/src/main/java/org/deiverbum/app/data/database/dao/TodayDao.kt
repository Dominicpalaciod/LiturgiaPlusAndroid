package org.deiverbum.app.data.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import kotlinx.coroutines.flow.Flow
import org.deiverbum.app.data.db.dao.TodayDao
import org.deiverbum.app.data.entity.HomilyEntity
import org.deiverbum.app.data.entity.LiturgyEntity
import org.deiverbum.app.data.entity.TodayEntity
import org.deiverbum.app.data.entity.relation.*
import org.deiverbum.app.model.SyncStatus
import org.deiverbum.app.model.Today

@Dao
interface TodayDao {

/*
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(note: List<PrayEntity>)
*/
    @Insert(entity = TodayEntity::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllTodays(today: List<Today?>)

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

    @Transaction
    @Query(
        "SELECT ss.lastUpdate,ss.versionDB," +
                "(SELECT max(todayDate) FROM today) tableName " +
                "FROM sync_status ss;"
    )    fun syncInfo(): SyncStatus?

    @Transaction
    @Query(
        "SELECT ss.lastUpdate,ss.versionDB," +
                "(SELECT max(todayDate) FROM today) tableName " +
                "FROM sync_status ss;"
    )    fun syncInfoo(): SyncStatus?

}