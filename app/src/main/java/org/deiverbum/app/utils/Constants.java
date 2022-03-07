package org.deiverbum.app.utils;

import android.graphics.Color;
import android.text.style.ForegroundColorSpan;

public class Constants {

    public static ForegroundColorSpan RED_COLOR = new ForegroundColorSpan(Color.parseColor("#A52A2A"));
    public static final String VOICE_INI = "Iniciando lectura.";
    public static final String ERR_LANG = "Lenguaje no soportado.";
    public static final String ERR_INITIALIZATION = "Falló la inicialización.";
    public static final String ERR_BIBLIA = "Todavía no hay introducción a este libro. <br>Proyecto abierto a la colaboración. <br><a href=\"http://bit.ly/2FInp4n\">Ver los detalles aquí</a>.";
    /*Cambiar tambien en build.gradle del módulo app y en new_YN.json*/
    public static final int VERSION_CODE = 202201011;

    public static final String CSS_RED_A = "<font color=\"#A52A2A\">";

    public static final String OLD_SEPARATOR = "≡";
    public static final String SEPARADOR = "\\.";
    public static final String CSS_RED_Z = "</font>";
    public static final String BRS = "<br /><br />";
    public static final String BR = "<br />";
    public static final String PRE_ANT = CSS_RED_A + "Ant. " + CSS_RED_Z;
    public static final String NBSP_4 = " &nbsp;&nbsp;&nbsp;&nbsp;";
    public static final String RESP_V = CSS_RED_A + "V. " + CSS_RED_Z;
    public static final String RESP_R = CSS_RED_A + "R. " + CSS_RED_Z;
    public static final String PADRENUESTRO = "Padre nuestro,~¦que estás en el cielo,~¦santificado sea tu Nombre;~¦" +
            "venga a nosotros tu reino;~¦hágase tu voluntad~¦en la tierra como en el cielo.~¦" +
            "Danos hoy nuestro pan de cada día;~¦perdona nuestras ofensas,~¦" +
            "como también nosotros perdonamos a los que nos ofenden;~¦" +
            "no nos dejes caer en la tentación,~¦y líbranos del mal. Amén.";

    //Mensajes de error
    public static final String ERR_RESPONSORIO = CSS_RED_A + "¡ERROR! " + CSS_RED_Z + BR + "Hay un error en el responsorio de este día, " +
            "por favor comunícalo al desarrollador a la dirección siguiente: " + "padre.cedano@gmail.com" + BRS;

    public static final String ERR_REPORT = "\n\nA fin de corregir cuanto antes este error, " +
            "por favor comunícalo al desarrollador a la dirección siguiente: " + "padre.cedano@gmail.com";


    public static final String ERR_SUBJECT = String.format("Reporte de error Liturgia+ v. %d", Constants.VERSION_CODE);

    //Otros mensajes
    public static final String PACIENCIA = "\n\nLa paciencia todo lo alcanza. Por favor espere ...";
    public static final String NBSP_SALMOS = BR + "&nbsp;&nbsp;";
    public static final String RESP_A = CSS_RED_A + " * " + CSS_RED_Z;
    static final String PRECES_IL = BRS + CSS_RED_A + "Se pueden añadir algunas intenciones libres." + CSS_RED_Z + BRS;
    static final String OBIEN = BRS + CSS_RED_A + "O bien:" + CSS_RED_Z + BRS;
    static final String PRECES_R = NBSP_SALMOS + CSS_RED_A + "– " + CSS_RED_Z;

    public static final String DATA_NOTFOUND = "No se encontraron datos";
    public static final String FIREBASE_SANTOS = "/liturgia/santos/";
    public static final String DOC_NOTFOUND = "Documento no encontrado";
    public static final String CONTENT_NOTFOUND = "Este contenido no está " +
            "disponible todavía. Puede que estés intentando acceder a un " +
            "contenido fuera de fecha. Si no es el caso, ponte en contacto " +
            "con el desarrollador para informarle de que hay un problema en " +
            "este módulo.";

    public static final String NOTFOUND_OR_NOTCONNECTION = "Este contenido no" +
            " está " +
            "disponible. Puede que estés intentando acceder a un " +
            "contenido fuera de fecha o que en este momento no estés " +
            "conectado a internet. " +
            "Si no es el caso, ponte en contacto " +
            "con el desarrollador para informarle de que hay un problema en " +
            "este módulo.";

    public static final String FILE_PRIVACY = "res/raw/privacy_202201.json";
    public static final String FILE_TERMS = "res/raw/terms_202201.json";
    public static final String PREF_ACCEPT = "accept_terms";
    public static final String PREF_ANALYTICS = "enable_analytics";
    public static final String PREF_CRASHLYTICS = "enable_crashlytics";

    public static final String MSG_LEGAL = "Si tienes alguna duda, puedes " +
            "pulsar en el botón \"Enviar eMail\" para ponerte en contacto con" +
            " " +
            "el Desarrollador. Se abrirá tu programa de eMail para que " +
            "puedas " +
            "redactar y enviar tu mensaje. Los datos recibidos serán " +
            "tratados de acuerdo a " +
            "lo indicado en la Política de Privacidad.\n\n";

    public static final String DIALOG_LEGAL_TITLE = "Aviso";
    public static final String DIALOG_LEGAL_BODY = "Retirarás tu consentimiento y Liturgia+ se cerrará al pulsar CONFIRMAR.";

    public static final String DIALOG_LEGAL_OK = "CONFIRMAR";
    public static final String DIALOG_LEGAL_CANCEL = "CANCELAR";


    private Constants() {
    }

}
