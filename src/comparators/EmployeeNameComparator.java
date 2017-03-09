package comparators;

import java.util.Comparator;

import model.EmployeeModel;



public class EmployeeNameComparator implements Comparator<EmployeeModel> {

	public int compare(EmployeeModel emp1, EmployeeModel emp2) {
		return emp1.getEmpName().compareTo(emp2.getEmpName());
	}

}
