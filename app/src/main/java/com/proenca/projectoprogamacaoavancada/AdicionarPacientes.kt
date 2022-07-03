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
import com.proenca.projectoprogamacaoavancada.databinding.FragmentAdicionarPacientesBinding
import java.text.SimpleDateFormat
import java.util.*


class AdicionarPacientes : Fragment() {
    private var _binding: FragmentAdicionarPacientesBinding? = null

    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAdicionarPacientesBinding.inflate(inflater,container,false)
        return binding.root
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val activity = activity as MainActivity
        activity.fragment = this
        activity.itemAtual = R.menu.menu_guardar
    }

    fun processaOpcaoMenu(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_save -> {
                guardarPaciente()
                true
            }
            R.id.action_cancel -> {
                voltarOpcoesPacientes()
                true
            }
            else -> false
        }
    }

    private fun voltarOpcoesPacientes() {
        findNavController().navigate(R.id.action_adicionarPacientes_to_PacienteOpcoesFrag)
    }

    private fun guardarPaciente() {
        val nome = binding.editTextNomePaciente.text.toString()
        if (nome.isBlank()) {
            binding.editTextNomePaciente.error = getString(R.string.campoObrigatorio)
            binding.editTextNomePaciente.requestFocus()
            return
        }

        val altura = binding.editTextAltura.text.toString()
        if (altura.isBlank()) {
            binding.editTextAltura.error = getString(R.string.campoObrigatorio)
            binding.editTextAltura.requestFocus()
            return
        }

        var calendarView = binding.calendarView
        var dataNasc = calendarView.date
            calendarView.setOnDateChangeListener { calendarView, ano, mes, dia ->

            val data = Calendar.getInstance()
            data.set(ano,mes,dia)
            dataNasc= data.timeInMillis
        }
        val dateFormat = SimpleDateFormat("dd-MM-yyy")

        val data = dateFormat.format(dataNasc)
        val paciente = Pacientes(nome, data, altura.toLong())


        val endereco = requireActivity().contentResolver.insert(
            myContentProvider.ENDERECO_PACIENTES,
            paciente.toContentValues()
        )

        if (endereco != null ){
            Toast.makeText(requireContext(),"Paciente adicionado com Sucesso",Toast.LENGTH_LONG).show()
            voltarOpcoesPacientes()
        }else{
            Snackbar.make(binding.editTextNomePaciente,"Erro ao adicionar Paciente",Snackbar.LENGTH_INDEFINITE).show()
        }

    }

}