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

@Entity(tableName = "lh_biblica_oficio",
        primaryKeys = {"grupoFK","pericopaFK","responsorioFK"},
        /*indices = {@Index(value = {"grupoFK","pericopaFK","responsorioFK"},
                unique = true)},*/

        foreignKeys =
        {
                @ForeignKey(
                        entity = LHBiblicaOficioJoinEntity.class,
                        parentColumns = "grupoId",
                        childColumns = "grupoFK",
                        onDelete = ForeignKey.CASCADE,
                        onUpdate = ForeignKey.CASCADE),
            @ForeignKey(
                    entity = BibliaLecturaEntity.class,
                    parentColumns = "pericopaId",
                    childColumns = "pericopaFK",
                    onDelete = ForeignKey.CASCADE,
                    onUpdate = ForeignKey.CASCADE),
            @ForeignKey(
                    entity = LHResponsorioEntity.class,
                    parentColumns = "responsorioId",
                    childColumns = "responsorioFK",
                    onDelete = ForeignKey.CASCADE,
                    onUpdate = ForeignKey.CASCADE)
        }
)
public class LHBiblicaOficioEntity {
    @NonNull
    //@PrimaryKey
    @ColumnInfo(name = "grupoFK")
    public Integer grupoFK;

    @NonNull
    @ColumnInfo(name = "pericopaFK")
    public Integer pericopaFK;

    @NonNull
    @ColumnInfo(name = "responsorioFK")
    public Integer responsorioFK;

    @NonNull
    @ColumnInfo(name = "tema")
    public String tema;

    @NonNull
    @ColumnInfo(name = "orden", defaultValue= "1")
    public Integer orden;
}

