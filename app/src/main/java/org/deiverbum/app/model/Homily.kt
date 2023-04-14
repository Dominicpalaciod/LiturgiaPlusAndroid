package org.deiverbum.app.model;

import static org.deiverbum.app.utils.Utils.LS2;

import android.text.SpannableStringBuilder;

import androidx.room.Ignore;

import org.deiverbum.app.utils.ColorUtils;
import org.deiverbum.app.utils.Utils;

import java.util.List;

@SuppressWarnings("SameReturnValue")
public class Homily {

    public Integer homilyID;
    public Integer opusFK;
    public String date;
    public Integer book;
    public Integer chapter;
    public Integer number;
    public Integer paragraph;
    public Integer collectionFK;
    public Integer colNumber;
    public Integer colParagraph;
    @Ignore
    public String padre;
    public String homily;
    @Ignore
    public List<HomilyList> homilias;
    @Ignore
    private Today today;

    public Homily() {
    }

    public List<HomilyList> getHomilias() {
        return this.homilias;
    }

    public void setHomilias(List<HomilyList> homilias) {
        this.homilias = homilias;
    }

    @SuppressWarnings("unused")
    private SpannableStringBuilder getTitulo() {
        return Utils.toH3Red("HOMILÍAS");
    }

    private String getTituloForRead() {
        return "Homilías.";
    }

    public SpannableStringBuilder getForView(boolean isNightMode) {
        ColorUtils.isNightMode = isNightMode;
        SpannableStringBuilder sb = new SpannableStringBuilder();
        try {
            //sb.append(LS2);
            sb.append(today.getSingleForView());
            sb.append(LS2);
            sb.append(getTitulo());
            sb.append(LS2);
            for (HomilyList h : homilias) {
                sb.append(h.getAllForView());
            }
        } catch (Exception e) {
            sb.append(Utils.createErrorMessage(e.getMessage()));
        }
        return sb;
    }

    public StringBuilder getAllForRead() {
        StringBuilder sb = new StringBuilder();
        try {
            sb.append(today.getSingleForRead());
            sb.append(getTituloForRead());
            for (HomilyList s : homilias) {
                sb.append(s.getAllForRead());
            }
        } catch (Exception e) {
            sb.append(Utils.createErrorMessage(e.getMessage()));
        }
        return sb;
    }

    public void setHoy(Today today) {
        this.today = today;
    }
}