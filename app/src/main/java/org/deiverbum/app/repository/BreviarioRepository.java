package org.deiverbum.app.repository;

import static org.deiverbum.app.utils.Constants.ERR_REPORT;
import static org.deiverbum.app.utils.Constants.NOTFOUND_OR_NOTCONNECTION;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;

import org.deiverbum.app.data.db.dao.TodayDao;
import org.deiverbum.app.data.entity.TodayCompletas;
import org.deiverbum.app.data.entity.TodayLaudes;
import org.deiverbum.app.data.entity.TodayMixto;
import org.deiverbum.app.data.entity.TodayNona;
import org.deiverbum.app.data.entity.TodayOficio;
import org.deiverbum.app.data.entity.TodaySexta;
import org.deiverbum.app.data.entity.TodayTercia;
import org.deiverbum.app.data.entity.TodayVisperas;
import org.deiverbum.app.data.source.local.FileDataSource;
import org.deiverbum.app.data.source.remote.firebase.FirebaseDataSource;
import org.deiverbum.app.data.source.remote.network.ApiService;
import org.deiverbum.app.data.wrappers.CustomException;
import org.deiverbum.app.data.wrappers.DataWrapper;
import org.deiverbum.app.model.BreviaryHour;
import org.deiverbum.app.model.Completas;
import org.deiverbum.app.model.Liturgy;
import org.deiverbum.app.model.LiturgyHelper;
import org.deiverbum.app.model.MetaLiturgia;
import org.deiverbum.app.utils.Utils;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;

import javax.inject.Inject;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.observers.DisposableSingleObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;


/**
 * Repositorio del módulo Breviary.
 * Este repositorio busca los datos en el siguiente orden:
 * <ul>
 *     <li>En la base de datos local</li>
 *     <li>En Firebase</li>
 *     <li>En el servidor remoto</li>
 * </ul>
 *
 * @author A. Cedano
 * @version 2.0
 * @since 2022.01.01
 */

public class BreviarioRepository {
    private final ApiService apiService;
    private final FirebaseDataSource firebaseDataSource;
    private final MediatorLiveData<DataWrapper<Liturgy, CustomException>> liveData = new MediatorLiveData<>();
    private final TodayDao mTodayDao;
    private final FileDataSource fileDataSource;

    @Inject
    public BreviarioRepository(
            FirebaseDataSource firebaseDataSource,
            ApiService apiService,
            TodayDao todayDao,
            FileDataSource fileDataSource
    ) {
        this.firebaseDataSource = firebaseDataSource;
        this.mTodayDao = todayDao;
        this.apiService = apiService;
        this.fileDataSource = fileDataSource;
    }

