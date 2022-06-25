package org.deiverbum.app.data.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2022.2
 *
 * CREATE TABLE `padre` (
 * 	`padreId` INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
 * 	`padre` TEXT NOT NULL,
 * 	`liturgyName` TEXT NOT NULL,
 * 	`lugarFK` INTEGER NOT NULL DEFAULT 0,
 * 	`tipoFK` INTEGER NOT NULL DEFAULT 0,
 * 	`tituloFK` INTEGER NOT NULL DEFAULT 0,
 * 	`misionFK` INTEGER NOT NULL DEFAULT 0,
 * 	`sexoFK` INTEGER NOT NULL DEFAULT 0,
 * 	`grupoFK` INTEGER NOT NULL DEFAULT 0,
 *     UNIQUE  (`padre`,`lugarFK`,`tipoFK`,`tituloFK`,`misionFK`,`sexoFK`,`grupoFK`)
 *  );
 */

@Entity(tableName = "padre",
        indices={@Index(value={"padre","lugarFK","tipoFK","tituloFK","misionFK","sexoFK","grupoFK"},unique = true)}
)
public class PadreEntity {
    @NonNull
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "padreId")
    public Integer padreId=0;

    @NonNull
    @ColumnInfo(name = "padre")
    public String padre="";

    @NonNull
    @ColumnInfo(name = "liturgyName")
    public String liturgyName="";

    @NonNull
    @ColumnInfo(name = "lugarFK", defaultValue = "0")
    public Integer lugarFK=0;

    @NonNull
    @ColumnInfo(name = "tipoFK", defaultValue = "0")
    public Integer tipoFK=0;

    @NonNull
    @ColumnInfo(name = "tituloFK", defaultValue = "0")
    public Integer tituloFK=0;

    @NonNull
    @ColumnInfo(name = "misionFK", defaultValue = "0")
    public Integer misionFK=0;

    @NonNull
    @ColumnInfo(name = "sexoFK", defaultValue = "0")
    public Integer sexoFK=0;

    @NonNull
    @ColumnInfo(name = "grupoFK", defaultValue = "0")
    public Integer grupoFK=0;


    public String getPadre() {
        return padre;
    }

    public String getLiturgyName() {
        return ( liturgyName.equals("") ) ?  getPadre() : liturgyName;
    }

}
