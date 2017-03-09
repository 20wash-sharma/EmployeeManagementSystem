package daoImpl;

import helper.CSVUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.Scanner;



import javax.sql.RowSetMetaData;
import javax.sql.rowset.JdbcRowSet;
import javax.sql.rowset.RowSetProvider;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

























import com.mysql.jdbc.Connection;

import comparators.EmployeeAddressComparator;
import comparators.EmployeeDateComparator;
import comparators.EmployeeIdComparator;
import comparators.EmployeeNameComparator;
import comparators.EmployeeSalaryComparator;
import model.EmployeeModel;
import model.Employees;
import dao.EmployeeDAO;

public class EmployeeDAOImpl implements EmployeeDAO {

	ArrayList <EmployeeModel> employeeList = new ArrayList <EmployeeModel>() ;
	Scanner input = new Scanner(System.in);
	EmployeeModel employeeModel;
	private static String db_driverClass="com.mysql.jdbc.Driver";
	private static String db_url="jdbc:mysql://localhost:3306/bishwash?autoReconnect=true&useSSL=false";
	private static String db_userName="root";
	private static String db_password="password";
	java.sql.Connection connection=null;
	PreparedStatement pstatement= null;
	ResultSet resultSet = null;
	public EmployeeDAOImpl(){
		loadDataFromFile();
		
	}
	public void addEmployee() {
		
		//employeeList.add(employee);
		System.out.println("enter employee name");
		String empName= input.next();
		System.out.println("enter employee salary");
		Double empSalary= Double.parseDouble(input.next());
		System.out.println("enter employee Address");
		String empAddress= input.next();
		System.out.println("enter employee date of joining using the format: dd/MM/yyyy ");
		
		Date date= new Date();
		try {
			date = new SimpleDateFormat("dd/MM/yyyy").parse(input.next());
		} catch (ParseException e) {
			
			e.printStackTrace();
		}
		int nextId=1;
		if(employeeList.size()!=0)
		{
		nextId=	Collections.max(employeeList, new EmployeeIdComparator()).getEmpId()+1;
		}
		employeeModel= new EmployeeModel(nextId,empName,empSalary,empAddress,date);
		//employeeModel= new EmployeeModel(employeeList.size()+1,empName,empSalary,empAddress);
		employeeList.add(employeeModel);
		saveEmployeetoFile();
		System.out.println("add successful");
		
	}

	public void updateEmployee() {
		System.out.println("enter employee id to update");
		int empId= Integer.parseInt(input.next());
		int flagFound=0,flagWrongCode=0;
		for(int i=0;i<employeeList.size();i++)
		{
			if(employeeList.get(i).getEmpId()==empId)
			{
				flagFound=1;
				String empName=employeeList.get(i).getEmpName();
				Double empSalary=employeeList.get(i).getEmpSalary();
				String empAddress=employeeList.get(i).getEmpAddress();
				Date date= employeeList.get(i).getDateOfJoining();
				System.out.println("Enter the following code to perform the respective operations");
				System.out.println("1: To update name");
				System.out.println("2: To update address");
				System.out.println("3: To update salary");
				System.out.println("4: To update date of joining");
				System.out.println ("Enter code:(1-4)");
			 String flag= input.next();
			 switch (flag) {
			   case "1":
				    System.out.println("enter employee name ");
					 empName= input.next();
			        break;
			  case "2":
				  System.out.println("enter employee Address");
					empAddress= input.next();
					break;
			  case "3":
				 
					 System.out.println("enter employee salary");
					 empSalary= Double.parseDouble(input.next());
			        break;
			  case "4":
				  System.out.println("enter employee date of joining using the format: dd/MM/yyyy ");
					try {
						date = new SimpleDateFormat("dd/MM/yyyy").parse(input.next());
					} catch (ParseException e) {
						
						e.printStackTrace();
					}
			        break;
			 
			  default:
				  flagWrongCode=1;
			      System.out.println("Wrong code. Please try again");
			        break;
			}
			
				
				
				
				
				if(flagWrongCode==0)
				{
					employeeModel= new EmployeeModel(empId,empName,empSalary,empAddress,date);
					employeeList.set(i, employeeModel);
					saveEmployeetoFile();
					System.out.println("Update successful");	
				}
				
				
				break;
			
			}//if id is found
		}
		if(flagFound==0&&flagWrongCode!=1)
		{
				
			System.out.println("not found");
			
		}//if id is found
		
		
	}

	
	public void bootTheApplication(){
		
		
		
		String flag= "Y";
		 while (!flag.equals("0")) {
			 System.out.println("Employee Application;");
				System.out.println("Enter the following code to perform the respective operations");
				System.out.println("1: To add employees");
				System.out.println("2: To delete employees");
				System.out.println("3: To update employees");
				System.out.println("4: To show all employees");
				System.out.println("5: To find Max Salary");
				System.out.println("6: To find Min Salary");
				System.out.println("7: To find Average Salary");
				System.out.println("8: Sort");
				System.out.println("9: To save employee info to xml");
				System.out.println("10: To display employee info from xml");
				System.out.println("11: To save employee info from xml into excel");
				System.out.println("12: To save employee info into a csv");
				System.out.println("13: To display employee info from a csv");
				System.out.println("14: To perform database operations");
				
				System.out.println("0: To exit the system");
			 System.out.println ("Enter code:(0-11)");
			 flag= input.next();
			 switch (flag) {
			 case "0":
				  	System.exit(0);
			        break;
			  case "1":
				  	addEmployee();
			        break;
			  case "2":
				  removeEmployee();
			        break;
			  case "3":
				  	updateEmployee();
			        break;
			  case "4":
				  	showEmployee();
			        break;
			  case "5":
				  maxEmployeeSalary();
			        break;
			  case "6":
				  minEmployeeSalary();
			        break;
			  case "7":
				  avgEmployeeSalary();
			        break;
			  case "8":
				  sortEmployee();
			        break;
			  case "9":
				  saveEmployeeInfoInAXML();
			        break;
			  case "10":
				  loadEmployeeInfoFromAXML();
			        break;
			  case "11":
				  saveEmployeeInfoFromAXMLIntoExcel();
			        break;
			  case "12":
				  saveEmployeeInfoIntoACSV();
			        break;
			  case "13":
				  displayEmployeeInfoFromACSV();
			        break;
			  case "14":
				  loadDatabaseOperation();
			        break;
			   default:
			      System.out.println("Wrong code. Please try again");
			        break;
			}
			
	          
	        }
	}
	
