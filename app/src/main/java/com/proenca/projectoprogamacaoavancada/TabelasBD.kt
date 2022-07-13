package com.proenca.projectoprogamacaoavancada

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase

abstract class TabelasBD(val db: SQLiteDatabase, val nome: String)  {

    abstract fun cria()

    fun insert(values: ContentValues) = db.insert(nome,null,values)
    fun update(values: ContentValues, whereClauses: String, whereArgs: Array<String>) = db.update(nome,values,whereClauses,whereArgs)
    fun delete(whereClauses: String, whereArgs: Array<String>) = db.delete(nome,whereClauses,whereArgs)
    open fun query(columns: Array<String>, selection: String?, selectionArgs: Array<String>?, groupBy: String?, having: String?, orderBy: String?) = db.query(nome,columns,selection,selectionArgs,groupBy,having,orderBy)
}