package org.deiverbum.app.model;

import static org.deiverbum.app.utils.Utils.LS2;

import android.text.SpannableStringBuilder;

import androidx.room.ColumnInfo;
import androidx.room.Entity;

import com.google.firebase.firestore.IgnoreExtraProperties;

import org.deiverbum.app.utils.Utils;

/**
 * <p>
 *     Esta clase recoge información valiosa sobre el día litúrgico.
 * </p>
 * @author A. Cedano
 * @version 1.0
 * @since 2022.1
 */
@IgnoreExtraProperties
public class Today {
    private Integer hoy;
    private Integer feriaFK;
    private Integer mLecturasFK;
    private Integer previoId;
    private Integer tiempoId;
    private Integer version;
//    private Integer olSalmos;

    //private HashMap<String,String> lh;

    public Today() {
    }

    public Integer getHoy() {
        return hoy;
    }

    public void setHoy(Integer hoy) {
        this.hoy = hoy;
    }

    public Integer getFeriaFK() {
        return feriaFK;
    }

    public void setFeriaFK(Integer feriaFK) {
        this.feriaFK = feriaFK;
    }

    public Integer getMLecturasFK() {
        return mLecturasFK;
    }

    public void setMLecturasFK(Integer mLecturasFK) {
        this.mLecturasFK = mLecturasFK;
    }

    public Integer getPrevioId() {
        return previoId;
    }

    public void setPrevioId(Integer previo_id) {
        this.previoId = previo_id;
    }

    public Integer getTiempoId() {
        return tiempoId;
    }

    public void setTiempoId(Integer tiempo_id) {
        this.tiempoId = tiempo_id;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

 /*
    public Integer getOlSalmos() {
        return olSalmos;
    }

    public void setOlSalmos(Integer olSalmos) {
        this.olSalmos = olSalmos;
    }
*/
    public SpannableStringBuilder getAllForView(){
        SpannableStringBuilder sb=new SpannableStringBuilder();
        sb.append("getTitulo()");
        sb.append(LS2);
        sb.append(hoy.toString());
        sb.append(LS2);
        sb.append(feriaFK.toString());
        return sb;
    }
}




