package com.proenca.projectoprogamacaoavancada

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class BD_Open_Helper(context : Context) : SQLiteOpenHelper(context,NOME,null,VERSAO){
    override fun onCreate(db: SQLiteDatabase?) {
        requireNotNull(db)
        TabelaPacientes(db).cria()
        TabelaAlimentos(db).cria()
    }

    override fun onUpgrade(p0: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }
    companion object{
        const val NOME = "Insulina_admin.db"
        private const val  VERSAO = 1
    }

}