    /**
     * Este método inicia la llamada al DataSource.
     * Primero buscará en la base de datos
     * si no encuentra, buscará en Firestore mediante {@link #getFromFirebase(String, int)}
     * y si no encuentra, buscará en la Api mediante {@link #getFromApi(String, int)}
     * La llamada a la Api se hará desde el onError
     *
     * @param theDate La fecha
     * @param hourId  Un valor numérico que identifica la hora
     * @return En MediatorLiveData con los datos obtenidos de cualquiera de las fuentes
     */
    public MediatorLiveData<DataWrapper<Liturgy, CustomException>> getFromLocal(String theDate, int hourId) {
        switch (hourId) {
            case 0:
                TodayMixto mixtoEntity = mTodayDao.getMixtoOfToday(Integer.valueOf(theDate));
                if (mixtoEntity != null) {
                    Liturgy theModel = mixtoEntity.getDomainModel();
                    liveData.postValue(new DataWrapper<>(theModel));
                } else {
                    getFromFirebase(theDate, hourId);
                }
                break;

            case 1:
                TodayOficio oficioEntity = mTodayDao.getOficioOfToday(Integer.valueOf(theDate));

                if (oficioEntity != null) {
                    Liturgy theModel = oficioEntity.getDomainModel();
                    liveData.postValue(new DataWrapper<>(theModel));
                } else {
                    getFromFirebase(theDate, hourId);
                }
                break;

            case 7:
                //TodayOficio theEntity = mTodayDao.getOficioOfToday(Integer.valueOf(theDate));
                TodayCompletas completasEntity = mTodayDao.getCompletasOfToday(Integer.valueOf(theDate));

                if (completasEntity != null) {
                    Liturgy l = completasEntity.getDomainModel();
                    MetaLiturgia meta = new MetaLiturgia();
                    InputStream raw = Objects.requireNonNull(getClass().getClassLoader()).getResourceAsStream("res/raw/completas.json");
                    Gson gson = new Gson();
                    Completas hora = gson.fromJson(new InputStreamReader(raw), Completas.class);
                    //String s = gson.fromJson(new InputStreamReader(raw), String.class);
                    String json = gson.toJson(hora);

                    Log.d("AAA", json);
                    hora.setMetaLiturgia(meta);
                    hora.setHoy(l.getHoy());
                    BreviaryHour bh = new BreviaryHour();
                    bh.setCompletas(hora);
                    l.setBreviaryHour(bh);
                    getBook(l);
                    //liveData.postValue(getBook(l));
                } else {
                    getFromFirebase(theDate, hourId);
                }
                break;

            case 2:
                TodayLaudes laudesEntity = mTodayDao.getLaudesOfToday(Integer.valueOf(theDate));
                if (laudesEntity != null) {
                    Liturgy theModel = laudesEntity.getDomainModel();
                    liveData.postValue(new DataWrapper<>(theModel));
                } else {
                    getFromFirebase(theDate, hourId);
                }
                break;

            case 3:
                TodayTercia todayTercia = mTodayDao.getTerciaOfToday(Integer.valueOf(theDate));
                if (todayTercia != null) {
                    Liturgy theModel = todayTercia.getDomainModel();
                    liveData.postValue(new DataWrapper<>(theModel));
                } else {
                    getFromFirebase(theDate, hourId);
                }
                break;

            case 4:
                TodaySexta todaySexta = mTodayDao.getSextaOfToday(Integer.valueOf(theDate));
                if (todaySexta != null) {
                    Liturgy theModel = todaySexta.getDomainModel();
                    liveData.postValue(new DataWrapper<>(theModel));
                } else {
                    getFromFirebase(theDate, hourId);
                }
                break;

            case 5:
                TodayNona todayNona = mTodayDao.getNonaOfToday(Integer.valueOf(theDate));
                if (todayNona != null) {
                    Liturgy theModel = todayNona.getDomainModel();
                    liveData.postValue(new DataWrapper<>(theModel));
                } else {
                    getFromFirebase(theDate, hourId);
                }
                break;

            case 6:
                TodayVisperas todayVisperas = mTodayDao.getVisperasOfToday(Integer.valueOf(theDate));
                if (todayVisperas != null) {
                    Liturgy theModel = todayVisperas.getDomainModel();
                    liveData.postValue(new DataWrapper<>(theModel));
                } else {
                    getFromFirebase(theDate, hourId);
                }
                break;
            default:
                break;
        }
        return liveData;
    }

    public void getBook(Liturgy l) {
        MutableLiveData<DataWrapper<Liturgy, CustomException>> finalData =
                new MediatorLiveData<>();
        fileDataSource.getCompletas(l)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSingleObserver<DataWrapper<Liturgy,
                        CustomException>>() {
                    @Override
                    public void onSuccess(@NonNull DataWrapper<Liturgy,
                            CustomException> data) {
                        liveData.postValue(data);
                        dispose();
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        liveData.postValue(new DataWrapper<>(new CustomException(String.format("Error:\n%s%s", e.getMessage(), ERR_REPORT))));
                        dispose();
                    }
                });

        //return finalData;
    }


    /**
     * Este método buscará en Firestore mediante {@link FirebaseDataSource#getBreviary(String, int)}
     * y si no encuentra, buscará en la Api llamando a {@link #getFromApi(String, int)}
     * La llamada a la Api se hará desde el onError
     *
     * @param theDate La fecha
     */

    public void getFromFirebase(String theDate, int hourId) {
        firebaseDataSource.getBreviary(theDate, hourId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSingleObserver<DataWrapper<Liturgy, CustomException>>() {

                    @Override
                    public void onSuccess(@NonNull DataWrapper<Liturgy, CustomException> data) {
                        liveData.postValue(data);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        getFromApi(theDate, hourId);
                    }
                });
    }

    /**
     * Este método buscará los datos en el servidor remoto, si no los encuentra en Firebase,
     * mediante una llamada a {@link ApiService#getBreviario(String, String)}
     *
     * @param theDate La fecha
     * @param hourId  Un identificador numérico para la hora
     */

    public void getFromApi(String theDate, int hourId) {
        String endPoint = LiturgyHelper.myMap.get(hourId);
        apiService.getBreviary(endPoint, Utils.cleanDate(theDate))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSingleObserver<Liturgy>() {
                    @Override
                    public void onStart() {
                    }

                    @Override
                    public void onSuccess(@NonNull Liturgy r) {
                        liveData.postValue(new DataWrapper<>(r));
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        liveData.setValue(new DataWrapper<>(new CustomException(NOTFOUND_OR_NOTCONNECTION)));
                    }
                });
    }

}

