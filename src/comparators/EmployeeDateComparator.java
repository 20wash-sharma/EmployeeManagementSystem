package comparators;

import java.util.Comparator;

import model.EmployeeModel;

public class EmployeeDateComparator implements Comparator<EmployeeModel> {

	public int compare(EmployeeModel emp1, EmployeeModel emp2) {
		return emp1.getDateOfJoining().compareTo(emp2.getDateOfJoining());
	}

}
