package comparators;

import java.util.Comparator;

import model.EmployeeModel;

public class EmployeeSalaryComparator implements Comparator<EmployeeModel> {

	public int compare(EmployeeModel emp1, EmployeeModel emp2) {
		
		if(emp1.getEmpSalary() > emp2.getEmpSalary()){
            return 1;
        } else {
            return -1;
        }


}
}

