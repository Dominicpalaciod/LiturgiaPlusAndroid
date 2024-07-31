package org.deiverbum.app.core.database.model.nia

import android.text.SpannableStringBuilder
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.buildAnnotatedString
import org.deiverbum.app.core.database.model.external.PopulatedLiturgiaTypus
import org.deiverbum.app.core.model.data.LiturgiaTypus
import org.deiverbum.app.core.model.data.UserDataDynamic

/**
 *
 * Reúne aquellos elementos que son comunes a las diversas horas del Breviario.
 * Las clases de las diferentes horas extienden de ésta,
 * y cada una tendrá aquellos elementos que le sean propios.
 *
 * @property typus Una cadena para identificar el tipo de celebración. Con este valor
 * si indica al adapter qué clase debe usarse para mapear los datos procedentes de la red.
 *
 *
 * @author A. Cedano
 * @version 1.0
 * @since 2022.1
 *
 * @see [LiturgiaTypus]
 *
 */


abstract class PopulatedBreviarium(typus: String) :
    PopulatedLiturgiaTypus {
    fun forView(): SpannableStringBuilder {
        return super.getHeaders()
    }

    @Composable
    override fun allForView(calendarTime: Int, userData: UserDataDynamic): AnnotatedString {
        return buildAnnotatedString {

            Text(text = "")

            //h3Rubric(text = liturgia?.nomen!!, userData = userData)
            //TextBody(text = fecha, useLineBreak =true )
            //TextBody(text = liturgia?.tempus?.externus!!, useLineBreak =true )

        }
    }

    @Composable
    open fun allForVieww(calendarTime: Int, userData: UserDataDynamic) {
    }
}