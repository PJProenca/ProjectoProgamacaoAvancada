package com.proenca.projectoprogamacaoavancada

import android.database.Cursor
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class AdapaterPacientes(val fragment: PacienteOpcoesFrag) : RecyclerView.Adapter<AdapaterPacientes.ViewHolderPacientes>() {

    var cursor:Cursor? =null
    get() = field
    set(value) {
        if (field != value){
            field=value
            notifyDataSetChanged()
        }
    }

    class ViewHolderPacientes(itemPacientes: View) : RecyclerView.ViewHolder(itemPacientes) {

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolderPacientes {
        val itemPacientes =fragment.layoutInflater.inflate(R.layout.paciente_item,parent,false)
        return ViewHolderPacientes(itemPacientes)
    }



    override fun onBindViewHolder(holder: ViewHolderPacientes, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        if (cursor == null) return 0

        return cursor!!.count
    }
}