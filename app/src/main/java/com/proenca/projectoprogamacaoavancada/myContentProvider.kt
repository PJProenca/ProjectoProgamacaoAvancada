package com.proenca.projectoprogamacaoavancada

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import android.provider.BaseColumns

class myContentProvider: ContentProvider() {

    var dbOpenHelper : BD_Open_Helper? = null

    override fun onCreate(): Boolean {
        dbOpenHelper= BD_Open_Helper(context)
        return true
    }

    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?
    ): Cursor? {
        val db = dbOpenHelper!!.readableDatabase

        val colunas = projection as Array<String>
        val selArgs = selectionArgs as Array<String>?

        val id = uri.lastPathSegment

        return when (getUriMatcher().match(uri)){
            URI_PACIENTES->TabelaPacientes(db).query(colunas,selection,selArgs,null,null,sortOrder)
            URI_PACIENTE_ESP -> TabelaPacientes(db).query(colunas,"${BaseColumns._ID}=?", arrayOf("$id"),null,null,null)
            URI_ALIMENTOS -> TabelaAlimentos(db).query(colunas,selection,selArgs,null,null,sortOrder)
            URI_ALIMENTOS_ESP -> TabelaAlimentos(db).query(colunas,"${BaseColumns._ID}=?", arrayOf("$id"),null,null,null)
            URI_REGISTOS -> TabelaRegistos(db).query(colunas,selection,selArgs,null,null,sortOrder)
            URI_REGISTOS_ESP -> TabelaRegistos(db).query(colunas,"${BaseColumns._ID}=?", arrayOf("$id"),null,null,null)
            else -> null
        }
    }

    override fun getType(uri: Uri): String? =
        when(getUriMatcher().match(uri)){
            URI_PACIENTES ->"$REGISTOS_MULTIPLOS/${TabelaPacientes.NOME}"
            URI_ALIMENTOS ->"$REGISTOS_MULTIPLOS/${TabelaAlimentos.NOME}"
            URI_REGISTOS ->"$REGISTOS_MULTIPLOS/${TabelaRegistos.NOME}"
            URI_PACIENTE_ESP ->"$REGISTO_UNICO/${TabelaPacientes.NOME}"
            URI_ALIMENTOS_ESP ->"$REGISTO_UNICO/${TabelaAlimentos.NOME}"
            URI_REGISTOS_ESP ->"$REGISTO_UNICO/${TabelaRegistos.NOME}"
            else -> null
        }


    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        val db = dbOpenHelper!!.writableDatabase
        requireNotNull(values)
        val id = when(getUriMatcher().match(uri)){

            URI_PACIENTES-> TabelaPacientes(db).insert(values)
            URI_ALIMENTOS-> TabelaAlimentos(db).insert(values)
            URI_REGISTOS-> TabelaRegistos(db).insert(values)
            URI_ALIM_REG -> TabelaAlimento_Registo(db).insert(values)
            else->null
        }
        if (id == 1L) return null

        return Uri.withAppendedPath(uri,"$id")
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        val db = dbOpenHelper!!.writableDatabase

        val id = uri.lastPathSegment

        return when(getUriMatcher().match(uri)){

            URI_PACIENTE_ESP -> TabelaPacientes(db).delete("${BaseColumns._ID}=?", arrayOf("$id"))
            URI_ALIMENTOS_ESP -> TabelaAlimentos(db).delete("${BaseColumns._ID}=?", arrayOf("$id"))
            URI_REGISTOS_ESP -> TabelaRegistos(db).delete("${BaseColumns._ID}=?", arrayOf("$id"))
            URI_ALIM_REG_ESP -> TabelaAlimento_Registo(db).delete("${TabelaAlimento_Registo.REG_ID_REGISTO}=?", arrayOf("$id"))

            else->0
        }
    }

    override fun update(uri: Uri, values: ContentValues?, selection: String?, selectionArg: Array<out String>?): Int {
        requireNotNull(values)
        val db = dbOpenHelper!!.writableDatabase

        val id = uri.lastPathSegment

        return when(getUriMatcher().match(uri)){

            URI_PACIENTE_ESP -> TabelaPacientes(db).update(values,"${BaseColumns._ID}=?", arrayOf("$id"))
            URI_ALIMENTOS_ESP -> TabelaAlimentos(db).update(values,"${BaseColumns._ID}=?", arrayOf("$id"))
            URI_REGISTOS_ESP -> TabelaRegistos(db).update(values,"${BaseColumns._ID}=?", arrayOf("$id"))                    

            else -> 0
        }
    }

    companion object{
        const val AUTORIDADE = "com.proenca.projectoprogamacaoavancada"
        const val URI_PACIENTES = 100
        const val URI_PACIENTE_ESP = 101
        const val URI_ALIMENTOS = 200
        const val URI_ALIMENTOS_ESP = 201
        const val URI_REGISTOS = 300
        const val URI_REGISTOS_ESP = 301
        const val URI_ALIM_REG = 400
        const val URI_ALIM_REG_ESP = 401

        const val REGISTO_UNICO = "vnd.android.cursor.item"
        const val REGISTOS_MULTIPLOS = "vnd.android.cursor.dir"


        fun getUriMatcher(): UriMatcher{
            val uriMatcher = UriMatcher(UriMatcher.NO_MATCH)

            uriMatcher.addURI(AUTORIDADE,TabelaPacientes.NOME, URI_PACIENTES)
            uriMatcher.addURI(AUTORIDADE,"${TabelaPacientes.NOME}/#", URI_PACIENTE_ESP)
            uriMatcher.addURI(AUTORIDADE,TabelaAlimentos.NOME, URI_ALIMENTOS)
            uriMatcher.addURI(AUTORIDADE,"${TabelaAlimentos.NOME}/#", URI_ALIMENTOS_ESP)
            uriMatcher.addURI(AUTORIDADE,TabelaRegistos.NOME, URI_REGISTOS)
            uriMatcher.addURI(AUTORIDADE,"${TabelaRegistos.NOME}/#", URI_REGISTOS_ESP)
            uriMatcher.addURI(AUTORIDADE,TabelaAlimento_Registo.NOME, URI_ALIM_REG)
            uriMatcher.addURI(AUTORIDADE,"${TabelaAlimento_Registo.NOME}/#", URI_ALIM_REG_ESP)

            return uriMatcher

        }
    }

}