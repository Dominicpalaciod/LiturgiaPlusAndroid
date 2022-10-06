package org.deiverbum.app.repository;

import static org.deiverbum.app.utils.Constants.CONTENT_TO_SYNC;
import static org.deiverbum.app.utils.Constants.NOTFOUND_OR_NOTCONNECTION;

import androidx.annotation.NonNull;
import androidx.lifecycle.MediatorLiveData;

import org.deiverbum.app.data.db.dao.TodayDao;
import org.deiverbum.app.data.entity.relation.TodayComentarios;
import org.deiverbum.app.data.source.remote.firebase.FirebaseDataSource;
import org.deiverbum.app.data.source.remote.network.ApiService;
import org.deiverbum.app.data.wrappers.CustomException;
import org.deiverbum.app.data.wrappers.DataWrapper;
import org.deiverbum.app.model.BibleCommentList;
import org.deiverbum.app.utils.Utils;

import javax.inject.Inject;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.observers.DisposableSingleObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;

/**
 * <p>Repositorio de datos para el módulo Comentarios.</p>
 * <p>Orden de búsqueda: </p>
 * <ul>
 *     <li>Firebase</li>
 *     <li>Api</li>
 * </ul>
 * @author A. Cedano
 * @version 1.0
 * @since 2022.1
 */
public class ComentariosRepository {
    final ApiService apiService;
    private final FirebaseDataSource firebaseDataSource;
    private final TodayDao mTodayDao;
    private final MediatorLiveData<DataWrapper<BibleCommentList, CustomException>> mData = new MediatorLiveData<>();

    @Inject
    public ComentariosRepository(ApiService apiService, FirebaseDataSource firebaseDataSource, TodayDao todayDao) {
        this.apiService = apiService;
        this.firebaseDataSource = firebaseDataSource;
        this.mTodayDao = todayDao;
    }

    /**
     * Este método inicia la llamada al DataSource.
     * Primero buscará en Firestore mediante
     * {@link FirebaseDataSource#getLecturas(String)}
     * y si no encuentra, buscará en la Api mediante
     * {@link ApiService#getLecturas(String)}
     * La llamada a la Api se hará desde el onError
     * @param dateString La fecha
     */
    public void getComentarios(String dateString) {
        firebaseDataSource.getComentarios(dateString)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSingleObserver<DataWrapper<BibleCommentList,
                        CustomException>>() {

                    @Override
                    public void onSuccess(@NonNull DataWrapper<BibleCommentList,
                            CustomException> data) {
                        mData.postValue(data);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        comentariosFromApi(dateString);
                    }
                });
    }

    /**
     * Este método buscará los datos en el servidor remoto, si no los encuentra en Firebase.
     * @param dateString La fecha
     */

    public void comentariosFromApi(String dateString) {
        apiService.getComentarios(Utils.cleanDate(dateString))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSingleObserver<BibleCommentList>() {
                    @Override public void onStart() {
                    }
                    @Override
                    public void onSuccess(@NonNull BibleCommentList r) {
                        mData.postValue(new DataWrapper<>(r));
                    }
                    @Override
                    public void onError(@NonNull Throwable e) {
                        mData.setValue(new DataWrapper<>(new CustomException(CONTENT_TO_SYNC)));
                    }
                });
    }

    public MediatorLiveData<DataWrapper<BibleCommentList, CustomException>> getFromDB(String s) {
        TodayComentarios theEntity = mTodayDao.getComentarios(Integer.valueOf(s));
        if (theEntity != null) {
            BibleCommentList theModel = theEntity.getDomainModel();
            mData.postValue(new DataWrapper<>(theModel));
        } else {
            getComentarios(s);
        }
        return mData;
    }
}