package com.proenca.projectoprogamacaoavancada

import android.database.Cursor
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat

class AdapaterPacientes(val fragment: PacienteOpcoesFrag) : RecyclerView.Adapter<AdapaterPacientes.ViewHolderPacientes>() {

    var cursor:Cursor? =null
    get() = field
    set(value) {
        if (field != value){
            field=value
            notifyDataSetChanged()
        }
    }

    inner class ViewHolderPacientes(itemPacientes: View) : RecyclerView.ViewHolder(itemPacientes), View.OnClickListener{
            val textViewNome = itemPacientes.findViewById<TextView>(R.id.textViewNomePaciente)
            val textViewDataNasc = itemPacientes.findViewById<TextView>(R.id.textViewDataNasc)
            val textViewAltura = itemPacientes.findViewById<TextView>(R.id.textViewAltura)

                init {
                    itemPacientes.setOnClickListener(this)
                }
            var paciente : Pacientes? = null
            get()= field
            set(value) {
                field=value
                textViewNome.text = paciente?.nome?: ""
                textViewAltura.text = paciente?.altura.toString()
                val dateFormat = SimpleDateFormat("dd-MM-yyy")
                val dataNasc = paciente?.dataNasc
                val data= dateFormat.format(dataNasc)
                textViewDataNasc.text = data
            }

        override fun onClick(p0: View?) {
            selec?.desseleciona()
            this.seleciona()

        }

        private fun seleciona() {
            selec = this
            fragment.pacienteSelec = paciente
            itemView.setBackgroundResource(android.R.color.holo_blue_dark)
        }

        private fun desseleciona() {
            itemView.setBackgroundResource(android.R.color.darker_gray)
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
    companion object{
        var selec : ViewHolderPacientes?= null
    }

}