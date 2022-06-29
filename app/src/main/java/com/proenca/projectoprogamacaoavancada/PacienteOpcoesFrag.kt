package com.proenca.projectoprogamacaoavancada

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.proenca.projectoprogamacaoavancada.databinding.FragmentPacientesOpcoesBinding

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class PacienteOpcoesFrag : Fragment() {

    private var _binding: FragmentPacientesOpcoesBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentPacientesOpcoesBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

   /*     binding.buttonAdicionaPaciente.setOnClickListener {
            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
        }*/
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}