
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
      String eventType = request.getParameter("EVENTTYPE");
      String eventTitle = request.getParameter("EVENTTITLE");
      String dueDate = request.getParameter("DUEDATE");
      String priority = request.getParameter("PRIORITY");
      
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
		  PreparedStatement displayStatement = connection.prepareStatement(totalDB);
		  ResultSet DBResult = displayStatement.executeQuery();
		  PrintWriter out = response.getWriter();
		  String title = "";
		  if (className.isEmpty() && eventType.isEmpty() && eventTitle.isEmpty() && dueDate.isEmpty() && priority.isEmpty()) {
	    	  title = "Here is your current schedule!";
	      }
		  else {
			  title = "Data Successfully Inserted!";
		  }
	      String docType = "<!doctype html public \"-//w3c//dtd html 4.0 " + "transitional//en\">\n";
	      out.println(docType + //
	            "<html>\n" + //
	            "<head><title>" + title + "</title></head>\n" + //
	            "<body bgcolor=\"#f0f0f0\">\n" + //
	            "<h2 align=\"center\">" + title + "</h2>\n" + //
	            "<h3 align=\"center\">" + "Your schedule can be seen below:\n\n" + //
	            "<table style='width:100%'>" +
	            "<tr><th align='left'>Event ID</th><th align='left'>Class Name</th><th align='left'>Event Type</th><th align='left'>" + 
	            "Event Title</th><th align='left'>Due Date</th><th align='left'>Priority</th></tr>" +
	    		  "</ul>\n");
	      while(DBResult.next()) {
	    	  if (!DBResult.getString(2).isEmpty() && !DBResult.getString(3).isEmpty() && !DBResult.getString(4).isEmpty() && !DBResult.getString(5).isEmpty() && !DBResult.getString(6).isEmpty()) {
	    		  out.print("<tr>\t<td>" + DBResult.getInt(1) + "</td>");
	    		  out.print("<td>" + DBResult.getString(2) + "</td>");
	    		  out.print("<td>" + DBResult.getString(3) + "</td>");
	    		  out.print("<td>" + DBResult.getString(4) + "</td>");
	    		  out.print("<td>" + DBResult.getString(5) + "</td>");
	    		  out.print("<td>" + DBResult.getString(6) + "</td></tr>");
	    	  }
	      }
	      out.println("</table>");
	      out.println("<nav><a href=\"/webproject-tech-exercise-dormady/simpleFormInsert.html\">Insert Another Event</a></nav><br/>");
	      out.println("</body></html>");
	      connection.close();
	  } catch (SQLException e) {
		  // TODO Auto-generated catch block
		  e.printStackTrace();
	  }
   }

   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      doGet(request, response);
   }

}
