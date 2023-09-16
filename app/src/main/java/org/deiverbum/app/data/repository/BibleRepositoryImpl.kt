package org.deiverbum.app.data.repository

import android.content.Context
import androidx.work.*
import dagger.hilt.android.qualifiers.ApplicationContext
import org.deiverbum.app.data.factory.BibleFactory
import org.deiverbum.app.domain.model.BibleBookRequest
import org.deiverbum.app.domain.model.SyncRequest
import org.deiverbum.app.domain.model.SyncResponse
import org.deiverbum.app.domain.repository.BibleRepository
import org.deiverbum.app.model.BibleBooks
import org.deiverbum.app.util.Source
import org.deiverbum.app.workers.TodayWorker
import javax.inject.Inject

/**
 *
 * Implementación del Repositorio para el módulo `Biblia`.
 *
 * @author A. Cedano
 * @version 1.0
 * @since 2023.1
 */

class BibleRepositoryImpl @Inject constructor(
    private val syncFactory: BibleFactory,
    @ApplicationContext val context: Context

) : BibleRepository {

    /**
     * Este método obtiene un libro de la Biblia.
     *

     * @param syncRequest Es un objeto [BibleBookRequest] que contiene la información del libro que se solicita.
     * @return un objeto [BibleBooks]
     */
    override suspend fun getBibleBook(bookRequest: BibleBookRequest): BibleBooks {
            return syncFactory.create().getBibleBook(bookRequest)
        }





}