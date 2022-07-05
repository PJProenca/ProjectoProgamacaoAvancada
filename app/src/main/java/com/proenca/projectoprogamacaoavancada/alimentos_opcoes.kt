package com.proenca.projectoprogamacaoavancada

import android.database.Cursor
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.loader.app.LoaderManager
import androidx.loader.content.CursorLoader
import androidx.loader.content.Loader
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.proenca.projectoprogamacaoavancada.databinding.FragmentAlimentosOpcoesBinding


class alimentos_opcoes : Fragment() , LoaderManager.LoaderCallbacks<Cursor>{

    private var _binding: FragmentAlimentosOpcoesBinding?=null
    private var adapterAlimentos: AdapterAlimentos?=null

    private val binding get() = _binding!!

    var alimentoSelect:Alimentos ?=null
        get() = field
        set(value) {
            if (value!=field){
                field=value
                (requireActivity() as MainActivity).atualizaOpcoes(field!=null)
            }
        }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        LoaderManager.getInstance(this).initLoader(ID_LOADER_ALIMENTOS, null, this)

        adapterAlimentos= AdapterAlimentos(this)
        binding.recyclerViewAlimentos.adapter=adapterAlimentos
        binding.recyclerViewAlimentos.layoutManager = LinearLayoutManager(requireContext())
        val activity = requireActivity() as MainActivity
        activity.fragment = this
        activity.itemAtual = R.menu.menu_barra
    }


    fun processaOpcaoMenu(item: MenuItem): Boolean{
        return when(item.itemId){
            R.id.action_add -> {
                findNavController().navigate(R.id.action_alimentos_opcoes_to_adicionarAlimentos)
                return true
            }
            R.id.action_edit ->true
            R.id.action_delete ->true
            else -> false
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentAlimentosOpcoesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreateLoader(id: Int, args: Bundle?): Loader<Cursor> =
        CursorLoader(requireContext(),
            myContentProvider.ENDERECO_ALIMENTOS,
            TabelaAlimentos.TODAS_COLUNAS,
            null,
            null,
            TabelaAlimentos.C_NOME)


    override fun onLoadFinished(loader: Loader<Cursor>, data: Cursor?) {
        adapterAlimentos!!.cursor = data
    }

    override fun onLoaderReset(loader: Loader<Cursor>) {
        adapterAlimentos!!.cursor= null
    }

    companion object{
        const val ID_LOADER_ALIMENTOS = 0
    }


}