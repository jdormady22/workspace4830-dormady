import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/MyServletDB")
public class MyServletDBDormady extends HttpServlet {
   private static final long serialVersionUID = 1L;
   static String url = "jdbc:mysql://ec2-54-180-113-223.ap-northeast-2.compute.amazonaws.com:3306/myDBTechExerciseDormady";
   static String user = "newmysqlremoteuser0722";
   static String password = "mypassword";
   static Connection connection = null;

   public MyServletDBDormady() {
      super();
   }

   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      response.setContentType("text/html;charset=UTF-8");
      response.getWriter().println("-------- MySQL JDBC Connection Testing ------------<br>");
      try {
         Class.forName("com.mysql.cj.jdbc.Driver");//("com.mysql.jdbc.Driver");
      } catch (ClassNotFoundException e) {
         System.out.println("Where is your MySQL JDBC Driver?");
         e.printStackTrace();
         return;
      }
      response.getWriter().println("MySQL JDBC Driver Registered!<br>");
      connection = null;
      try {
         connection = DriverManager.getConnection(url, user, password);
      } catch (SQLException e) {
         System.out.println("Connection Failed! Check output console");
         e.printStackTrace();
         return;
      }
      if (connection != null) {
         response.getWriter().println("You made it, take control your database now!<br>");
      } else {
         System.out.println("Failed to make connection!");
      }
      try {
         String selectSQL = "SELECT * FROM myTableTechExerciseDormady";// WHERE MYUSER LIKE ?";
//         String theUserName = "user%";
         response.getWriter().println(selectSQL + "<br>");
         response.getWriter().println("------------------------------------------<br>");
         PreparedStatement preparedStatement = connection.prepareStatement(selectSQL);
//         preparedStatement.setString(1, theUserName);
         ResultSet rs = preparedStatement.executeQuery();
         while (rs.next()) {
            String eventID = rs.getString("EventID");
            String className = rs.getString("CLASSNAME");
            String eventType  = rs.getString("EVENTTYPE");
            String eventTitle  = rs.getString("EVENTTITLE");
            String dueDate = rs.getString("DUEDATE");
            String priority = rs.getString("PRIORITY");
            response.getWriter().append("EVENT ID: " + eventID + ", ");
            response.getWriter().append("CLASS NAME: " + className + ", ");
            response.getWriter().append("EVENT TYPE: " + eventType  + ", ");
            response.getWriter().append("EVENT TITLE: " + eventTitle  + "<br>");
            response.getWriter().append("DUE DATE: " + dueDate + ", ");
            response.getWriter().append("Priority: " + priority + ", ");
         }
      } catch (SQLException e) {
         e.printStackTrace();
      }
   }

   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      doGet(request, response);
   }
}