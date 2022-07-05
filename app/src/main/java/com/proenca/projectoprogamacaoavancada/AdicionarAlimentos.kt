package com.proenca.projectoprogamacaoavancada

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.proenca.projectoprogamacaoavancada.databinding.FragmentAdicionarAlimentosBinding
import java.util.*


/**
 * A simple [Fragment] subclass.
 * Use the [AdicionarAlimentos.newInstance] factory method to
 * create an instance of this fragment.
 */
class AdicionarAlimentos : Fragment() {



    private var _binding: FragmentAdicionarAlimentosBinding?=null

    private val binding get() = _binding!!



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val activity = requireActivity() as MainActivity
        activity.fragment = this
        activity.itemAtual = R.menu.menu_guardar
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun processaOpcaoMenu(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_save -> {
                guardarAlimento()
                true
            }
            R.id.action_cancel -> {
                voltarOpcoesAlimentos()
                true
            }
            else -> false
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAdicionarAlimentosBinding.inflate(inflater,container,false)
        return binding.root
    }

    private fun voltarOpcoesAlimentos() {
        findNavController().navigate(R.id.action_adicionarAlimentos_to_alimentos_opcoes)
    }

    private fun guardarAlimento() {
        val nome = binding.editTextAlimento.text.toString()
        if (nome.isBlank()) {
            binding.editTextAlimento.error = getString(R.string.campoObrigatorio)
            binding.editTextAlimento.requestFocus()
            return
        }

        val hidratos = binding.editTextHidratos.text.toString()
        if (hidratos.isBlank()) {
            binding.editTextHidratos.error = getString(R.string.campoObrigatorio)
            binding.editTextHidratos.requestFocus()
            return
        }


        val alimento = Alimentos(nome, hidratos.toLong())


        val endereco = requireActivity().contentResolver.insert(
            myContentProvider.ENDERECO_ALIMENTOS,
            alimento.toContentValues()
        )

        if (endereco != null ){
            Toast.makeText(requireContext(),R.string.AlimentadoGuardado, Toast.LENGTH_LONG).show()
            voltarOpcoesAlimentos()
        }else{
            Snackbar.make(binding.editTextAlimento,R.string.ErroGuardarAlimento, Snackbar.LENGTH_INDEFINITE).show()
        }

    }
}