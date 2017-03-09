package controller;

import daoImpl.EmployeeDAOImpl;


public class EmployeeMain {

	public static void main(String[] args) {
		
		EmployeeDAOImpl employeeDAOImpl = new EmployeeDAOImpl();
		employeeDAOImpl.bootTheApplication();
		
		
		
	}

}
