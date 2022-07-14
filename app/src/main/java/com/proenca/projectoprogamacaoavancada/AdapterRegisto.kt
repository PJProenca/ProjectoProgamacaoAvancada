package com.proenca.projectoprogamacaoavancada

import android.database.Cursor
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat

class AdapterRegisto(val fragment: RegistoOpcoes) : RecyclerView.Adapter<AdapterRegisto.ViewHolderReg>(){

    var cursor:Cursor?=null
    get() = field
    set(value) {
        if (field!=value){
            field=value
            notifyDataSetChanged()
        }
    }

    inner class ViewHolderReg(itemRegisto: View) : RecyclerView.ViewHolder(itemRegisto),View.OnClickListener {
        val textViewRegId= itemRegisto.findViewById<TextView>(R.id.RegNumberView)
        val textViewRegData= itemRegisto.findViewById<TextView>(R.id.RegDataView)
        val textViewReNome= itemRegisto.findViewById<TextView>(R.id.RegNomePacienteView)
        val textViewRegPeso= itemRegisto.findViewById<TextView>(R.id.RegPesoPacienteView)
        val textViewRegGlicemia= itemRegisto.findViewById<TextView>(R.id.RegGlicemiaView)
        val textViewRegInsulina= itemRegisto.findViewById<TextView>(R.id.RegInsulinaView)

        init {
            itemRegisto.setOnClickListener(this)
        }

        var registo : Registos?=null
        get() = field
        set(value) {
            field=value
            textViewRegId.text=registo?.id.toString()
            val dateFormat = SimpleDateFormat("dd-MM-yyy")
            val dataReg = registo?.data_reg
            val data = dateFormat.format(dataReg)
            textViewRegData.text= data
            textViewReNome.text=registo?.paciente?.nome
            textViewRegPeso.text=registo?.peso.toString()
            textViewRegGlicemia.text = registo?.glicemia.toString()
            textViewRegInsulina.text = registo?.insulina.toString()


        }
        override fun onClick(p0: View?) {
            selec?.desseleciona()
            this.seleciona()
        }

        private fun seleciona() {
            selec = this
            fragment.registoSelec=registo
            itemView.setBackgroundResource(android.R.color.holo_blue_dark)
        }

        private fun desseleciona() {
            itemView.setBackgroundResource(android.R.color.darker_gray)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderReg {
        val itemRegisto =fragment.layoutInflater.inflate(R.layout.registo_item,parent,false)
        return ViewHolderReg(itemRegisto)
    }

    override fun onBindViewHolder(holder: ViewHolderReg, position: Int) {
        cursor!!.moveToPosition(position)
        holder.registo=Registos.fromCursor(cursor!!)
    }

    override fun getItemCount(): Int {
        if (cursor == null) return 0

        return cursor!!.count
    }

    companion object{
        var selec : ViewHolderReg?= null
    }
}