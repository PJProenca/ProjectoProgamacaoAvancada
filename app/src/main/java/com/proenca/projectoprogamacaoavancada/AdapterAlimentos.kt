package com.proenca.projectoprogamacaoavancada

import android.database.Cursor
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class AdapterAlimentos(val fragment: alimentos_opcoes): RecyclerView.Adapter<AdapterAlimentos.ViewHolderAlimentos>(){

    var cursor:Cursor?=null
        get() = field
        set(value) {
            if (field!=value){
                field=value
                notifyDataSetChanged()
            }
        }
    inner class ViewHolderAlimentos(itemAlimentos: View):RecyclerView.ViewHolder(itemAlimentos),View.OnClickListener {
        val textViewNomeAlimento = itemAlimentos.findViewById<TextView>(R.id.textViewNomeAlimento)
        val textViewHidratos = itemAlimentos.findViewById<TextView>(R.id.textViewValorHidratos)
        init {
            itemAlimentos.setOnClickListener(this)
        }
        var alimento : Alimentos? =null

            get() =  field
            set(value) {

                field=value
                textViewNomeAlimento.text = alimento?.nome?:""
                textViewHidratos.text=alimento?.hidratos?.toString()

            }

        override fun onClick(p0: View?) {
            selec?.desseleciona()
            this.seleciona()
        }
        private fun seleciona() {
            selec = this
            fragment.alimentoSelect = alimento
            itemView.setBackgroundResource(android.R.color.holo_blue_dark)
        }

        private fun desseleciona() {
            itemView.setBackgroundResource(android.R.color.darker_gray)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderAlimentos {
        val itemAlimentos =fragment.layoutInflater.inflate(R.layout.alimento_item,parent,false)
        return ViewHolderAlimentos(itemAlimentos)
    }

    override fun onBindViewHolder(holder: ViewHolderAlimentos, position: Int) {
        cursor!!.moveToPosition(position)
        holder.alimento = Alimentos.fromCursor(cursor!!)
    }

    override fun getItemCount(): Int {
        if (cursor == null) return 0

        return cursor!!.count
    }

    companion object{
        var selec : AdapterAlimentos.ViewHolderAlimentos?= null
    }
}