	private void loadDatabaseOperation() {
		System.out.println("Enter username");
		String userName= input.next();
		System.out.println("enter password");
		String password= input.next();
		
		int flagUserExists =0;
		try{
			Class.forName(db_driverClass);
			connection=DriverManager.getConnection(db_url,db_userName,db_password);
			String selectData="select count(*)  from login where username=? and password =?";
			pstatement=connection.prepareStatement(selectData);
			
			pstatement.setString(1, userName);
			pstatement.setString(2, password);
			resultSet=pstatement.executeQuery();
			
			while(resultSet.next())
			{
				flagUserExists=resultSet.getInt(1);
			}
		}catch(Exception exception)
		{
			exception.printStackTrace();
		}
		finally{
			if (resultSet != null) {
		        try {
		        	resultSet.close();
		        } catch (SQLException e) { /* ignored */}
		    }
		    if (pstatement != null) {
		        try {
		        	pstatement.close();
		        } catch (SQLException e) { /* ignored */}
		    }
		    if (connection != null) {
		        try {
		        	connection.close();
		        } catch (SQLException e) { /* ignored */}
		    }
		}
		if(flagUserExists==1)
		{
		String flag= "Y";
		 while (!flag.equals("0")) {
		System.out.println("Enter the following code to perform the respective operations");
		System.out.println("1: To add employee to database");
		System.out.println("2: To delete employee from database");
		System.out.println("3: To update employee from database");
		System.out.println("4: To find max salary");
		System.out.println("5: To find min salary");
		System.out.println("6: To count the no of employees");
		System.out.println("7: To find the average salary");
		System.out.println("8: To show all employees info");
		System.out.println("9:To add employees from excel");
		System.out.println("10:To delete all employees from database");
		System.out.println("11:To get database Metadata");
		System.out.println("12:To show employees using rowset");
		System.out.println("13:To show Join operations");
		System.out.println("0: To exit");
		 System.out.println ("Enter code:(0-13)");
		 flag= input.next();
		 switch (flag) {
		 case "0":
			  
			break;
		   case "1":
			  
				addEmployeesToDataBase();
		        break;
		  case "2":
			  deleteEmployeesFromDataBase();
				break;
		  case "3":
			  updateEmployeesFromDataBase();
				break;
		  case "4":
			  findMaxSalaryFromDataBase();
				break;
		  case "5":
			  findMinSalaryFromDataBase();
				break;
		  case "6":
			  countNoOfEmployeesFromDataBase();
				break;
		  case "7":
			  findAverageSalaryFromDataBase();
				break;
		  case "8":
			  showEmployeesFromDataBase();
				break;
		  case "9":
			  addEmployeesFromExcelSheetToDatabase();
				break;
		  case "10":
			  deleteAllFromDatabase();
				break;
		  case "11":
			  getDatabaseMetaData();
				break;
		  case "12":
			  getEmployeesUsingRowSet();
				break;
		  case "13":
			  showJoinOperations();
				break;
		 
		  	  default:
			  System.out.println("Wrong code. Please try again");
		        break;
		}
		 }
		}else {
			System.out.println("login failed");
		}
		
	}
	private void showJoinOperations() {
		try{
			JdbcRowSet rowSet = RowSetProvider.newFactory().createJdbcRowSet();
			rowSet.setUsername(db_userName);
			rowSet.setPassword(db_password);
			rowSet.setUrl(db_url);
			String selectData= "select * from user";
			
			rowSet.setCommand(selectData);
			rowSet.execute();
			ResultSetMetaData metaData = rowSet.getMetaData();
            int noOfCols = metaData.getColumnCount();
            System.out.println("User");

            for (int i = 1; i <= noOfCols; i++) {
                System.out.printf("%-10s\t", metaData.getColumnName(i));
            }
            System.out.println();
			while(rowSet.next())
			{
				for (int i = 1; i <= noOfCols; i++) {
                    System.out.printf("%-10s\t", rowSet.getObject(i));
                }
                System.out.println();		
             }
          selectData= "select * from course";
			
			rowSet.setCommand(selectData);
			rowSet.execute();
			 metaData = rowSet.getMetaData();
           noOfCols = metaData.getColumnCount();
            System.out.println("Course");

            for (int i = 1; i <= noOfCols; i++) {
                System.out.printf("%-10s\t", metaData.getColumnName(i));
            }
            System.out.println("Inner Join/Join");
			while(rowSet.next())
			{
				for (int i = 1; i <= noOfCols; i++) {
                    System.out.printf("%-10s\t", rowSet.getObject(i));
                }
                System.out.println();		
             }
			selectData= "select u.name, c.name from user u inner join course c on u.course_id =c.id;";
			
			rowSet.setCommand(selectData);
			rowSet.execute();
			 metaData = rowSet.getMetaData();
           noOfCols = metaData.getColumnCount();
            System.out.println("Inner Join");

            for (int i = 1; i <= noOfCols; i++) {
                System.out.printf("%-10s\t", metaData.getColumnName(i));
            }
            System.out.println();
			while(rowSet.next())
			{
				for (int i = 1; i <= noOfCols; i++) {
                    System.out.printf("%-10s\t", rowSet.getObject(i));
                }
                System.out.println();		
             }
selectData= "select u.name, c.name from user u left join course c on u.course_id =c.id;";
			
			rowSet.setCommand(selectData);
			rowSet.execute();
			 metaData = rowSet.getMetaData();
           noOfCols = metaData.getColumnCount();
           System.out.println("");
            System.out.println("left Join");

            for (int i = 1; i <= noOfCols; i++) {
                System.out.printf("%-10s\t", metaData.getColumnName(i));
            }
            System.out.println();
			while(rowSet.next())
			{
				for (int i = 1; i <= noOfCols; i++) {
                    System.out.printf("%-10s\t", rowSet.getObject(i));
                }
                System.out.println();		
             }
selectData= "select u.name, c.name from user u right join course c on u.course_id =c.id;";
			
			rowSet.setCommand(selectData);
			rowSet.execute();
			 metaData = rowSet.getMetaData();
           noOfCols = metaData.getColumnCount();
           System.out.println("");
            System.out.println("Right Join");

            for (int i = 1; i <= noOfCols; i++) {
                System.out.printf("%-10s\t", metaData.getColumnName(i));
            }
            System.out.println();
			while(rowSet.next())
			{
				for (int i = 1; i <= noOfCols; i++) {
                    System.out.printf("%-10s\t", rowSet.getObject(i));
                }
                System.out.println();		
             }
selectData= "select count(u.name), c.name from user u right join course c on u.course_id =c.id group by c.id;";
			
			rowSet.setCommand(selectData);
			rowSet.execute();
			 metaData = rowSet.getMetaData();
           noOfCols = metaData.getColumnCount();
           System.out.println("");
            System.out.println("Right Join with group by");

            for (int i = 1; i <= noOfCols; i++) {
                System.out.printf("%-10s\t", metaData.getColumnName(i));
            }
            System.out.println();
			while(rowSet.next())
			{
				for (int i = 1; i <= noOfCols; i++) {
                    System.out.printf("%-10s\t", rowSet.getObject(i));
                }
                System.out.println();		
             }
 selectData= "SELECT user.name, course.name FROM `user` LEFT JOIN `course` on user.course_id = course.id UNION SELECT user.name, course.name FROM `user` RIGHT JOIN `course` on user.course_id = course.id;";
			
			rowSet.setCommand(selectData);
			rowSet.execute();
			 metaData = rowSet.getMetaData();
           noOfCols = metaData.getColumnCount();
           System.out.println("");
            System.out.println("full outer join");

            for (int i = 1; i <= noOfCols; i++) {
                System.out.printf("%-10s\t", metaData.getColumnName(i));
            }
            System.out.println();
			while(rowSet.next())
			{
				for (int i = 1; i <= noOfCols; i++) {
                    System.out.printf("%-10s\t", rowSet.getObject(i));
                }
                System.out.println();		
             }
		}catch(Exception exception)
		{
			exception.printStackTrace();
		}
		finally{
			
		}
		
	}
	private void getEmployeesUsingRowSet() {
		try{
			JdbcRowSet rowSet = RowSetProvider.newFactory().createJdbcRowSet();
			rowSet.setUsername(db_userName);
			rowSet.setPassword(db_password);
			rowSet.setUrl(db_url);
			String selectData= "select * from employees";
			
			rowSet.setCommand(selectData);
			rowSet.execute();
			ResultSetMetaData metaData = rowSet.getMetaData();
            int noOfCols = metaData.getColumnCount();
            System.out.println("Employees");

            for (int i = 1; i <= noOfCols; i++) {
                System.out.printf("%-10s\t", metaData.getColumnName(i));
            }
            System.out.println();
			while(rowSet.next())
			{
				for (int i = 1; i <= noOfCols; i++) {
                    System.out.printf("%-10s\t", rowSet.getObject(i));
                }
                System.out.println();		
             }
		}catch(Exception exception)
		{
			exception.printStackTrace();
		}
		finally{
			
		}
		
	}
	private void getDatabaseMetaData() {
		try{
			Class.forName(db_driverClass);
			connection=DriverManager.getConnection(db_url,db_userName,db_password);
			DatabaseMetaData databaseMetaData= connection.getMetaData();
			
			System.out.println("DatabaseProductName:"+ databaseMetaData.getDatabaseProductName());
			System.out.println("DatabaseProductVersion:"+ databaseMetaData.getDatabaseProductVersion());
			System.out.println("DatabaseDriverName:"+ databaseMetaData.getDriverName());
			System.out.println("DatabaseDriverVersion:"+ databaseMetaData.getDriverVersion());
			System.out.println("MaxColumnNameLength:"+ databaseMetaData.getMaxColumnNameLength());
			System.out.println("URL:"+ databaseMetaData.getURL());
			System.out.println("UserName:"+ databaseMetaData.getUserName());
			
			 resultSet= databaseMetaData.getCatalogs();
			 while(resultSet.next())
				{
					System.out.println(resultSet.getString(1));
					
				}
			
			
		}catch(Exception exception)
		{
			exception.printStackTrace();
		}
		finally{
			if (resultSet != null) {
		        try {
		        	resultSet.close();
		        } catch (SQLException e) { /* ignored */}
		    }
		    if (pstatement != null) {
		        try {
		        	pstatement.close();
		        } catch (SQLException e) { /* ignored */}
		    }
		    if (connection != null) {
		        try {
		        	connection.close();
		        } catch (SQLException e) { /* ignored */}
		    }
		}
		
	}
	private void deleteAllFromDatabase() {
		
		try{
			Class.forName(db_driverClass);
			connection=DriverManager.getConnection(db_url,db_userName,db_password);
			String deleteData="delete from employees";
			pstatement=connection.prepareStatement(deleteData);
			
			
			pstatement.executeUpdate();
			
		}catch(Exception exception)
		{
			exception.printStackTrace();
		}
		finally{
			if (resultSet != null) {
		        try {
		        	resultSet.close();
		        } catch (SQLException e) { /* ignored */}
		    }
		    if (pstatement != null) {
		        try {
		        	pstatement.close();
		        } catch (SQLException e) { /* ignored */}
		    }
		    if (connection != null) {
		        try {
		        	connection.close();
		        } catch (SQLException e) { /* ignored */}
		    }
		}
		
		System.out.println("delete successful");
		
	}
	private void addEmployeesFromExcelSheetToDatabase() {
		
		try{
			Class.forName(db_driverClass);
			connection=DriverManager.getConnection(db_url,db_userName,db_password);
			connection.setAutoCommit(false);
			String insertData="insert into employees (emp_name,emp_salary,emp_address,date_of_joining) values(?,?,?,?)";
			pstatement=connection.prepareStatement(insertData);
			 for(EmployeeModel employee : employeeList){
		            
				 pstatement.setString(1, employee.getEmpName());
					pstatement.setDouble(2, employee.getEmpSalary());
					pstatement.setString(3, employee.getEmpAddress());
					 java.util.Date utilDate = employee.getDateOfJoining();
					    java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
					pstatement.setDate(4, sqlDate );
					pstatement.addBatch();
		           
		        }
			 pstatement.executeBatch();
			 connection.commit();
			 System.out.println("add batch from excel successful");
			
		}catch(Exception exception)
		{
			exception.printStackTrace();
		}
		finally{
			if (resultSet != null) {
		        try {
		        	resultSet.close();
		        } catch (SQLException e) { /* ignored */}
		    }
		    if (pstatement != null) {
		        try {
		        	pstatement.close();
		        } catch (SQLException e) { /* ignored */}
		    }
		    if (connection != null) {
		        try {
		        	connection.close();
		        } catch (SQLException e) { /* ignored */}
		    }
		}
		
		
		 
	        
	        
	       

		
	}

