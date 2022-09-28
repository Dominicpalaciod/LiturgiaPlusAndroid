package org.deiverbum.app.model;

import static org.deiverbum.app.utils.Constants.TITLE_LAUDES;
import static org.deiverbum.app.utils.Constants.TITLE_OFICIO;
import static org.deiverbum.app.utils.Utils.LS2;

import android.text.SpannableStringBuilder;

import org.deiverbum.app.utils.Utils;

public class Laudes extends BreviaryHour {
    private LHInvitatory invitatorio;
    private BiblicalShort lecturaBreve;
    private LHGospelCanticle benedictus;
    private LHIntercession preces;
    private final PadreNuestro padreNuestro;

    public Laudes() {

        this.padreNuestro = new PadreNuestro();

    }

    public SpannableStringBuilder getTituloHora() {
        return Utils.toH1Red(TITLE_LAUDES);
    }
    public String getTituloHoraForRead() {
        return Utils.pointAtEnd(TITLE_LAUDES);
    }


    @SuppressWarnings("unused")
    public LHInvitatory getInvitatorio() {
        return invitatorio;
    }

    @SuppressWarnings("unused")
    public void setInvitatorio(LHInvitatory invitatorio) {
        this.invitatorio = invitatorio;
    }

    public BiblicalShort getLecturaBreve() {
        return lecturaBreve;
    }

    @SuppressWarnings("unused")
    public void setLecturaBreve(BiblicalShort lecturaBreve) {
        this.lecturaBreve = lecturaBreve;
    }

    public LHGospelCanticle getBenedictus() {
        return benedictus;
    }

    @SuppressWarnings("unused")
    public void setBenedictus(LHGospelCanticle benedictus) {
        this.benedictus = benedictus;
        //this.benedictus.setTipo(2);

    }

    public LHIntercession getPreces() {
        return preces;
    }

    public void setPreces(LHIntercession preces) {
        this.preces = preces;
    }


    public SpannableStringBuilder getForView(LiturgyTime liturgyTime, boolean hasInvitatorio) {
        SpannableStringBuilder sb = new SpannableStringBuilder();
        //PadreNuestro padreNuestro=new PadreNuestro();
        //try {
            //TODO hacer esto en la clase LHResponsoryShort, revisar Completas y
            // demás horas
            //biblicaBreve.normalizeByTime(metaLiturgia.calendarTime);

            invitatorio.normalizeByTime(liturgyTime.getTiempoId());
            salmodia.normalizeByTime(liturgyTime.getTiempoId());

            sb.append(hoy.getAllForView());
            sb.append(LS2);

            if (hoy.getHasSaint()==1) {
                sb.append(santo.getVida());
                sb.append(LS2);
            }

            sb.append(getTituloHora());
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

            sb.append(lecturaBreve.getAllWithHourCheck(2));
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
        //} catch (Exception e) {
        //    sb.append(e.getMessage());

        //}
        return sb;
    }

    public StringBuilder getForRead(boolean hasInvitatorio) {
        StringBuilder sb = new StringBuilder();
        try {
            sb.append(hoy.getAllForRead());
            if (hoy.getHasSaint()==1) {
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

            PadreNuestro padreNuestro = new PadreNuestro();
            sb.append(padreNuestro.getAllForRead());
            sb.append(oracion.getAllForRead());
            sb.append(getConclusionHorasMayoresForRead());
        } catch (Exception e) {
            sb.append(e.getMessage());
        }
        return sb;
    }



}
