package com.proenca.projectoprogamacaoavancada

import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteQueryBuilder
import android.provider.BaseColumns

class TabelaRegistos(db:SQLiteDatabase):TabelasBD(db,NOME) {
    override fun cria(){
        db.execSQL("CREATE TABLE $NOME (${BaseColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT," +
                "$DATA_REG INTEGER NOT NULL,$GLICEMIA INTEGER NOT NULL," +
                "$INSULINA INTEGER NOT NULL,$PESO REAL NOT NULL ," +
                "$ID_PACIENTE INTEGER NOT NULL," +
                "FOREIGN KEY ($ID_PACIENTE) REFERENCES ${TabelaPacientes.NOME}(${BaseColumns._ID}) ON DELETE RESTRICT) ")
    }

    override fun query(
        columns: Array<String>,
        selection: String?,
        selectionArgs: Array<String>?,
        groupBy: String?,
        having: String?,
        orderBy: String?
    ): Cursor {
        val queryBuilder = SQLiteQueryBuilder()
        queryBuilder.tables = "$NOME INNER JOIN ${TabelaPacientes.NOME} ON ${TabelaPacientes.CAMPO_ID} = $ID_PACIENTE"

        return queryBuilder.query(db, columns, selection, selectionArgs, groupBy, having, orderBy)
    }

    companion object{
        const val NOME = "Registos"
        const val CAMPO_ID = "$NOME.${BaseColumns._ID}"
        const val ID_PACIENTE = "ID_do_Paciente"
        const val DATA_REG = "Data_do_Registo"
        const val GLICEMIA="Glicemia_Medida"
        const val INSULINA = "Unidade_Administrada"
        const val PESO = "Peso"

        val TODAS_COLUNAS = arrayOf(CAMPO_ID,
            ID_PACIENTE,
            DATA_REG,
            GLICEMIA,
            INSULINA,
            PESO,
            TabelaPacientes.C_NOME
        )
    }
}