package org.deiverbum.app.data.entity;

import androidx.room.Embedded;
import androidx.room.Relation;

import org.deiverbum.app.model.BiblicaMisa;
import org.deiverbum.app.model.Liturgia;

import java.util.List;

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2022.2
 */
public class MisaWithLecturasBis {
    @Embedded
    public LiturgyGroupEntity misaLectura;

    @Relation(
            parentColumn = "groupID",
            entityColumn = "groupFK",
            entity = MassReadingEntity.class
    )
    public List<MassReadingWithAll> lectura;

    @Relation(
            parentColumn = "liturgyFK",
            entityColumn = "liturgyID",
            entity = LiturgiaEntity.class
    )
    public LiturgiaWithTiempo liturgia;

    public BiblicaMisa getDomainModel() {
        BiblicaMisa theModel = null;//lectura.getDomainModelMisa();
        //theModel.setTema(misaLectura.getTema());
        //theModel.setOrden(misaLectura.getOrden());
        return theModel;
    }

    public Liturgia getLiturgia() {
        return liturgia.getDomainModel();
    }

}
