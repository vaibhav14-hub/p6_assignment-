package in.vp.backend;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Servlet implementation class insert
 */
public class delete extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public delete() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException  {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		 response.setContentType("text/html");
		 
		 PrintWriter pw = response.getWriter();
		// pw.println("Driver class loaded sucessfully..1");
		 String animalname=request.getParameter("animalname");
		
		 Connection con= null;
		 PreparedStatement pstmt=null;
		 
	     try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		pw.println("Driver class loaded sucessfully..");
		con=DriverManager.getConnection("jdbc:mysql://localhost:3306/adb","root","root");
		pw.println(" connect sucessfully..");
		String sq= "DELETE FROM animal WHERE name =?";
		pstmt = con.prepareStatement(sq);
		
		pstmt.setString(1, animalname);
		
		int rowsInserted = pstmt.executeUpdate();
		
        if (rowsInserted > 0) {
            pw.println("<h2>Data Deleted successfully.</h2>");
        } else {
            pw.println("<h2>Failed to Delete data.</h2>");
        }
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
            pw.println("An error occurred: " + e.getMessage());
		} finally {
            // Close resources
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException ex) {
                pw.println("An error occurred while closing resources: " + ex.getMessage());
            }
        }
	     
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
