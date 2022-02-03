package org.deiverbum.app.model;

import static org.deiverbum.app.utils.Constants.BR;
import static org.deiverbum.app.utils.Constants.PRE_ANT;
import static org.deiverbum.app.utils.Utils.LS2;

import android.text.SpannableStringBuilder;
import android.text.Spanned;

import org.deiverbum.app.utils.Utils;

public class Laudes extends BreviarioHora{
    private Invitatorio invitatorio;
    private LecturaBreve lecturaBreve;
    private Benedictus benedictus;
    private Preces preces;
    private PadreNuestro padreNuestro;

    public Laudes(){
        this.padreNuestro=new PadreNuestro();
    }

    public String getTituloHora() {
        return "LAUDES";
    }

    public Invitatorio getInvitatorio() {
        return invitatorio;
    }

    public void setInvitatorio(Invitatorio invitatorio) {
        this.invitatorio = invitatorio;
    }

    public LecturaBreve getLecturaBreve() {
        return lecturaBreve;
    }

    public void setLecturaBreve(LecturaBreve lecturaBreve) {
        this.lecturaBreve = lecturaBreve;
    }

    public Benedictus getBenedictus() {
        return benedictus;
    }

    public void setBenedictus(Benedictus benedictus) {
        this.benedictus = benedictus;
    }

    public Preces getPreces() {
        return preces;
    }

    public void setPreces(Preces preces) {
        this.preces = preces;
    }

    
    public SpannableStringBuilder getForView(boolean hasInvitatorio) {
        SpannableStringBuilder sb = new SpannableStringBuilder();
        //PadreNuestro padreNuestro=new PadreNuestro();
        try {
            lecturaBreve.normalizeByTime(metaLiturgia.calendarTime);
            sb.append(metaLiturgia.getAll());
            sb.append(LS2);

            if (metaLiturgia.getHasSaint()) {
                sb.append(santo.getVida());
                sb.append(LS2);
            }

            sb.append(Utils.toH3Red(getTituloHora().toUpperCase()));
            sb.append(Utils.fromHtmlToSmallRed(getMetaInfo()));
            sb.append(LS2);

            sb.append(getSaludoOficio());
            sb.append(LS2);
            sb.append(invitatorio.getAll(hasInvitatorio));
            sb.append(LS2);

            sb.append(himno.getAll());
            sb.append(LS2);

            sb.append(salmodia.getAll());
            sb.append(Utils.LS2);

            sb.append(lecturaBreve.getAll());
            sb.append(Utils.LS2);

            sb.append(benedictus.getAll());
            sb.append(LS2);

            sb.append(preces.getAll());
            sb.append(LS2);

            sb.append(padreNuestro.getAll());
            sb.append(LS2);

            sb.append(oracion.getAll());
            sb.append(LS2);

            sb.append(getConclusionHorasMayores());
        }catch (Exception e){
            sb.append(e.getMessage());

        }
        return sb;
    }
    
public StringBuilder getForRead(boolean hasInvitatorio){
    StringBuilder sb = new StringBuilder();
    try {
    sb.append(metaLiturgia.getAllForRead());
    if(metaLiturgia.getHasSaint()) {
        sb.append(santo.getVida());
    }
    sb.append(getTituloHoraForRead());
    sb.append(getSaludoOficioForRead());
    sb.append(invitatorio.getAllForRead(hasInvitatorio));

    sb.append(himno.getAllForRead());
    sb.append(salmodia.getAllForRead());

    sb.append(lecturaBreve.getAllForRead());

    sb.append(benedictus.getAllForRead());

    sb.append(preces.getAllForRead());

    PadreNuestro padreNuestro=new PadreNuestro();
    sb.append(padreNuestro.getAllForRead());
    sb.append(oracion.getAllForRead());
    sb.append(getConclusionHorasMayoresForRead());
    }catch (Exception e){
        sb.append(e.getMessage());
    }
    return sb;
}



    private String getTituloHoraForRead() {
        return "LAUDES.";
    }

}
