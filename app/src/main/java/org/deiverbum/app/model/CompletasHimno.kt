package org.deiverbum.app.model

class CompletasHimno {
    private var himno: LHHymn? = null
    fun getHimno(): LHHymn? {
        return himno
    }

    @Suppress("unused")
    fun setHimno(himno: LHHymn?) {
        this.himno = himno
    }
}