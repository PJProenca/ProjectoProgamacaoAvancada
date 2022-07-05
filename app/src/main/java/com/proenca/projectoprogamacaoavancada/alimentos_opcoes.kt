package com.proenca.projectoprogamacaoavancada

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.proenca.projectoprogamacaoavancada.databinding.FragmentAlimentosOpcoesBinding


class alimentos_opcoes : Fragment() {
   var alimentos: Alimentos? = null

        get() = field
        set(value) {
            if (value!=field){
                field=value
                (requireActivity() as MainActivity).atualizaOpcoes(field!=null)
            }
        }

    private val _binding: FragmentAlimentosOpcoesBinding?=null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_alimentos_opcoes, container, false)
    }


}