package com.example.embeddedroomsample.data.dao

import android.util.Log
import androidx.room.*
import com.example.embeddedroomsample.data.BranchEntity
import com.example.embeddedroomsample.data.Company
import com.example.embeddedroomsample.data.CompanyEntity
import com.example.embeddedroomsample.data.EmployeeEntity

@Dao
interface CompanyDao {

    @Query("SELECT * FROM company_table")
    //@Query("SELECT * FROM employee,salary,logo  join company_table on  company_table.id = employee.parentId ")
    suspend fun getAllCompanies() : List<Company>

    //Hidden from external classes

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(companyEntities: CompanyEntity) : Long

    //Hidden from external classes
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEmployeeEntities(data: List<EmployeeEntity>)

    //Hidden from external classes
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEmployeeBranch(data: List<BranchEntity>)

    suspend fun insertAllCompanyEntities(companyEntities: List<Company>){
        for (company in companyEntities){
            insertCompanyEntity(company)
        }
    }
    @Transaction
     suspend fun insertCompanyEntity(data: Company){
            insert(data)
            data.branches.forEach { it.parentId = data.id };
            insertEmployeeBranch(data.branches)
            data.employee.forEach { it.parentId = data.id };
            insertEmployeeEntities(data.employee)
    }
}