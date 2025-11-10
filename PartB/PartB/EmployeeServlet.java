package com.example.web;

import com.example.web.util.DBUtil;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.*;
import java.sql.*;

@WebServlet("/EmployeeServlet")
public class EmployeeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        String idParam = request.getParameter("id");
        String all = request.getParameter("all");

        try (PrintWriter out = response.getWriter();
             Connection conn = DBUtil.getConnection()) {

            out.println("<!doctype html><html><body><h2>Employees</h2>");

            if (all != null && all.equals("true")) {
                String sql = "SELECT EmpID, Name, Salary FROM Employee";
                try (PreparedStatement ps = conn.prepareStatement(sql);
                     ResultSet rs = ps.executeQuery()) {

                    out.println("<table border='1'><tr><th>EmpID</th><th>Name</th><th>Salary</th></tr>");
                    while (rs.next()) {
                        out.printf("<tr><td>%d</td><td>%s</td><td>%.2f</td></tr>",
                                   rs.getInt("EmpID"),
                                   escapeHtml(rs.getString("Name")),
                                   rs.getDouble("Salary"));
                    }
                    out.println("</table>");
                }
            } else if (idParam != null && !idParam.isBlank()) {
                String sql = "SELECT EmpID, Name, Salary FROM Employee WHERE EmpID = ?";
                try (PreparedStatement ps = conn.prepareStatement(sql)) {
                    ps.setInt(1, Integer.parseInt(idParam));
                    try (ResultSet rs = ps.executeQuery()) {
                        if (rs.next()) {
                            out.println("<table border='1'><tr><th>EmpID</th><th>Name</th><th>Salary</th></tr>");
                            out.printf("<tr><td>%d</td><td>%s</td><td>%.2f</td></tr>",
                                       rs.getInt("EmpID"),
                                       escapeHtml(rs.getString("Name")),
                                       rs.getDouble("Salary"));
                            out.println("</table>");
                        } else {
                            out.println("<p>No employee found with ID: " + escapeHtml(idParam) + "</p>");
                        }
                    }
                }
            } else {
                out.println("<p>No action. Use the search form.</p>");
            }

            out.println("<p><a href='employee.html'>Back</a></p>");
            out.println("</body></html>");

        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    private String escapeHtml(String s) {
        return s == null ? "" : s.replace("&","&amp;")
                                 .replace("<","&lt;")
                                 .replace(">","&gt;");
    }
}
