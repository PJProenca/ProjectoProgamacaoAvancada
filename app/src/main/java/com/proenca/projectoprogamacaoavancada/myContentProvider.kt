package com.proenca.projectoprogamacaoavancada

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri

class myContentProvider: ContentProvider() {

    var dbOpenHelper : BD_Open_Helper? = null

    override fun onCreate(): Boolean {
        dbOpenHelper= BD_Open_Helper(context)
        return true
    }

    override fun query(
        p0: Uri,
        p1: Array<out String>?,
        p2: String?,
        p3: Array<out String>?,
        p4: String?
    ): Cursor? {
        TODO("Not yet implemented")
    }

    override fun getType(p0: Uri): String? {
        TODO("Not yet implemented")
    }

    override fun insert(p0: Uri, p1: ContentValues?): Uri? {
        TODO("Not yet implemented")
    }

    override fun delete(p0: Uri, p1: String?, p2: Array<out String>?): Int {
        TODO("Not yet implemented")
    }

    override fun update(uri: Uri, values: ContentValues?, selection: String?, selectionArg: Array<out String>?): Int {
        TODO("Not yet implemented")
    }

    companion object{
        const val AUTORIDADE = "com.proenca.projectoprogamacaoavancada"
        const val URI_PACIENTES = 100
        const val URI_PACIENTE_ESP = 101
        const val  URI_ALIMENTOS = 200
        const val  URI_ALIMENTOS_ESP = 201
        const val URI_REGISTOS = 300
        const val URI_REGISTOS_ESP = 301

        fun getUriMatcher(): UriMatcher{
            val uriMatcher = UriMatcher(UriMatcher.NO_MATCH)

            uriMatcher.addURI(AUTORIDADE,TabelaPacientes.NOME, URI_PACIENTES)
            uriMatcher.addURI(AUTORIDADE,"${TabelaPacientes.NOME}/#", URI_PACIENTE_ESP)
            uriMatcher.addURI(AUTORIDADE,TabelaAlimentos.NOME, URI_ALIMENTOS)
            uriMatcher.addURI(AUTORIDADE,"${TabelaAlimentos.NOME}/#", URI_ALIMENTOS_ESP)
            uriMatcher.addURI(AUTORIDADE,TabelaRegistos.NOME, URI_REGISTOS)
            uriMatcher.addURI(AUTORIDADE,"${TabelaRegistos.NOME}/#", URI_REGISTOS_ESP)

            return uriMatcher

        }
    }

}