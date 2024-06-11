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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Servlet implementation class Search
 */
@WebServlet("/Search")
public class search extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public search() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        out.println("<html><head><title>Sorted Species</title></head><body>");
        out.println("<h1>Sort Animals</h1>");
        out.println("<form action='' method='get'>");
        out.println("<label for='sort'>Sort by:</label>");
        out.println("<select id='sort' name='sort'>");
        out.println("<option value='name'>Name</option>");
        out.println("<option value='category'>Category</option>");
        out.println("<option value='lifeexpectancy'>Life Expectancy</option>");
        out.println("</select>");
        out.println("<button type='submit'>Submit</button>");
      
        out.println("<label for='category'>Category:</label>");
        out.println("<select id='category' name='category'>");
        out.println("<option value='mammal'>Mammal</option>");
        out.println("<option value='fish'>Fish</option>");
        out.println("<option value='reptile'>Reptile</option>");
        out.println("<option value='amphibian'>Amphibian</option>");
        out.println("<option value='insect'>Insect</option>");
        out.println("<option value='bird'>Bird</option>");
        out.println("</select>");

        out.println("<button type='submit'>Submit</button>");
        out.println("</form>");

        // Fetching data from the database and displaying it
        displaySortedData(out, request.getParameter("sort"), request.getParameter("category"));

        out.println("</body></html>");
    }

    // Method to fetch and display sorted data based on selected criteria
    private void displaySortedData(PrintWriter out, String sortBy, String category) {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/adb", "root", "root");
            stmt = conn.createStatement();

            String sql = "SELECT name, category, lifeexpectancy FROM animal";

          
            if (sortBy != null && !sortBy.isEmpty()) {
                sql += " ORDER BY ";

                // Append sorting criteria
                switch (sortBy) {
                    case "name":
                        sql += "name";
                        break;
                    case "category":
                        sql += "category";
                        break;
                    case "lifeexpectancy":
                        sql += "lifeexpectancy";
                        break;
                    default:
                        // invalid sorting 
                        out.println("<p>Invalid sorting criteria</p>");
                        return;
                }
            }

            
            if (category != null && !category.isEmpty()) {
                if (sortBy == null || sortBy.isEmpty()) {
                    
                    sql += " ORDER BY ";
                } else {
                    
                    sql += ", ";
                }
                sql += "category='" + category + "'";
            }



            rs = stmt.executeQuery(sql);

            // Display the sorted records
            out.println("<h2>Sorted Species</h2>");
            out.println("<table border='1'>");
            out.println("<tr><th>Name</th><th>Category</th><th>Life Expectancy</th></tr>");
            while (rs.next()) {
                String name = rs.getString("name");
                String cat = rs.getString("category");
                String lifeExpectancy = rs.getString("lifeexpectancy");
                out.println("<tr><td>" + name + "</td><td>" + cat + "</td><td>" + lifeExpectancy + "</td></tr>");
            }
            out.println("</table>");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            out.println("<h2>Error</h2>");
            out.println("<p>" + e.getMessage() + "</p>");
        } finally {
            // Close resources
            try {
                if (rs != null)
                    rs.close();
                if (stmt != null)
                    stmt.close();
                if (conn != null)
                    conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
