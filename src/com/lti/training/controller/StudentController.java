package com.lti.training.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lti.training.dao.StudentDao;
import com.lti.training.model.Student;

@WebServlet("/student")
public class StudentController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private StudentDao sDao;

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		String driverName = config.getServletContext().getInitParameter("jdbcDriver");
		String url = config.getServletContext().getInitParameter("jdbcUrl");
		String username = config.getServletContext().getInitParameter("jdbcUsername");
		String password = config.getServletContext().getInitParameter("jdbcPassword");

		// initialize Dao
		this.sDao = new StudentDao(driverName, url, username, password);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// fetch the action parameter
		String action = request.getParameter("action");
		String viewName = "";
		try {
			switch (action) {
			case "entry":
				viewName = this.showEntry(request, response);
				break;
			case "save":
				viewName = this.addEntry(request, response);
				break;
			case "dash" : 
				viewName = this.showDashBoard(request, response);
				break;
			case "showUpdate" : 
				viewName = this.showUpdateEntryPage(request, response);
				break;
			case "update" : 
				viewName = this.updateEntry(request, response);
				break;
			case "delete" : 
				viewName = this.deleteEntry(request, response);
				break;	
			}
		} catch (Exception e) {
			throw new ServletException(e.getMessage());
		}
		RequestDispatcher dispatcher = request.getRequestDispatcher(viewName);
		dispatcher.forward(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	private String showEntry(HttpServletRequest request, HttpServletResponse response) {
		return "entry.jsp";
	}
	
	private String addEntry(HttpServletRequest request, HttpServletResponse response) throws ClassNotFoundException, SQLException {
		String name = request.getParameter("name");
		String email = request.getParameter("email");
		String contact = request.getParameter("contact");
		this.sDao.addRecord(new Student(name, email, contact));
		return "student?action=dash";
	}
	
	private String showUpdateEntryPage(HttpServletRequest request, HttpServletResponse response) throws ClassNotFoundException, SQLException {
		String id = request.getParameter("id");
		Student student = this.sDao.getDataForUpdate(id);
		request.setAttribute("student", student);
		return "updateEntry.jsp";
	}
	
	private String updateEntry(HttpServletRequest request, HttpServletResponse response) throws ClassNotFoundException, SQLException {
		String name = request.getParameter("name");
		String email = request.getParameter("email");
		String contact = request.getParameter("contact");
		String id = request.getParameter("id");
		this.sDao.updateRecord(new Student(name, email, contact), id);
		return "student?action=dash";
	}
	
	private String showDashBoard(HttpServletRequest request, HttpServletResponse response) throws ClassNotFoundException, SQLException, ServletException, IOException {
		// fetch all student records from DB
		List<Student> students;
		students = this.sDao.getStudentRecord();
		request.setAttribute("studentList", students);
		return "dashboard.jsp";
	}

	private String deleteEntry(HttpServletRequest request, HttpServletResponse response) throws ClassNotFoundException, SQLException, ServletException, IOException{
		String id = request.getParameter("id");
		this.sDao.deleteRecord(id);
		return "student?action=dash";
	}

}
