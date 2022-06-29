package com.proenca.projectoprogamacaoavancada

import android.database.Cursor
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
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
            val textViewNome = itemPacientes.findViewById<TextView>(R.id.textViewNomePaciente)
            val textViewDataNasc = itemPacientes.findViewById<TextView>(R.id.textViewDataNasc)
            val textViewAltura = itemPacientes.findViewById<TextView>(R.id.textViewAltura)

            var paciente : Pacientes? = null
            get()= field
            set(value) {
                field=value
                textViewNome.text = paciente?.nome?: ""
                textViewDataNasc.text = paciente?.dataNasc?: ""
                textViewAltura.text = paciente?.altura.toString()
            }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolderPacientes {
        val itemPacientes =fragment.layoutInflater.inflate(R.layout.paciente_item,parent,false)
        return ViewHolderPacientes(itemPacientes)
    }



    override fun onBindViewHolder(holder: ViewHolderPacientes, position: Int) {
        cursor!!.moveToPosition(position)
        holder.paciente=Pacientes.fromCursor(cursor!!)
    }

    override fun getItemCount(): Int {
        if (cursor == null) return 0

        return cursor!!.count
    }
}