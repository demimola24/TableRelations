package com.example.embeddedroomsample

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.embeddedroomsample.data.*
import com.example.embeddedroomsample.data.database.AppDatabase
import com.example.embeddedroomsample.databinding.FragmentFirstBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null
   private lateinit var mDb: AppDatabase

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       mDb = AppDatabase.getInstance(requireContext())
        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        saveTasks()
        binding.buttonFirst.setOnClickListener {
            retrieveTasks()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun saveTasks(){
        CoroutineScope(Dispatchers.IO).launch{ // do
            try {
                val salaryLow = Salary("1","111",40000,0)
                val salaryHigh = Salary("2","111",20,0)
                val branches  = mutableListOf(BranchEntity("3", "111","Branch","Branch Address"))
                val employees  = mutableListOf(EmployeeEntity("4", "111","Employee","Employee Address"))
                val logoItem = LogoEntity("8","111", "www","http")

                val fullDataOne = Company(branches,employees).apply {
                        id = "111"
                        companyName = "companyName"
                        companySlogan = "companySlogan"
                        companyMoto = "companyMoto"
                        highestSalary = salaryHigh
                        lowestSalary = salaryLow
                        logo = logoItem
                }

                mDb.companyDao().insertAllCompanyEntities(mutableListOf(fullDataOne))

            }catch (e: Exception){
                Log.d("room","e: $e")
            }
        }
    }

    private fun retrieveTasks() {
        CoroutineScope(Dispatchers.IO).launch{ // do
            try {
                val companyEntity: List<Company> = mDb.companyDao().getAllCompanies()
                Log.d("room","retrieveTasks: companyEntity ${companyEntity.size}")
                Log.d("room","retrieveTasks: lowestSalary ${companyEntity.first().lowestSalary.amount}")
                Log.d("room","retrieveTasks: highestSalary ${companyEntity.first().highestSalary.amount}")
                Log.d("room","retrieveTasks: employee ${companyEntity.first().employee}")
                Log.d("room","retrieveTasks: logo ${companyEntity.first().logo}")
            }catch (e: Exception){
                Log.d("room","e: $e")
            }
        }
    }
}