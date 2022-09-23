package org.deiverbum.app.data.entity;

import androidx.room.Embedded;
import androidx.room.Relation;

import org.deiverbum.app.model.LHIntercession;

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2022.2
 */
public class LHIntercessionsDM {
    @Embedded
    public LHIntercessionsJoinEntity lhPatristica;

    @Relation(
            parentColumn = "intercessionFK",
            entityColumn = "intercessionID",
            entity = LHIntercessionsEntity.class
    )
    public LHIntercessionsEntity preces;

    public LHIntercession getDomainModel() {
        LHIntercession theModel=new LHIntercession();
        theModel.setIntro(preces.intro);
        theModel.setTexto(preces.preces);
        return theModel;
    }
}
