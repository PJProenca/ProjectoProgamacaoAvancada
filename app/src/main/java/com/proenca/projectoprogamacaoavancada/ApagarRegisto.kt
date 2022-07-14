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
import com.proenca.projectoprogamacaoavancada.databinding.FragmentApagarRegistoBinding
import java.text.SimpleDateFormat


class ApagarRegisto : Fragment() {

    private var _binding:FragmentApagarRegistoBinding?=null
    private val binding get() = _binding!!
    private lateinit var registos: Registos

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding= FragmentApagarRegistoBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val activity =requireActivity() as MainActivity
        activity.fragment= this
        activity.itemAtual = R.menu.menu_apagar

        registos = ApagarRegistoArgs.fromBundle(arguments!!).registo
        binding.RegNumberView.text = registos.id.toString()
        val dateFormat = SimpleDateFormat("dd-MM-yyy")
        val dataReg = registos.data_reg
        val data = dateFormat.format((dataReg))
        binding.RegDataView.text = data
        binding.RegNomePacienteView.text=registos.paciente.nome
        binding.RegPesoPacienteView.text = registos.peso.toString()
        binding.RegGlicemiaView.text = registos.glicemia.toString()
        binding.RegInsulinaView.text = registos.insulina.toString()
    }

    fun processaOpcaoMenu(item: MenuItem): Boolean{
        return when (item.itemId){
            R.id.action_delete ->{
                confirmaApagarRegisto()
                true
            }
            R.id.action_cancel ->{
                voltarOpcoesRegisto()
                true
            }
            else ->false
        }
    }

    private fun voltarOpcoesRegisto() {
        val acao = ApagarRegistoDirections.actionApagarRegistoToRegistoOpcoes()
        findNavController().navigate(acao)
    }

    private fun confirmaApagarRegisto() {
        val alert =AlertDialog.Builder(requireContext())
        alert.setTitle(R.string.APAGAR_REGISTO_TITLE)
        alert.setMessage(R.string.ConfirmaApagaRegisto)
        alert.setNegativeButton(R.string.Cancel_apagar, DialogInterface.OnClickListener{dialog,which ->})
        alert.setPositiveButton(R.string.apagar_confirmacao, DialogInterface.OnClickListener { dialog, wich -> apagarRegisto() })
        alert.show()
    }

    private fun apagarRegisto() {
        val endereco = Uri.withAppendedPath(myContentProvider.ENDERECO_REGISTOS,"${registos.id}")
        val registoApagado = requireActivity().contentResolver.delete(endereco,null,null)

        if (registoApagado == 1){
            Toast.makeText(requireContext(),R.string.delete_sucess_registo,Toast.LENGTH_LONG).show()
            voltarOpcoesRegisto()
        }else{
            Snackbar.make(binding.RegNumberView,R.string.delete_error_registo,Snackbar.LENGTH_INDEFINITE).show()
        }
    }

}