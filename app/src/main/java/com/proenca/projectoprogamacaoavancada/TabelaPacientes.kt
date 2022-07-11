package com.proenca.projectoprogamacaoavancada

import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns

class TabelaPacientes(db: SQLiteDatabase) : TabelasBD(db,NOME) {

    override fun cria(){
        db.execSQL("CREATE TABLE $NOME (${BaseColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT, $C_NOME TEXT NOT NULL, $C_DATA_NASC INTEGER NOT NULL, $C_ALTURA INTEGER NOT NULL)")
    }

    companion object{
        const val NOME = "Pacientes"
        const val C_NOME = "Nome"
        const val C_DATA_NASC = "Data_de_Nascimento"
        const val C_ALTURA = "Altura"
        const val CAMPO_ID = "$NOME.${BaseColumns._ID}"

        val TODAS_COLUNAS = arrayOf(BaseColumns._ID, C_NOME, C_DATA_NASC, C_ALTURA)
    }
}