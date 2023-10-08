package org.deiverbum.app.core.model.data

import android.text.SpannableStringBuilder
import org.deiverbum.app.util.Utils

/**
 *
 *
 * Ritos conclusivos de la Liturgia.
 *
 *
 * @author A. Cedano
 * @version 1.0
 * @since 2023.1.3
 */

class RitusConclusionis {
    companion object {

        var viewDominusnosBenedicat: SpannableStringBuilder = SpannableStringBuilder()

        init {
            val ssb = SpannableStringBuilder("")
            ssb.append(Utils.toRed("V/. "))
            ssb.append("El Señor nos bendiga, nos guarde de todo mal y nos lleve a la vida eterna.")
            ssb.append(Utils.LS)
            ssb.append(Utils.toRed("R/. "))
            ssb.append("Amén.")
            ssb.append(Utils.LS2)
            viewDominusnosBenedicat = ssb
        }

        var readDominusNosBenedicat: String =
            "El Señor nos bendiga, nos guarde de todo mal y nos lleve a la vida eterna. Amén."

        var horasMayoresForRead: String =
            "El Señor nos bendiga, nos guarde de todo mal y nos lleve a la vida eterna. Amén."

        var titleForView = SpannableStringBuilder(Utils.formatTitle("CONCLUSIÓN"))
        var titleForRead = "Conclusión."
    }
}




