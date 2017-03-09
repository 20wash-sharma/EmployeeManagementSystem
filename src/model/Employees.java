package model;

import java.util.ArrayList;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "employees")
@XmlAccessorType (XmlAccessType.FIELD)
public class Employees 
{
    @XmlElement(name = "employeeModel")
    private ArrayList<EmployeeModel> employees = null;
 
    public ArrayList<EmployeeModel> getEmployees() {
        return employees;
    }
 
    public void setEmployees(ArrayList<EmployeeModel> employees) {
        this.employees = employees;
    }
}
