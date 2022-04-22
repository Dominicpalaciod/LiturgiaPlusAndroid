package org.deiverbum.app.data.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.deiverbum.app.model.BibliaLibro;

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2022.2
 */

@Entity(tableName = "biblia_libro"/*,
        foreignKeys =
        {
            @ForeignKey(
                    entity = BibliaPericopaEntity.class,
                    parentColumns = "pericopaId",
                    childColumns = "pericopaFK",
                    onDelete = ForeignKey.CASCADE,
                    onUpdate = ForeignKey.CASCADE)
        },
        indices={
                @Index(value={"libroId", "capitulo", "desde", "hasta"},unique
                 = true)}
         */
)
public class BibliaLibroEntity {
    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "libroId")
    public Integer libroId;

    @NonNull
    @ColumnInfo(name = "shortName")
    public String shortName;

    @NonNull
    @ColumnInfo(name = "longName")
    public String longName;

    @NonNull
    @ColumnInfo(name = "liturgyName")
    public String liturgyName;

    @NonNull
    @ColumnInfo(name = "order")
    public Integer order;


    @NonNull
    public String getShortName() {
        return shortName;
    }

    @NonNull
    public String getLongName() {
        return longName;
    }

    @NonNull
    public String getLiturgyName() {
        return liturgyName;
    }

    public BibliaLibro getDomainModel(){
    BibliaLibro theModel=new BibliaLibro();
    theModel.setName(getLongName());
    theModel.setLiturgyName(getLiturgyName());
    theModel.setShortName(getShortName());
    //theModel.setLibro(String.valueOf(getLibroId()));

    //theModel.setResponsorio(lhResponsorioEntity.getDomainModel());
    return theModel;
}

}

