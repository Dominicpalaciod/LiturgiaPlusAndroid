package org.deiverbum.app.core.database.dao

import androidx.room.Dao
import org.deiverbum.app.core.model.data.Liturgy
import org.deiverbum.app.core.model.data.LiturgyTime

@Dao
abstract class LiturgiaDao : BaseDao<Liturgy>

@Dao
abstract class LiturgiaTempusDao : BaseDao<LiturgyTime>