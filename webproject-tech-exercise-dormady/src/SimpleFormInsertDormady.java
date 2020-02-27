
/**
 * @file SimpleFormInsert.java
 */
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/SimpleFormInsert")
public class SimpleFormInsertDormady extends HttpServlet {
   private static final long serialVersionUID = 1L;

   public SimpleFormInsertDormady() {
      super();
   }

   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      String className = request.getParameter("CLASSNAME");
      System.out.println(className);
      String eventType = request.getParameter("EVENTTYPE");
      System.out.println(eventType);
      String eventTitle = request.getParameter("EVENTTITLE");
      System.out.println(eventTitle);
      String dueDate = request.getParameter("DUEDATE");
      System.out.println(dueDate);
      String priority = request.getParameter("PRIORITY");
      System.out.println(priority);
      
      Connection connection = null;
      String insertSql = " INSERT INTO myTableTechExerciseDormady (eventID, CLASSNAME, EVENTTYPE, EVENTTITLE, DUEDATE, PRIORITY) values (default, ?, ?, ?, ?, ?)";

      try {
    	 DBConnectionDormady.getDBConnection(getServletContext());
         DBConnectionDormady.getDBConnection();
         connection = DBConnectionDormady.connection;
         PreparedStatement preparedStmt = connection.prepareStatement(insertSql);
         preparedStmt.setString(1, className);
         preparedStmt.setString(2, eventType);
         preparedStmt.setString(3, eventTitle);
         preparedStmt.setString(4, dueDate);
         preparedStmt.setString(5, priority);
         preparedStmt.execute();
      } catch (Exception e) {
         e.printStackTrace();
      }

      // Set response content type
      response.setContentType("text/html");
      String totalDB = "SELECT eventID, className, eventType, eventTitle, dueDate, priority FROM myTableTechExerciseDormady";
      try {
    	  connection = DBConnectionDormady.connection;
		  PreparedStatement displayStatement = connection.prepareStatement(totalDB);
		  ResultSet DBResult = displayStatement.executeQuery();
		  PrintWriter out = response.getWriter();
	      String title = "Data Successfully Inserted!";
	      String docType = "<!doctype html public \"-//w3c//dtd html 4.0 " + "transitional//en\">\n";
	      out.println(docType + //
	            "<html>\n" + //
	            "<head><title>" + title + "</title></head>\n" + //
	            "<body bgcolor=\"#f0f0f0\">\n" + //
	            "<h2 align=\"center\">" + title + "</h2>\n" + //
	            "<h3 align=\"center\">" + "Your resulting schedule can be seen below:\n\n" + //
	            "<table style='width:100%'>" +
	            "<tr><th>Event ID</th><th>Class Name</th><th>Event Type</th><th>Event Title</th><th>Due Date</th><th>Priority</th></tr>" +
	    		  "</ul>\n");
	      while(DBResult.next()) {
	    	  out.print("<tr><td>" + DBResult.getInt(1) + "</td>");
	    	  out.print("<td>" + DBResult.getString(2) + "</td>");
	    	  out.print("<td>" + DBResult.getString(3) + "</td>");
	    	  out.print("<td>" + DBResult.getString(4) + "</td>");
	    	  out.print("<td>" + DBResult.getString(5) + "</td>");
	    	  out.print("<td>" + DBResult.getString(6) + "</td></tr>");
	      }
	      out.println("<a href=/webproject/simpleFormSearch.html>Search Data</a> <br>");
	      out.println("</body></html>");
	      connection.close();
	  } catch (SQLException e) {
		  // TODO Auto-generated catch block
		  e.printStackTrace();
	  }
      /*PrintWriter out = response.getWriter();
      String title = "Data Successfully Inserted!";
      String docType = "<!doctype html public \"-//w3c//dtd html 4.0 " + "transitional//en\">\n";
      out.println(docType + //
            "<html>\n" + //
            "<head><title>" + title + "</title></head>\n" + //
            "<body bgcolor=\"#f0f0f0\">\n" + //
            "<h2 align=\"center\">" + title + "</h2>\n" + //
            "<ul>\n" + //
            //output +
            //"  <li><b>Class Name</b>: " + className + "\n" + //
            //"  <li><b>Event Type</b>: " + eventType + "\n" + //
            //"  <li><b>Event Title</b>: " + eventTitle + "\n" + //
            //"  <li><b>Due Date</b>: " + dueDate + "\n" + //
            //"  <li><b>Priority</b>: " + priority + "\n" + //

            "</ul>\n");

      out.println("<a href=/webproject/simpleFormSearch.html>Search Data</a> <br>");
      out.println("</body></html>");*/
   }

   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      doGet(request, response);
   }

}