	private void findAverageSalaryFromDataBase() {
		try{
			Class.forName(db_driverClass);
			connection=DriverManager.getConnection(db_url,db_userName,db_password);
			String selectData="select avg(emp_salary) from employees";
			pstatement=connection.prepareStatement(selectData);
			
			
			resultSet=pstatement.executeQuery();
			ResultSetMetaData resultsetMetaData= resultSet.getMetaData();
			for(int i=1;i<=resultsetMetaData.getColumnCount();i++)
			{
				System.out.print(resultsetMetaData.getColumnName(i)+"\t");
			}
			System.out.println();
			while(resultSet.next())
			{
				System.out.println(resultSet.getInt(1));
			}
		}catch(Exception exception)
		{
			exception.printStackTrace();
		}
		finally{
			if (resultSet != null) {
		        try {
		        	resultSet.close();
		        } catch (SQLException e) { /* ignored */}
		    }
		    if (pstatement != null) {
		        try {
		        	pstatement.close();
		        } catch (SQLException e) { /* ignored */}
		    }
		    if (connection != null) {
		        try {
		        	connection.close();
		        } catch (SQLException e) { /* ignored */}
		    }
		}
		
	}
	private void countNoOfEmployeesFromDataBase() {
		try{
			Class.forName(db_driverClass);
			connection=DriverManager.getConnection(db_url,db_userName,db_password);
			String selectData="select count(*) from employees";
			pstatement=connection.prepareStatement(selectData);
			
			
			resultSet=pstatement.executeQuery();
			ResultSetMetaData resultsetMetaData= resultSet.getMetaData();
			for(int i=1;i<=resultsetMetaData.getColumnCount();i++)
			{
				System.out.print(resultsetMetaData.getColumnName(i)+"\t");
			}
			System.out.println();
			while(resultSet.next())
			{
				System.out.println(resultSet.getInt(1));
			}
		}catch(Exception exception)
		{
			exception.printStackTrace();
		}
		finally{
			if (resultSet != null) {
		        try {
		        	resultSet.close();
		        } catch (SQLException e) { /* ignored */}
		    }
		    if (pstatement != null) {
		        try {
		        	pstatement.close();
		        } catch (SQLException e) { /* ignored */}
		    }
		    if (connection != null) {
		        try {
		        	connection.close();
		        } catch (SQLException e) { /* ignored */}
		    }
		}
		
	}
	private void findMinSalaryFromDataBase() {
		try{
			Class.forName(db_driverClass);
			connection=DriverManager.getConnection(db_url,db_userName,db_password);
			String selectData="select min(emp_salary) from employees";
			pstatement=connection.prepareStatement(selectData);
			
			
			resultSet=pstatement.executeQuery();
			ResultSetMetaData resultsetMetaData= resultSet.getMetaData();
			for(int i=1;i<=resultsetMetaData.getColumnCount();i++)
			{
				System.out.print(resultsetMetaData.getColumnName(i)+"\t");
			}
			System.out.println();
			while(resultSet.next())
			{
				System.out.println(resultSet.getInt(1));
			}
		}catch(Exception exception)
		{
			exception.printStackTrace();
		}
		finally{
			if (resultSet != null) {
		        try {
		        	resultSet.close();
		        } catch (SQLException e) { /* ignored */}
		    }
		    if (pstatement != null) {
		        try {
		        	pstatement.close();
		        } catch (SQLException e) { /* ignored */}
		    }
		    if (connection != null) {
		        try {
		        	connection.close();
		        } catch (SQLException e) { /* ignored */}
		    }
		}
		
	}
	private void findMaxSalaryFromDataBase() {
		try{
			Class.forName(db_driverClass);
			connection=DriverManager.getConnection(db_url,db_userName,db_password);
			String selectData="select max(emp_salary) from employees";
			pstatement=connection.prepareStatement(selectData);
			
			
			resultSet=pstatement.executeQuery();
			ResultSetMetaData resultsetMetaData= resultSet.getMetaData();
			for(int i=1;i<=resultsetMetaData.getColumnCount();i++)
			{
				System.out.print(resultsetMetaData.getColumnName(i)+"\t");
			}
			System.out.println();
			while(resultSet.next())
			{
				System.out.println(resultSet.getInt(1));
			}
		}catch(Exception exception)
		{
			exception.printStackTrace();
		}
		finally{
			if (resultSet != null) {
		        try {
		        	resultSet.close();
		        } catch (SQLException e) { /* ignored */}
		    }
		    if (pstatement != null) {
		        try {
		        	pstatement.close();
		        } catch (SQLException e) { /* ignored */}
		    }
		    if (connection != null) {
		        try {
		        	connection.close();
		        } catch (SQLException e) { /* ignored */}
		    }
		}
		
	}
	private void updateEmployeesFromDataBase() {
		System.out.println("enter employee id to update");
		int empId= Integer.parseInt(input.next());
		 System.out.println("enter employee name ");
		 String empName= input.next();
		 System.out.println("enter employee Address");
		 String empAddress= input.next();
		 System.out.println("enter employee salary");
		 Double empSalary= Double.parseDouble(input.next());
		 Date date = null;
		 System.out.println("enter employee date of joining using the format: yyyy-MM-dd ");
			try {
				 date = new SimpleDateFormat("yyyy-MM-dd").parse(input.next());
			} catch (ParseException e) {
				
				e.printStackTrace();
			}
			employeeModel= new EmployeeModel(empId,empName,empSalary,empAddress,date);
			try{
				Class.forName(db_driverClass);
				connection=DriverManager.getConnection(db_url,db_userName,db_password);
				String deleteData="update  employees set emp_name =?, emp_salary =?, emp_address =?, date_of_joining =? where id = ?";
				pstatement=connection.prepareStatement(deleteData);
				pstatement.setString(1, employeeModel.getEmpName());
				pstatement.setDouble(2, employeeModel.getEmpSalary());
				pstatement.setString(3, employeeModel.getEmpAddress());
				 java.util.Date utilDate = employeeModel.getDateOfJoining();
				    java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
				pstatement.setDate(4, sqlDate );
				pstatement.setInt(5, empId );
				pstatement.executeUpdate();
			
				
			}catch(Exception exception)
			{
				exception.printStackTrace();
			}
			finally{
				if (resultSet != null) {
			        try {
			        	resultSet.close();
			        } catch (SQLException e) { /* ignored */}
			    }
			    if (pstatement != null) {
			        try {
			        	pstatement.close();
			        } catch (SQLException e) { /* ignored */}
			    }
			    if (connection != null) {
			        try {
			        	connection.close();
			        } catch (SQLException e) { /* ignored */}
			    }
			}
			
			System.out.println("update successful");
	}
	private void showEmployeesFromDataBase() {
		try{
			Class.forName(db_driverClass);
			connection=DriverManager.getConnection(db_url,db_userName,db_password);
			String selectData="select * from employees";
			pstatement=connection.prepareStatement(selectData);
			
			
			resultSet=pstatement.executeQuery();
			ResultSetMetaData resultsetMetaData= resultSet.getMetaData();
			for(int i=1;i<=resultsetMetaData.getColumnCount();i++)
			{
				//System.out.print(resultsetMetaData.getColumnName(i)+"\t");
				System.out.printf("%-10s\t", resultsetMetaData.getColumnName(i));
			}
			System.out.println();
			while(resultSet.next())
			{
				for (int i = 1; i <= resultsetMetaData.getColumnCount(); i++) {
                    System.out.printf("%-10s\t", resultSet.getString(i));
                }
                System.out.println();
				}
			
		}catch(Exception exception)
		{
			exception.printStackTrace();
		}
		finally{
			if (resultSet != null) {
		        try {
		        	resultSet.close();
		        } catch (SQLException e) { /* ignored */}
		    }
		    if (pstatement != null) {
		        try {
		        	pstatement.close();
		        } catch (SQLException e) { /* ignored */}
		    }
		    if (connection != null) {
		        try {
		        	connection.close();
		        } catch (SQLException e) { /* ignored */}
		    }
		}
		
	}
	private void deleteEmployeesFromDataBase() {
		System.out.println("enter employee id to remove");
		int empId= Integer.parseInt(input.next());
		int flagFound=0;
		try{
			Class.forName(db_driverClass);
			connection=DriverManager.getConnection(db_url,db_userName,db_password);
			String deleteData="delete from employees where id = ?";
			pstatement=connection.prepareStatement(deleteData);
			pstatement.setInt(1,empId);
			
			pstatement.executeUpdate();
			
		}catch(Exception exception)
		{
			exception.printStackTrace();
		}
		finally{
			if (resultSet != null) {
		        try {
		        	resultSet.close();
		        } catch (SQLException e) { /* ignored */}
		    }
		    if (pstatement != null) {
		        try {
		        	pstatement.close();
		        } catch (SQLException e) { /* ignored */}
		    }
		    if (connection != null) {
		        try {
		        	connection.close();
		        } catch (SQLException e) { /* ignored */}
		    }
		}
		
		System.out.println("delete successful");
		
		
	}
	private void addEmployeesToDataBase() {
		
			
		
			//employeeList.add(employee);
			System.out.println("enter employee name");
			String empName= input.next();
			System.out.println("enter employee salary");
			Double empSalary= Double.parseDouble(input.next());
			System.out.println("enter employee Address");
			String empAddress= input.next();
			System.out.println("enter employee date of joining using the format: yyyy-MM-dd ");
			
			Date date= new Date();
			try {
				date = new SimpleDateFormat("yyyy-MM-dd").parse(input.next());
			} catch (ParseException e) {
				
				e.printStackTrace();
			}
			int nextId=1;
			
			employeeModel= new EmployeeModel(nextId,empName,empSalary,empAddress,date);
			try{
				Class.forName(db_driverClass);
				connection=DriverManager.getConnection(db_url,db_userName,db_password);
				String insertData="insert into employees (emp_name,emp_salary,emp_address,date_of_joining) values(?,?,?,?)";
				pstatement=connection.prepareStatement(insertData);
				pstatement.setString(1, employeeModel.getEmpName());
				pstatement.setDouble(2, employeeModel.getEmpSalary());
				pstatement.setString(3, employeeModel.getEmpAddress());
				 java.util.Date utilDate = employeeModel.getDateOfJoining();
				    java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
				pstatement.setDate(4, sqlDate );
				pstatement.executeUpdate();
				
			}catch(Exception exception)
			{
				exception.printStackTrace();
			}
			finally{
				if (resultSet != null) {
			        try {
			        	resultSet.close();
			        } catch (SQLException e) { /* ignored */}
			    }
			    if (pstatement != null) {
			        try {
			        	pstatement.close();
			        } catch (SQLException e) { /* ignored */}
			    }
			    if (connection != null) {
			        try {
			        	connection.close();
			        } catch (SQLException e) { /* ignored */}
			    }
			}
			
			System.out.println("add successful");
			
		

		
	}
	private void displayEmployeeInfoFromACSV() {
		
		String csvFile = "resource/employeeInformation.csv";
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";
        ArrayList<EmployeeModel> csvEmpList= new ArrayList<EmployeeModel>() ;
        EmployeeModel csvEmp;
        try {

            br = new BufferedReader(new FileReader(csvFile));
            while ((line = br.readLine()) != null) {
            	csvEmp= new EmployeeModel();
            		//System.out.println(line);
                // use comma as separator
                String[] employeeFromCsv = line.split(cvsSplitBy);
                csvEmp.setEmpId(Integer.parseInt(employeeFromCsv[0]));
                csvEmp.setEmpName(employeeFromCsv[1]);
                csvEmp.setEmpSalary(Double.parseDouble(employeeFromCsv[2]));
                csvEmp.setEmpAddress(employeeFromCsv[3]);
                csvEmp.setDateOfJoining(new Date(employeeFromCsv[4]));
                csvEmpList.add(csvEmp);
            }
            employeeList=csvEmpList;
            showEmployee();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

	}
	private void saveEmployeeInfoIntoACSV() {
		String csvFile = "resource/employeeInformation.csv";
		try {
		FileWriter writer = new FileWriter(csvFile);

      

        //for header
      //  CSVUtils.writeLine(writer, Arrays.asList("Employee ID","Name", "Salary", "Adress","Date of Joining"));

        for (EmployeeModel emp : employeeList) {
        	
            ArrayList<String> list = new ArrayList<String>();
            list.add(Integer.toString(emp.getEmpId()));
            list.add(emp.getEmpName());
            list.add(Double.toString(emp.getEmpSalary()));
            list.add(emp.getEmpAddress());
            list.add(emp.getDateOfJoining().toString());

            
				//CSVUtils.writeLine(writer, list);
			

			//try custom separator and quote.
			//CSVUtils.writeLine(writer, list, '|', '\"');
            
            StringBuilder sb = new StringBuilder();
            boolean first = true;
            for (String value : list) {
            	
            	
            	 if (!first) {
                     sb.append(',');
                 }
            	 sb.append(value);

                 first = false;
            }
            sb.append("\n");
            writer.append(sb.toString());
        }

        writer.flush();
        writer.close();
        System.out.println("successfully saved to csv");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	private void saveEmployeeInfoFromAXMLIntoExcel() {
		loadEmployeeInfoFromAXML();
		saveEmployeetoFile();
		
	}
	private void loadEmployeeInfoFromAXML() {
		try {
		JAXBContext jaxbContext = JAXBContext.newInstance(Employees.class);
		
	    Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
	     
	    //We had written this file in marshalling example
	    Employees emps = (Employees) jaxbUnmarshaller.unmarshal( new File("resource/employeeInfo.xml") );
	    employeeList=emps.getEmployees();
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		showEmployee();
	}
	private void saveEmployeeInfoInAXML() {
		Employees employeesTest= new Employees();
		employeesTest.setEmployees(employeeList);
		 try {

				File file = new File("resource/employeeInfo.xml");
				JAXBContext jaxbContext = JAXBContext.newInstance(Employees.class);
				Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

				// output pretty printed
				jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
				
				
				//Marshal the employees list in console
			    //jaxbMarshaller.marshal(employeesTest, System.out);
			     
			    //Marshal the employees list in file
			    jaxbMarshaller.marshal(employeesTest, file);
				

			      } catch (JAXBException e) {
				e.printStackTrace();
			      }

		
	}
	private void sortEmployee() {
		System.out.println("Enter the following code to perform the respective operations");
		System.out.println("1: To sort using employee ID");
		System.out.println("2: To sort using employee Name");
		System.out.println("3: To sort using employee Salary");
		System.out.println("4: To sort using employee Date of joining");
		System.out.println("5: To sort using employee address");
		 System.out.println ("Enter code:(1-5)");
		String flag= input.next();
		 switch (flag) {
		   case "1":
			  
				Collections.sort(employeeList, new EmployeeIdComparator());
				saveEmployeetoFile();
				System.out.println ("successfully sorted");
		        break;
		  case "2":
			  Collections.sort(employeeList, new EmployeeNameComparator());
			  System.out.println ("successfully sorted");
				saveEmployeetoFile();
				break;
		  case "3":
			  Collections.sort(employeeList, new EmployeeSalaryComparator());
			  System.out.println ("successfully sorted");
				saveEmployeetoFile();
				break;
		  case "4":
			  Collections.sort(employeeList, new EmployeeDateComparator());
			  System.out.println ("successfully sorted");
				saveEmployeetoFile();
		        break;
		  case "5":
			  Collections.sort(employeeList, new EmployeeAddressComparator());
			  System.out.println ("successfully sorted");
				saveEmployeetoFile();
		        break;
		 
		  default:
			  System.out.println("Wrong code. Please try again");
		        break;
		}
		
			
		
	}
	private void loadDataFromFile() {
		   FileInputStream fis = null;
		   Date date= new Date();
		   
	        try {
	            fis = new FileInputStream("resource/employeeTest.xlsx");

	            // Using XSSF for xlsx format, for xls use HSSF
	            Workbook workbook = new XSSFWorkbook(fis);

	            int numberOfSheets = workbook.getNumberOfSheets();

	            //looping over each workbook sheet
	            for (int i = 0; i < numberOfSheets; i++) {
	                Sheet sheet = workbook.getSheetAt(i);
	                Iterator rowIterator = sheet.iterator();

	                //iterating over each row
	                while (rowIterator.hasNext()) {

	                    EmployeeModel employee = new EmployeeModel();
	                    Row row =  (Row) rowIterator.next();
	                    Iterator cellIterator = row.cellIterator();

	                    //Iterating over each cell (column wise)  in a particular row.
	                    while (cellIterator.hasNext()) {

	                        Cell cell = (Cell) cellIterator.next();
	                        if (cell.getColumnIndex() == 0) {
	                        	employee.setEmpId((int)cell.getNumericCellValue());
                            }
                            //Cell with index 2 contains marks in Science
                            else if (cell.getColumnIndex() == 1) {
                            	employee.setEmpName(cell.getStringCellValue());
                            }
                            //Cell with index 3 contains marks in English
                            else if (cell.getColumnIndex() == 2) {
                            	employee.setEmpAddress(cell.getStringCellValue());
                            }
                            else if (cell.getColumnIndex() == 3) {
                            	employee.setEmpSalary(cell.getNumericCellValue());
                            }
                            else if (cell.getColumnIndex() == 4) {
                            	try {
									date = new SimpleDateFormat("dd/MM/yyyy").parse(cell.getStringCellValue());
								} catch (ParseException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
                            	employee.setDateOfJoining(date);
                            	
                            }
	                    }
	                    //end iterating a row, add all the elements of a row in list
	                    employeeList.add(employee);
	                }
	            }

	            fis.close();

	        } catch (FileNotFoundException e) {
	            e.printStackTrace();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	}

	private void insertDummyData() {
		Date date= new Date();
		try {
			date = new SimpleDateFormat("dd/MM/yyyy").parse("22/05/1990");
		} catch (ParseException e) {
			
			e.printStackTrace();
		}
		employeeModel= new EmployeeModel(employeeList.size()+1,"bishwash",40000,"miami",date);
		employeeList.add(employeeModel);
		employeeModel= new EmployeeModel(employeeList.size()+1,"srawani",45000,"ohio",date);
		employeeList.add(employeeModel);
		employeeModel= new EmployeeModel(employeeList.size()+1,"ravi",46000,"maryland",date);
		employeeList.add(employeeModel);
		employeeModel= new EmployeeModel(employeeList.size()+1,"sundari",47000,"chicago",date);
		employeeList.add(employeeModel);
		employeeModel= new EmployeeModel(employeeList.size()+1,"gopi",48000,"New York",date);
		employeeList.add(employeeModel);
		 System.out.println("successfully inserted");
		
	}

	public void showEmployee() {
		
		for(int i=0;i<employeeList.size();i++)
		{
			//System.out.println("Employee "+(i+1) );
			System.out.println(employeeList.get(i) );
			System.out.println("========================================");
		}
		if(employeeList.size()==0)
			System.out.println("No employee is added . please add first");
			
		
			}

	public void removeEmployee() {
		
		System.out.println("enter employee id to remove");
		int empId= Integer.parseInt(input.next());
		int flagFound=0;
		for(int i=0;i<employeeList.size();i++)
		{
			if(employeeList.get(i).getEmpId()==empId)
			{
				employeeList.remove(i);
				flagFound=1;
			}
		}
		if(flagFound==1)
		{
			saveEmployeetoFile();
			System.out.println("successfully removed");
		}
		else 
		{
			System.out.println("not found");
			
		}
		
		
		
	}

	public void maxEmployeeSalary() {
		double max=0;
		/*for(int i=0;i<employeeList.size();i++)
		{
			if(max<employeeList.get(i).getEmpSalary())
			{
				max=employeeList.get(i).getEmpSalary();
			}
			
			
		}*/
		max=Collections.max(employeeList, new EmployeeSalaryComparator()).getEmpSalary();
		System.out.println("Maximum salary: "+max);
		
	}

	public void minEmployeeSalary() {
		double min=0;
		/*for(int i=0;i<employeeList.size();i++)
		{
			if(i==0)
			{
				min=employeeList.get(i).getEmpSalary();
			}
			if(min>employeeList.get(i).getEmpSalary())
			{
				min=employeeList.get(i).getEmpSalary();
			}
			
			
		}*/
		min=Collections.min(employeeList, new EmployeeSalaryComparator()).getEmpSalary();
		
		System.out.println("Minimum salary: "+min);
		
	}

	public void avgEmployeeSalary() {
		double average=0,total=0;
		for(int i=0;i<employeeList.size();i++)
		{
			total+=employeeList.get(i).getEmpSalary();
			
			
		}
		average=total/employeeList.size();
		NumberFormat formatter = new DecimalFormat("#0.00");     
		
		System.out.println("Average salary: "+formatter.format(average));
		
	}

	
	public void saveEmployeetoFile() {
        // Using XSSF for xlsx format, for xls use HSSF
        Workbook workbook = new XSSFWorkbook();

        Sheet employeeSheet =  workbook.createSheet("Employees");

        int rowIndex = 0;
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        
        
        for(EmployeeModel employee : employeeList){
            Row row =  employeeSheet.createRow(rowIndex++);
            int cellIndex = 0;
            //first place in row is id
            row.createCell(cellIndex++).setCellValue(employee.getEmpId());
            
            //second place in row is name
            row.createCell(cellIndex++).setCellValue(employee.getEmpName());

            //third place in row is address
            row.createCell(cellIndex++).setCellValue(employee.getEmpAddress());

            //fourth place in row is salary
            row.createCell(cellIndex++).setCellValue(employee.getEmpSalary());

            //fifth place in row is marks in English
        
            row.createCell(cellIndex++).setCellValue(dateFormat.format(employee.getDateOfJoining()));
            

        }

        //write this workbook in excel file.
        try {
            FileOutputStream fos = new FileOutputStream("resource/employeeTest.xlsx");
            workbook.write(fos);
            fos.close();

           // System.out.println(" is successfully written");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    
		
	}

	
	public void displayEmployeeFromFile() {
		InputStream fileIs = null;
        ObjectInputStream objIs = null;
        try {
            fileIs = new FileInputStream("resource/EmployeeDetail.txt");
            objIs = new ObjectInputStream(fileIs);
            EmployeeModel emp;
            while ((emp=(EmployeeModel) objIs.readObject())!=null)
            {
            	
            	// EmployeeModel emp = (EmployeeModel) objIs.readObject();
            	 System.out.println(emp);
            	 System.out.println("=================================================");
               
            }
           
           
           
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if(objIs != null) objIs.close();
            } catch (Exception ex){
                 
            }
        }
		
	}
	
	
	
	
	

}
