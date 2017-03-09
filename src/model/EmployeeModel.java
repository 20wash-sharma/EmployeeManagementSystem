package model;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlAccessType;
@XmlRootElement(name = "employeeModel")
@XmlAccessorType (XmlAccessType.FIELD)
public class EmployeeModel implements Serializable {
/**
	 * 
	 */
	private static final long serialVersionUID = 2487824484382437980L;
private int empId;
private String empName;
private double empSalary;
private String empAddress;
private Date dateOfJoining;

public EmployeeModel(){
	
	
}
public EmployeeModel(int empId,String empName,double empSalary,String empAddress, Date dateOfJoining){
	this.empId=empId;
	this.empName=empName;
	this.empSalary=empSalary;
	this.empAddress=empAddress;
	this.dateOfJoining=dateOfJoining;
	
}

public Date getDateOfJoining() {
	return dateOfJoining;
}

public void setDateOfJoining(Date dateOfJoining) {
	this.dateOfJoining = dateOfJoining;
}

public int getEmpId() {
	return empId;
}
public void setEmpId(int empId) {
	this.empId = empId;
}
public String getEmpName() {
	return empName;
}
public void setEmpName(String empName) {
	this.empName = empName;
}
public double getEmpSalary() {
	return empSalary;
}
public void setEmpSalary(double empSalary) {
	this.empSalary = empSalary;
}
public String getEmpAddress() {
	return empAddress;
}
public void setEmpAddress(String empAddress) {
	this.empAddress = empAddress;
}
public String toString(){
	return "Employee ID:"+empId+"\n"+"Employee Name:"+empName+"\n"+"Employee Address:"+empAddress+"\nEmployee Salary:"+empSalary+"\nEmployee Date of Joining:"+dateOfJoining;
}

}
