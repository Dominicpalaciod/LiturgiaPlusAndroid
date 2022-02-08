package org.deiverbum.app.model;

import static org.deiverbum.app.utils.Constants.BR;
import static org.deiverbum.app.utils.Constants.NBSP_SALMOS;

import android.text.SpannableStringBuilder;
import android.text.Spanned;

import org.deiverbum.app.utils.Utils;

public class Salmo {
    private String orden;
    protected String antifona;
    private String ref;
    private String tema;
    private String intro;
    private String parte;
    private String salmo;

    public String getOrden() {
        return (orden != null ) ?  orden : "";
    }

    public void setOrden(String orden) {
        this.orden = orden;
    }

    public String getAntifona() {
        return (antifona != null ) ?  antifona : "";
    }

    public String getAntifonaForRead() {
        return (antifona != null ) ?  antifona : "";
    }


    public void setAntifona(String antifona) {
        this.antifona = antifona;
    }

    public SpannableStringBuilder getRef() {
        if (ref != null) {
            return new SpannableStringBuilder(Utils.toRedHtml(Utils.getFormato(ref)));//Utils.ssbRed((SpannableStringBuilder) Utils.fromHtml(ref));
        } else {
            return null;
        }
    }


    public void setRef(String ref) {
        this.ref = ref;
    }

    public String getTema() {
        return (tema != null ) ? tema : null;
    }

    public void setTema(String tema) {
        this.tema = tema;
    }

    public String getIntro() {
        return (intro != null ) ? intro:null;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getParte() {
        return (parte != null) ? parte: null;
    }

    public void setParte(String parte) {
        this.parte = parte;
    }

    public String getSalmo() {
        return salmo;
    }

    public void setSalmo(String salmo) {
        this.salmo = salmo;
    }

    public Spanned getTextos() {
        SpannableStringBuilder ssb = new SpannableStringBuilder();
        Spanned str = Utils.fromHtml(Utils.getFormato(intro));
        ssb.append(str);
        return Utils.ssbSmallSize(ssb);
    }

    /**
     *
     * @return Texto al final de cada salmo
     * @since 2022.01
     */
    public Spanned getFinSalmo() {
        String fin = "Gloria al Padre, y al Hijo, y al Espíritu Santo." + BR
                + NBSP_SALMOS + "Como era en el principio ahora y siempre, "
                + NBSP_SALMOS + "por los siglos de los siglos. Amén.";
        return Utils.fromHtml(fin);
    }

    public String getFinSalmoForRead() {
        return "Gloria al Padre, y al Hijo, y al Espíritu Santo." +
                "Como era en el principio ahora y siempre, "
                + "por los siglos de los siglos. Amén.";
    }

}