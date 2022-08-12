package com.example.embeddedroomsample.data
import androidx.room.*
import androidx.room.ForeignKey.CASCADE

data class Company (

    @Relation(parentColumn = "id", entityColumn = "parentId")
    var branches: List<BranchEntity>,

    @Relation(parentColumn = "id", entityColumn = "parentId")
    var employee: List<EmployeeEntity>,


    ) : CompanyEntity()


@Entity(tableName = "company_table")
open class CompanyEntity(
    @PrimaryKey
    var id: String,
    var companyName: String,
    var companyMoto: String,
    var companySlogan: String,

    @Embedded(prefix = "highest")
    var highestSalary: Salary,

    @Embedded(prefix = "lowest")
    var lowestSalary: Salary,

    @Embedded(prefix = "logo")
    var logo: LogoEntity,

) {
    constructor() : this("","","","",Salary(),Salary(),LogoEntity())
}


//Logo Entity
@Entity(tableName = "logo")
class LogoEntity(
    @PrimaryKey val id: String,
    val parentId: String,
    val privateUrl: String,
    val publicUrl: String
){
    constructor() : this("","","","")
}

//Salary Entity
@Entity(tableName = "salary")
data class Salary(@PrimaryKey val id: String,
                  val parentId: String,
                  val amount: Int,
                  val bonus: Int
){
    constructor() : this("","",0,0)
}



//Relational Branch Entity
@Entity(tableName = "branch")
class BranchEntity(@PrimaryKey val id: String,
                   var parentId: String,
                   val name: String,
                   val address: String
)

//Relational Employee Entity
@Entity(tableName = "employee")
class EmployeeEntity(@PrimaryKey val id: String,
                     var parentId: String,
                     val name: String,
                     val age: String
)

