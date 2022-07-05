package com.proenca.projectoprogamacaoavancada

import android.app.AlertDialog
import android.content.DialogInterface
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.proenca.projectoprogamacaoavancada.databinding.FragmentApagarAlimentoBinding


/**
 * A simple [Fragment] subclass.
 * Use the [ApagarAlimento.newInstance] factory method to
 * create an instance of this fragment.
 */
class ApagarAlimento : Fragment() {

    private var _binding: FragmentApagarAlimentoBinding? = null
    private val binding get() = _binding!!

    private lateinit var alimento: Alimentos

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val activity = requireActivity() as MainActivity
        activity.fragment = this
        activity.itemAtual = R.menu.menu_apagar

        alimento = ApagarAlimentoArgs.fromBundle(arguments!!).alimento
        binding.textViewAlimentoApagar.text= alimento.nome
        binding.textViewAlimentoHidratoApagar.text=alimento.hidratos.toString()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentApagarAlimentoBinding.inflate(inflater, container, false)
        return binding.root
    }
    fun processaOpcaoMenu(item: MenuItem): Boolean{
        return when (item.itemId){
            R.id.action_delete ->{
                confirmaApagarAlimento()
                true
            }
            R.id.action_cancel ->{
                voltarOpcoesAlimentos()
                true
            }
            else ->false
        }
    }

    private fun confirmaApagarAlimento(){
        val alert = AlertDialog.Builder(requireContext())
        alert.setTitle(R.string.ApagarAlimentoTitulo)
        alert.setMessage(R.string.ConfirmaApagaAlimento)
        alert.setNegativeButton(R.string.Cancel_apagar,
            DialogInterface.OnClickListener { dialog, wich ->  })
        alert.setPositiveButton(R.string.apagar_confirmacao,
            DialogInterface.OnClickListener { dialog, wich -> apagarAlimento()  })
        alert.show()
    }

    private fun apagarAlimento() {
        val endereco= Uri.withAppendedPath(myContentProvider.ENDERECO_ALIMENTOS, "${alimento.id}")
        val AlimentoApagado = requireActivity().contentResolver.delete(endereco,null,null)

        if(AlimentoApagado == 1){
            Toast.makeText(requireContext(),R.string.AlimentoApagadoSucesso, Toast.LENGTH_LONG).show()
            voltarOpcoesAlimentos()
        }else{
            Snackbar.make(binding.textAlimentoApaga,R.string.AlimentoApagadoErro, Snackbar.LENGTH_INDEFINITE).show()
        }

    }

    private fun voltarOpcoesAlimentos() {
        val acao = ApagarAlimentoDirections.actionApagarAlimentoToAlimentosOpcoes()
        findNavController().navigate(acao)
    }

}