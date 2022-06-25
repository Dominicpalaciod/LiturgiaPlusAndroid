package org.deiverbum.app.data.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import org.deiverbum.app.model.ResponsorioLargo;
import org.deiverbum.app.utils.Utils;

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2022.2
 */

@Entity(tableName = "lh_responsorio",
        indices={
                @Index(value={"texto","fuente", "tipo"},unique = true)}
)
public class LHResponsorioLargoEntity {
    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "responsorioId")
    public Integer responsorioId=0;

    @NonNull
    public String getTexto() {
        return texto;
    }

    @NonNull
    public String getFuente() {
        return fuente;
    }

    @NonNull
    public Integer getTipo() {
        return tipo;
    }

    @NonNull
    @ColumnInfo(name = "texto")
    public String texto="";

    @NonNull
    @ColumnInfo(name = "fuente")
    public String fuente="";

    @NonNull
    @ColumnInfo(name = "tipo")
    public Integer tipo=0;

    public ResponsorioLargo getDomainModel(Integer timeId){
        ResponsorioLargo theModel=new ResponsorioLargo();
        theModel.setTexto(Utils.replaceByTime(getTexto(),timeId));
        theModel.setForma(getTipo());
        theModel.setRef(getFuente());
        return theModel;
    }

}
