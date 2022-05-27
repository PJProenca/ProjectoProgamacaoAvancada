package com.proenca.projectoprogamacaoavancada

import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns

class TabelaAlimentos(db:SQLiteDatabase) : TabelasBD(db,NOME) {

    override fun cria() {
        db.execSQL("CREATE TABLE $NOME (${BaseColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT, $C_NOME TEXT NOT NULL,$C_VALOR INTEGER NOT NULL )")
    }

    companion object{
        const val NOME = "Alimentos"
        const val C_NOME = "Alimento"
        const val C_VALOR = "Valor_Hidratos"
    }
}