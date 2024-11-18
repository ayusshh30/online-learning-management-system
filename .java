import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// Helper Class for Database Connection
class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/OnlineLMS";
    private static final String USER = "root";
    private static final String PASSWORD = "password";
    public static Connection getConnection() throws Exception {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}

// Servlet to Fetch All Courses
@WebServlet("/courses")
public class FetchCoursesServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        try (Connection conn = DatabaseConnection.getConnection()) {
            PrintWriter out = response.getWriter();
            String query = "SELECT * FROM courses";
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            out.println("<h1>Available Courses</h1>");
            while (rs.next()) {
                out.println("<p>" + rs.getString("title") + ": " + rs.getString("description") + "</p>");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

// Servlet to Add a New Course
@WebServlet("/add-course")
public class AddCourseServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String title = request.getParameter("title");
        String description = request.getParameter("description");
        int instructorId = Integer.parseInt(request.getParameter("instructorId"));
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "INSERT INTO courses (title, description, instructor_id) VALUES (?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, title);
            stmt.setString(2, description);
            stmt.setInt(3, instructorId);
            stmt.executeUpdate();
            response.getWriter().println("Course added successfully!");
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("Error adding course.");
        }
    }
}

// Servlet to Enroll a Student
@WebServlet("/enroll")
public class EnrollStudentServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int studentId = Integer.parseInt(request.getParameter("studentId"));
        int courseId = Integer.parseInt(request.getParameter("courseId"));
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "INSERT INTO enrollments (student_id, course_id) VALUES (?, ?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, studentId);
            stmt.setInt(2, courseId);
            stmt.executeUpdate();
            response.getWriter().println("Student enrolled successfully!");
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("Error enrolling student.");
        }
    }
}

// Servlet to Submit an Assignment
@WebServlet("/submit-assignment")
public class SubmitAssignmentServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int assignmentId = Integer.parseInt(request.getParameter("assignmentId"));
        int studentId = Integer.parseInt(request.getParameter("studentId"));
        String submissionText = request.getParameter("submission");
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "UPDATE assignments SET submission = ?, status = 'Submitted' WHERE assignment_id = ? AND student_id = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, submissionText);
            stmt.setInt(2, assignmentId);
            stmt.setInt(3, studentId);
            stmt.executeUpdate();
            response.getWriter().println("Assignment submitted successfully!");
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("Error submitting assignment.");
        }
    }
}
