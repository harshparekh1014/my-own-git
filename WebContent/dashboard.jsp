<%@page import="com.lti.training.model.Student"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%!
		/*public void updateData(String stud_id){
			request.setAttribute("id", id);
		}*/
	%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<h2>Student List..</h2>
	<hr/>
	<a href="student?action=entry">Add New Student</a>
	<%
		List<Student> students = (List<Student>)request.getAttribute("studentList");
	%>
	<table border="1|0">
		<thead>
			<th>Id</th>
			<th>Name</th>
			<th>Email</th>
			<th>Contact</th>
		</thead>
		<tbody>
			<%
				for(Student student: students){
			%>
			<tr>
				<td name="stud_id"><%=student.getId() %></td>
				<td><%=student.getName() %></td>
				<td><%=student.getEmail() %></td>
				<td><%=student.getContact() %></td>
				<td><a href="student?action=showUpdate&id=<%=student.getId() %>">Update</a></td>
				<td><a href="student?action=delete&id=<%=student.getId() %>">Delete</a></td>
			</tr>
			<%} %>
		</tbody>
	</table>
</body>
</html>