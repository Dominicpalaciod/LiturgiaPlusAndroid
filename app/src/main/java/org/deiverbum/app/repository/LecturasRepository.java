package org.deiverbum.app.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;

import org.deiverbum.app.data.source.remote.firebase.FirebaseDataSource;
import org.deiverbum.app.data.source.remote.network.ApiService;
import org.deiverbum.app.data.wrappers.CustomException;
import org.deiverbum.app.data.wrappers.DataWrapper;
import org.deiverbum.app.model.Lecturas;
import org.deiverbum.app.utils.Utils;

import javax.inject.Inject;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.observers.DisposableSingleObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;

/**
 * <p>Repositorio de datos para el módulo Lecturas.</p>
 * <p>Orden de búsqueda: </p>
 * <ul>
 *     <li>Firebase</li>
 *     <li>Api</li>
 * </ul>
 * @author A. Cedano
 * @version 1.0
 * @since 2022.1
 */
public class LecturasRepository {
    ApiService apiService;
    private final FirebaseDataSource firebaseDataSource;
    private final MutableLiveData<DataWrapper<Lecturas, CustomException>> mData = new MediatorLiveData<>();

    @Inject
    public LecturasRepository(ApiService apiService, FirebaseDataSource firebaseDataSource) {
        this.apiService = apiService;
        this.firebaseDataSource = firebaseDataSource;
    }

    /**
     * Este método inicia la llamada al DataSource.
     * Primero buscará en Firestore mediante
     * {@link FirebaseDataSource#getLecturas(String)}
     * y si no encuentra, buscará en la Api mediante
     * {@link ApiService#getLecturas(String)}
     * La llamada a la Api se hará desde el onError
     * @param dateString La fecha
     * @return En MediatorLiveData con los datos obtenidos de cualquiera de las fuentes
     */
    public MutableLiveData<DataWrapper<Lecturas, CustomException>> getLecturas(String dateString) {
        firebaseDataSource.getLecturas(dateString)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSingleObserver<DataWrapper<Lecturas,
                        CustomException>>() {

                    @Override
                    public void onSuccess(@NonNull DataWrapper<Lecturas,
                            CustomException> data) {
                        mData.postValue(data);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        lecturasFromApi(dateString);
                    }
                });
        return mData;
    }

    /**
     * Este método buscará los datos en el servidor remoto, si no los encuentra en Firebase.
     * @param dateString La fecha
     */

    public void lecturasFromApi(String dateString) {
        apiService.getLecturas(Utils.cleanDate(dateString))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSingleObserver<Lecturas>() {
                    @Override public void onStart() {
                    }
                    @Override
                    public void onSuccess(@NonNull Lecturas r) {
                        mData.postValue(new DataWrapper<>(r));
                    }
                    @Override
                    public void onError(@NonNull Throwable e) {
                        mData.setValue(new DataWrapper<>(new CustomException(e.getMessage())));
                    }
                });
    }

}
