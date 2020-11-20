package com.lti.training.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.lti.training.model.Student;

public class StudentDao {

	private String driverName;
	private String url;
	private String username;
	private String password;
	private Connection conn;

	public StudentDao(String driverName, String url, String username, String password) {
		super();
		this.driverName = driverName;
		this.url = url;
		this.username = username;
		this.password = password;
	}

	// set up connection
	public void connect() throws SQLException, ClassNotFoundException {
		if (this.conn == null || this.conn.isClosed()) {
			Class.forName(driverName);
			conn = DriverManager.getConnection(url, username, password);
		}
	}

	// close connection
	public void disconnect() throws SQLException{
		if(this.conn != null && !this.conn.isClosed())
			this.conn.close();
	}
	
	public List<Student> getStudentRecord() throws SQLException, ClassNotFoundException{
		List<Student> students = new ArrayList<Student>();
		String sql = "select * from student";
		this.connect();
		Statement statement = this.conn.createStatement();
		ResultSet rSet = statement.executeQuery(sql);
		
		while(rSet.next()){
			Student student = new Student(rSet.getString("name"), 
										  rSet.getString("email"), 
										  rSet.getString("contact"));
			student.setId(rSet.getInt("id"));
			students.add(student);
		}
		rSet.close();
		statement.close();
		this.disconnect();
		return students;
	}
	
	public boolean addRecord(Student student) throws ClassNotFoundException, SQLException{
		String sql = "insert into student values (stud_seq.nextval,'%s','%s','%s')";
		sql = String.format(sql, student.getName(), student.getEmail(),student.getContact());
		this.connect();
		Statement stmt = this.conn.createStatement();
		int n = stmt.executeUpdate(sql);
		stmt.close();
		this.disconnect();
		if(n > 0)return true;
		return false;
	}
	
	public Student getDataForUpdate(String id) throws SQLException, ClassNotFoundException{
		Student student = null;
		String sql = "select * from student where id = " + id;
		this.connect();
		Statement statement = this.conn.createStatement();
		ResultSet rSet = statement.executeQuery(sql);
		while(rSet.next()){
			student = new Student(rSet.getString("name"), 
										  rSet.getString("email"), 
										  rSet.getString("contact"));
			student.setId(rSet.getInt("id"));
		}
		rSet.close();
		statement.close();
		this.disconnect();
		return student;
	}
	
	public boolean updateRecord(Student student,String id) throws ClassNotFoundException, SQLException{
		String sql = "update student set name = '%s', email = '%s', contact = '%s' where id = " + id;
		sql = String.format(sql, student.getName(), student.getEmail(),student.getContact());
		this.connect();
		Statement stmt = this.conn.createStatement();
		int n = stmt.executeUpdate(sql);
		stmt.close();
		this.disconnect();
		if(n > 0)return true;
		return false;
	}
	
	public boolean deleteRecord(String id) throws ClassNotFoundException, SQLException{
		String sql = "delete from student where id = " + id;
		this.connect();
		Statement stmt = this.conn.createStatement();
		int n = stmt.executeUpdate(sql);
		stmt.close();
		this.disconnect();
		if(n > 0)return true;
		return false;
	}

}
