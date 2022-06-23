package com.proenca.projectoprogamacaoavancada

import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns

class TabelaRegistos(db:SQLiteDatabase):TabelasBD(db,NOME) {
    override fun cria(){
        db.execSQL("CREATE TABLE $NOME (${BaseColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT," +
                "$DATA_REG TEXT NOT NULL,$GLICEMIA INTEGER NOT NULL," +
                "$INSULINA INTEGER NOT NULL,$PESO REAL NOT NULL ," +
                "$ID_PACIENTE INTEGER NOT NULL," +
                "FOREIGN KEY ($ID_PACIENTE) REFERENCES ${TabelaPacientes.NOME}(${BaseColumns._ID}) ON DELETE RESTRICT) ")
    }

    companion object{
        const val NOME = "Registos"
        const val ID_PACIENTE = "ID_do_Paciente"
        const val DATA_REG = "Data_do_Registo"
        const val GLICEMIA="Glicemia_Medida"
        const val INSULINA = "Unidade_Administrada"
        const val PESO = "Peso"

        val TODAS_COLUNAS = arrayOf(BaseColumns._ID,
            TabelaRegistos.ID_PACIENTE,
            TabelaRegistos.DATA_REG,
            TabelaRegistos.GLICEMIA,
            TabelaRegistos.INSULINA,
            TabelaRegistos.PESO,
        )
    }
}