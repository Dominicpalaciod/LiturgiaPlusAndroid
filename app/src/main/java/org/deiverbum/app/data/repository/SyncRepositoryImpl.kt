package org.deiverbum.app.data.repository

import android.content.Context
import androidx.work.BackoffPolicy
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import androidx.work.WorkRequest
import dagger.hilt.android.qualifiers.ApplicationContext
import org.deiverbum.app.data.factory.SyncFactory
import org.deiverbum.app.domain.model.SyncRequest
import org.deiverbum.app.domain.model.SyncResponse
import org.deiverbum.app.domain.repository.SyncRepository
import org.deiverbum.app.util.Constants.SYNC_TAG
import org.deiverbum.app.util.Source
import org.deiverbum.app.workers.SyncWorker
import org.deiverbum.app.workers.TodayWorker
import java.util.concurrent.TimeUnit
import javax.inject.Inject

/**
 *
 * Implementación del Repositorio para el módulo de Sincronización.
 *
 * Busca en la base de datos la última fecha disponible en el calendario y la fecha de la última sincronización.
 *
 * @author A. Cedano
 * @version 1.0
 * @since 2023.1
 */

class SyncRepositoryImpl @Inject constructor(
    private val syncFactory: SyncFactory,
    @ApplicationContext val context: Context

) : SyncRepository {

    /**
     * Este método obtiene el estado de la sincronización, según los datos
     * en el objeto [SyncRequest] que recibe en parámetro.
     *
     * El método funciona según dos contextos:
     *
     * 1. Si la propiedad [SyncRequest.hasInitialSync] es `false` abrirá una fuente remota ([Source.NETWORK])
     * para traer los datos remotos y guardarlos en la base de datos local. Pero si el valor de la propiedad
     * [SyncResponse.allToday] está vacío, abrirá una fuente remota, pero en Firebase ([Source.FIREBASE])
     * para traer los datos remotos de la presente semana.
     *
     *    En cualquiera de los casos, si el valor de la propiedad [SyncResponse.allToday] no está vacío
     *    abrirá una fuente de datos local para insertar los datos del objeto [SyncResponse]
     *    en la base de datos local. Finalmente, retornará el objeto [SyncResponse].
     *
     * 2. Si la propiedad [SyncRequest.hasInitialSync] es `true` verificará si el valor
     *  de la propiedad [SyncRequest.isWorkScheduled] es `false`, en cuyo caso
     *  llamará a [launchSyncWorker] para iniciar a [TodayWorker]. Luego, abrirá una fuente
     *  de datos local y obtendrá el estado actual de la sincronización en la base de datos.
     *
     * @param syncRequest Es un objeto [SyncRequest] que contiene la información necesaria para la sincronización que se debe realizar.
     * @return un objeto [SyncResponse]
     */
    override suspend fun getSync(syncRequest: SyncRequest): SyncResponse {
        var syncResponse: SyncResponse
        if (!syncRequest.hasInitialSync) {
            //if (!syncRequest.hasInitialSync||true) {
            syncResponse = syncFactory.create(Source.NETWORK).getSync(syncRequest)
            if (syncResponse.allToday.isEmpty()) {
                syncResponse = syncFactory.create(Source.FIREBASE).getSync(syncRequest)
            }
            if (syncResponse.allToday.isNotEmpty()) {
                syncFactory.create(Source.LOCAL).addSync(syncResponse)
            }
            return syncResponse
        }

        if (!syncRequest.isWorkScheduled) {
            launchSyncWorker()
        }
        return syncFactory.create(Source.LOCAL).getSync(syncRequest)
    }

    /**
     *
     * Lanza el objeto  [TodayWorker], encargado de monitorear/sincronizar
     * eventuales cambios en los datos remotos.
     */
    private fun launchSyncWorker() {
        val mWorkManager = WorkManager.getInstance(context)
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()
        val periodicSyncDataWork =
            PeriodicWorkRequest.Builder(SyncWorker::class.java, 15, TimeUnit.MINUTES)
                .addTag(SYNC_TAG)
                .setConstraints(constraints)
                .setBackoffCriteria(
                    BackoffPolicy.LINEAR,
                    WorkRequest.MIN_BACKOFF_MILLIS,
                    TimeUnit.MILLISECONDS
                )
                .build()
        mWorkManager.enqueueUniquePeriodicWork(
            "SYNC_TODAY", ExistingPeriodicWorkPolicy.UPDATE,
            periodicSyncDataWork
        )
    }
}