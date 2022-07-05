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
import com.proenca.projectoprogamacaoavancada.databinding.FragmentAdicionaEditaPacientesBinding
import java.text.SimpleDateFormat
import java.util.*


class AdicionaEditaPacientes : Fragment() {
    private var _binding: FragmentAdicionaEditaPacientesBinding? = null

    private val binding get() = _binding!!

    private var paciente : Pacientes?= null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAdicionaEditaPacientesBinding.inflate(inflater,container,false)
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
        if(arguments!=null){
            paciente = AdicionaEditaPacientesArgs.fromBundle(arguments!!).paciente
            if(paciente != null){
                binding.editTextNomePaciente.setText(paciente!!.nome)
                binding.editTextAltura.setText(paciente!!.altura.toString())
            }
        }

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
        findNavController().navigate(R.id.action_AdicionaEditaPacientes_to_PacienteOpcoesFrag)
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

        val calendarView = binding.calendarView
        var dataNasc = calendarView.date
            calendarView.setOnDateChangeListener { calendarView, ano, mes, dia ->

            val data = Calendar.getInstance()
            data.set(ano,mes,dia)
            dataNasc= data.timeInMillis
        }
        val dateFormat = SimpleDateFormat("dd-MM-yyy")

        val data = dateFormat.format(dataNasc)
        val paciente = Pacientes(nome, data, altura.toLong())
        val pacienteAdicionado =
            if(paciente == null){
                adicionaPaciente(nome,data,altura)
            }else{
                editaPaciente(nome,data,altura)
            }

        val endereco = requireActivity().contentResolver.insert(
            myContentProvider.ENDERECO_PACIENTES,
            paciente.toContentValues()
        )

        if (endereco != null ){
            Toast.makeText(requireContext(),getString(R.string.PacienteaAddSucesso),Toast.LENGTH_LONG).show()
            voltarOpcoesPacientes()
        }else{
            Snackbar.make(binding.editTextNomePaciente,getString(R.string.PacienteAddErro),Snackbar.LENGTH_INDEFINITE).show()
        }

    }

    private fun adicionaPaciente(nome: String, data: String, altura: String): Boolean {
        val paciente = Pacientes(nome,data,altura.toLong())
        val endereco = requireActivity().contentResolver.insert(myContentProvider.ENDERECO_PACIENTES,paciente.toContentValues())
        return endereco !=null
    }

    private fun editaPaciente(nome: String, data: String, altura: String): Any {
        val paciente = Pacientes(nome,data,altura.toLong())
        val endereco = requireActivity().contentResolver.update(myContentProvider.ENDERECO_PACIENTES,paciente.toContentValues(),null,null)
        return  endereco == 1
    }
}