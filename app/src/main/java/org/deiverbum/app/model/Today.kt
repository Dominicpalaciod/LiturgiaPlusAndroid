package org.deiverbum.app.model

import android.text.SpannableStringBuilder
import androidx.room.Ignore
import org.deiverbum.app.utils.ColorUtils
import org.deiverbum.app.utils.Utils

/**
 *
 *
 * Esta clase recoge información valiosa sobre el día litúrgico.
 *
 *
 * @author A. Cedano
 * @version 1.0
 * @since 2022.1
 */
//@SuppressWarnings("SameReturnValue")
class Today {
    var todayDate: Int = 0
        get() = field
        set(todayDate) {
            field = todayDate
        }

    var weekDay: Int? = null
        get() = if(field==null) 0 else field
        set(weekDay) {field=weekDay}

    var timeID: Int? = null
        get() = field
        set(timeID) {
            field = timeID
        }

    @Ignore
    var weekDayFK: Int? = null
    var liturgyFK: Int? = null
    var previousFK: Int? = null
        get() = field
        set(previo_id) {
            field = previo_id
        }
    var massReadingFK: Int? = null
    var invitatoryFK: Int? = null

    //@Ignore
    var hasSaint: Int? = null
    @JvmField
    var saintFK: Int? = null
    var oHymnFK: Int? = null
    var oPsalmodyFK: Int? = null
    var oVerseFK: Int? = null
    @JvmField
    var oBiblicalFK: Int? = null
    var oPatristicFK: Int? = null
    var oPrayerFK: Int? = null
    var oTeDeum: Int? = null
    var lHymnFK: Int? = null
    var lPsalmodyFK: Int? = null
    var lBiblicalFK: Int? = null
    var lBenedictusFK: Int? = null
    var lIntercessionsFK: Int? = null
    var lPrayerFK: Int? = null
    var tHymnFK: Int? = null
    var tPsalmodyFK: Int? = null
    var tBiblicalFK: Int? = null
    var tPrayerFK: Int? = null
    var sHymnFK: Int? = null
    var sPsalmodyFK: Int? = null
    var sBiblicalFK: Int? = null
    var sPrayerFK: Int? = null
    var nHymnFK: Int? = null
    var nPsalmodyFK: Int? = null
    var nBiblicalFK: Int? = null
    var nPrayerFK: Int? = null
    var vHymnFK: Int? = null
    var vPsalmodyFK: Int? = null
    var vBiblicalFK: Int? = null
    var vMagnificatFK: Int? = null
    var vIntercessionsFK: Int? = null
    var vPrayerFK: Int? = null

    @JvmField
    @Ignore
    var liturgyDay: Liturgy? = null

    @JvmField
    @Ignore
    var liturgyPrevious: Liturgy? = null

    @Ignore
    var liturgyTime: LiturgyTime? = null
    fun setMLecturasFK(mLecturasFK: Int?) {
        massReadingFK = mLecturasFK
    }

    val tituloVisperas: String?
        get() = if (liturgyPrevious != null) {
            liturgyPrevious!!.name!!.replace(" I Vísperas.| I Vísperas".toRegex(), "")
        } else {
            liturgyDay!!.name
        }
    val titulo: String?
        get() = if (liturgyDay!!.typeID == 6) tituloVisperas else liturgyDay!!.name
    val tituloForRead: String?
        get() = if (liturgyDay!!.typeID == 6) tituloVisperas else liturgyDay!!.titleForRead
    val fecha: String
        get() = if (todayDate != null) Utils.getLongDate(todayDate.toString()) else ""
    val tiempo: String?
        get() = if (liturgyDay!!.typeID == 6 && liturgyPrevious != null) liturgyPrevious!!.liturgiaTiempo!!.liturgyName else liturgyDay!!.liturgiaTiempo!!.liturgyName



    fun hasSaintToday(): Boolean {
        return hasSaint != null && hasSaint == 1 //this.hasSaint == 1;
    }

