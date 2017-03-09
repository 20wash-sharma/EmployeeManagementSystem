package comparators;

import java.util.Comparator;

import model.EmployeeModel;

public class EmployeeIdComparator implements Comparator<EmployeeModel> {

	public int compare(EmployeeModel emp1, EmployeeModel emp2) {
		
		if(emp1.getEmpId() > emp2.getEmpId()){
            return 1;
        } else {
            return -1;
        }


}
}
