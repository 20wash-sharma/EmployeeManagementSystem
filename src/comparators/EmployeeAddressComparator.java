package comparators;

import java.util.Comparator;

import model.EmployeeModel;

public class EmployeeAddressComparator implements Comparator<EmployeeModel> {

	public int compare(EmployeeModel emp1, EmployeeModel emp2) {
		return emp1.getEmpAddress().compareTo(emp2.getEmpAddress());
	}

}
