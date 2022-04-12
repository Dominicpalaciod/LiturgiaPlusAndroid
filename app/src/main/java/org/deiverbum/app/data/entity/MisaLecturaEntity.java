package org.deiverbum.app.data.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2022.2
 */

@Entity(tableName = "misa_lectura",
        primaryKeys = {"liturgiaId","pericopaFK","orden"},
        foreignKeys =
                {
                        @ForeignKey(
                                entity = BibliaLecturaEntity.class,
                                parentColumns = "pericopaId",
                                childColumns = "pericopaFK",
                                onDelete = ForeignKey.SET_DEFAULT,
                                onUpdate = ForeignKey.CASCADE)}/*,
         indices = {@Index(value = {"liturgiaId","pericopaFK","orden"},unique
          = true)}*/
)
public class MisaLecturaEntity {
    @NonNull
    @ColumnInfo(name = "liturgiaId")
    public Integer homiliaId;

    @NonNull
    @ColumnInfo(name = "pericopaFK")
    public Integer pericopaFK;


    @NonNull
    @ColumnInfo(name = "orden")
    public Integer orden;

    @NonNull
    @ColumnInfo(name = "tema")
    public String tema;


    public Integer getOrden() {
        return orden !=null ? orden : 0;
    }

    public String getTema() {
        return tema!=null ? tema : "";
    }
}