    fun getAllForView(hasInvitatory: Boolean, nightMode: Boolean): SpannableStringBuilder {
        liturgyDay!!.setHasSaint(hasSaintToday())
        ColorUtils.isNightMode = nightMode
        val sb = SpannableStringBuilder()
        try {
            sb.append(Utils.LS)
            sb.append(fecha)
            sb.append(Utils.LS2)
            sb.append(Utils.toH2(tiempo))
            sb.append(Utils.LS2)
            sb.append(Utils.toH3(titulo))
            //liturgyDay.today.previousFK
            //sb.append(liturgyDay.getForView(hasInvitatory,previousFK));
            var bh: BreviaryHour? =liturgyDay!!.breviaryHour

            if (liturgyDay!!.typeID == 0) {
                if (oBiblicalFK == 600010101) {
                    sb.append(bh?.getOficioEaster()?.forView)
                } else {
                    sb.append(
                        bh?.getMixtoForView(
                            liturgyDay!!.liturgiaTiempo!!,
                            hasSaintToday()
                        )
                    ) //.getForView(liturgyDay.liturgyTime));
                }
            }
            if (liturgyDay!!.typeID == 1) {
                if (oBiblicalFK == 600010101) {
                    sb.append(liturgyDay!!.breviaryHour!!.getOficioEaster()?.forView)
                } else {
                    sb.append(
                        liturgyDay!!.breviaryHour!!.getOficio(hasInvitatory)!!
                            .getForView(liturgyDay!!.liturgiaTiempo, hasSaintToday(), nightMode)
                    )
                }
            }
            if (liturgyDay!!.typeID == 2) {
                sb.append(
                    liturgyDay!!.breviaryHour!!.getLaudes(hasInvitatory)!!
                        .getForView(liturgyDay!!.liturgiaTiempo!!, hasSaintToday())
                )
            }
            if (liturgyDay!!.typeID == 3 || liturgyDay!!.typeID == 4 || liturgyDay!!.typeID == 5) {
                sb.append(
                    liturgyDay!!.breviaryHour!!.getIntermedia()!!
                        .getForView(liturgyDay!!.liturgiaTiempo!!, liturgyDay!!.typeID)
                )
            }
            if (liturgyDay!!.typeID == 6) {
                sb.append(
                    liturgyDay!!.breviaryHour!!.getVisperas()!!
                        .getForView(liturgyDay!!.liturgiaTiempo)
                )
            }
            if (liturgyDay!!.typeID == 7) {
                sb.append(liturgyDay!!.breviaryHour!!.getCompletas()!!.getAllForView())
            }
        } catch (e: Exception) {
            sb.append(Utils.createErrorMessage(e.message))
        }
        return sb
    }

    //liturgyDay.today.previousFK
    //sb.append(liturgyDay.getAllForView());
    val singleForView: SpannableStringBuilder
        get() {
            val sb = SpannableStringBuilder()
            try {
                sb.append(Utils.LS)
                sb.append(fecha)
                sb.append(Utils.LS2)
                sb.append(Utils.toH2(tiempo))
                sb.append(Utils.LS2)
                sb.append(Utils.toH3(titulo))
                //liturgyDay.today.previousFK
                //sb.append(liturgyDay.getAllForView());
            } catch (e: Exception) {
                sb.append(Utils.createErrorMessage(e.message))
            }
            return sb
        }

    fun getAllForRead(hasInvitatory: Boolean): StringBuilder {
        val sb = StringBuilder()
        try {
            sb.append(Utils.pointAtEnd(fecha))
            sb.append(tituloForRead)
            //sb.append(liturgyDay.getForRead());
            if (liturgyDay!!.typeID == 0) {
                if (oBiblicalFK == 600010101) {
                    sb.append(liturgyDay!!.breviaryHour!!.getOficioEaster()?.forRead)
                } else {
                    //TODO
                    sb.append(liturgyDay!!.breviaryHour!!.getMixtoForRead())
                }
            }
            if (liturgyDay!!.typeID == 1) {
                if (oBiblicalFK == 600010101) {
                    sb.append(liturgyDay!!.breviaryHour!!.getOficioEaster()?.forRead)
                } else {
                    sb.append(liturgyDay!!.breviaryHour!!.getOficio(hasInvitatory)?.forRead)
                }
            }
            if (liturgyDay!!.typeID == 2) {
                sb.append(liturgyDay!!.breviaryHour!!.getLaudes(hasInvitatory)!!.forRead)
            }
            if (liturgyDay!!.typeID == 3 || liturgyDay!!.typeID == 4 || liturgyDay!!.typeID == 5) {
                sb.append(liturgyDay!!.breviaryHour!!.getIntermedia()!!.forRead)
            }
            if (liturgyDay!!.typeID == 6) {
                sb.append(liturgyDay!!.breviaryHour!!.getVisperas()!!.getAllForRead())
            }
            if (liturgyDay!!.typeID == 7) {
                sb.append(liturgyDay!!.breviaryHour!!.getCompletas()!!.getForRead())
            }
        } catch (e: Exception) {
            sb.append(Utils.createErrorMessage(e.message))
        }
        return sb
    }

    fun getSingleForRead(): StringBuilder {
        val sb = StringBuilder()
        try {
            sb.append(Utils.pointAtEnd(fecha))
            sb.append(tituloForRead)
            //sb.append(liturgyDay.getForRead());
        } catch (e: Exception) {
            sb.append(Utils.createErrorMessage(e.message))
        }
        return sb
    }
}