package org.deiverbum.app.data.entity;

import static org.deiverbum.app.utils.Constants.LH_PSALM;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.deiverbum.app.model.LHPsalm;

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2023.1
 */

@Entity(tableName = LH_PSALM)

public class PsalmEntity {
    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "psalmID")
    public Integer salmoId = 0;

    @NonNull
    @ColumnInfo(name = "psalm")
    public String salmo = "";

    @NonNull
    @ColumnInfo(name = "readingID")
    public Integer pericopaId = 0;

    @ColumnInfo(name = "quote")
    public String salmoRef;

    @NonNull
    public String getSalmo() {
        return salmo;
    }

    public void setSalmo(@NonNull String salmo) {
        this.salmo = salmo;
    }

    public String getSalmoRef() {
        return (salmoRef != null) ? salmoRef : "";
    }

    @SuppressWarnings("unused")
    public void setSalmoRef(String salmoRef) {
        this.salmoRef = salmoRef;
    }

    @SuppressWarnings("unused")
    @NonNull
    public Integer getSalmoId() {
        return salmoId;
    }

    @SuppressWarnings("unused")
    public void setSalmoId(@NonNull Integer salmoId) {
        this.salmoId = salmoId;
    }

    @NonNull
    @SuppressWarnings("unused")
    public Integer getPericopaId() {
        return pericopaId;
    }

    @SuppressWarnings("unused")
    public void setPericopaId(@NonNull Integer pericopaId) {
        this.pericopaId = pericopaId;
    }

    public LHPsalm getDomainModel() {
        LHPsalm dm = new LHPsalm();
        dm.setSalmo(getSalmo());
        dm.setRef(getSalmoRef());
        return dm;
    }
}
