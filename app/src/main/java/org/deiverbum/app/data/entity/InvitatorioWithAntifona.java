package org.deiverbum.app.data.entity;

import androidx.room.Embedded;
import androidx.room.Relation;

/**
 * @author A. Cedano
 * @version 1.0
 * @since 2022.2
 */
public class InvitatorioWithAntifona {
    @Embedded
    public InvitatorioEntity invitatorio;

    @Relation(
            parentColumn = "grupoId",
            entityColumn = "grupoFK",
            entity = LHInvitatorioJoinEntity.class
    )
    public LHInvitatorioJoinEntity invitatorioEntity;

    @Relation(
            parentColumn = "antifonaFK",
            entityColumn = "antifonaId",
            entity = AntifonaEntity.class/*,
            associateBy = @Junction(SalmodiaSalmoCrossRef.class)*/
    )
    public AntifonaEntity antifona;

/*
    @Relation(
            parentColumn = "salmoId",
            entityColumn = "salmoFK",
            entity = SalmoEntity.class
    )
    public SalmoEntity salmo;
*/
/*
    public Integer getId() {
        return invitatorio.getTipoId();
    }
*/
    public String getAntifona() {
        return antifona.getAntifona();
    }
}
