package org.deiverbum.app.model;

import static org.deiverbum.app.utils.Constants.TITLE_RESPONSORY;
import static org.deiverbum.app.utils.Utils.LS2;

import android.text.SpannableStringBuilder;

import org.deiverbum.app.utils.Utils;

import java.util.Locale;

public class LHOfficeBiblical extends Biblical {
    private String tema;
    private LHResponsory responsorioLargo;

    public String getTema() {
        return tema;
    }

    public void setTema(String tema) {
        this.tema = tema;
    }

    public String getTemaForRead() {
        return tema+".";
    }

    public LHResponsory getResponsorioLargo() {
        return responsorioLargo;
    }

    public void setResponsorioLargo(LHResponsory responsorioLargo) {
        this.responsorioLargo=responsorioLargo;
    }

    //@Override
    public SpannableStringBuilder getHeaderForReview() {
        String s=String.format(new Locale("es"),"%s lectura",Utils.getOrdinal(order)).toUpperCase();
        return Utils.formatTitle(s);
    }

    public SpannableStringBuilder getHeader() {
        return Utils.formatTitle("PRIMERA LECTURA");
    }

    public SpannableStringBuilder getResponsorioHeader() {
        SpannableStringBuilder sb=new SpannableStringBuilder();
        sb.append(Utils.toRed(String.format(new Locale("es"),"%-15s", TITLE_RESPONSORY)));
        sb.append(Utils.toRed(getCita()));
        return sb;
    }

    public String getResponsorioHeaderForRead() {
        return Utils.pointAtEnd(TITLE_RESPONSORY);
    }

    /**
     * <p>Obtiene la lectura bíblica completa, incluyendo el responsorio, formateada para la vista.</p>
     * @since 2022.01
     * @return Un objeto {@link SpannableStringBuilder con el contenido.}
     */
    @Override
    public SpannableStringBuilder getAll() {
        SpannableStringBuilder sb = new SpannableStringBuilder();
        sb.append(getHeader());
        sb.append(LS2);
        sb.append(book.getLiturgyName());
        sb.append("    ");
        sb.append(Utils.toRed(getCita()));
        sb.append(LS2);
        sb.append(Utils.toRed(getTema()));
        sb.append(LS2);
        sb.append(getTextoSpan());
        sb.append(Utils.LS);
        sb.append(responsorioLargo.getAll());
        return sb;
    }

    /**
     * <p>Obtiene la lectura bíblica completa formateada para la lectura de voz.</p>
     * @since 2022.01
     * @return Un objeto {@link SpannableStringBuilder con el contenido.}
     */
    @Override
    public SpannableStringBuilder getAllForRead(){
        SpannableStringBuilder sb=new SpannableStringBuilder();
        sb.append(getHeaderForRead());
        sb.append(book.getForRead());
        sb.append(getTemaForRead());
        sb.append(getTexto());
        sb.append(getConclusionForRead());
        sb.append(getResponsorioHeaderForRead());
        sb.append(getResponsorioLargo().getAllForRead());
        return sb;
    }

    public Integer getOrden() {
        return this.order;
    }

    public void setOrden(Integer orden) {
        this.order=orden;
    }
}